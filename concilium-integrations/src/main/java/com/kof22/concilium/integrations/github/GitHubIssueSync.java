package com.kof22.concilium.integrations.github;


import java.util.List;
import com.kingsrook.qqq.backend.core.actions.tables.GetAction;
import com.kingsrook.qqq.backend.core.actions.tables.InsertAction;
import com.kingsrook.qqq.backend.core.actions.tables.UpdateAction;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kingsrook.qqq.backend.core.model.actions.tables.get.GetInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.get.GetOutput;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertOutput;
import com.kingsrook.qqq.backend.core.model.actions.tables.update.UpdateInput;
import com.kingsrook.qqq.backend.core.model.data.QRecord;
import com.kof22.concilium.model.work.WorkItem;


/*******************************************************************************
 ** Synchronises Concilium {@link WorkItem} records with GitHub Issues.
 **
 ** {@link #createAndTrack} creates a new GitHub Issue via the {@link GitHubClient}
 ** and then inserts a corresponding WorkItem into the Concilium database.
 ** {@link #syncStatus} reads the current state of a GitHub Issue and updates
 ** the local WorkItem record to match.
 *******************************************************************************/
public class GitHubIssueSync
{
   private static final QLogger LOG = QLogger.getLogger(GitHubIssueSync.class);

   private static final String TRACKER_TYPE = "github";

   private final GitHubClient gitHubClient;



   /*******************************************************************************
    ** Constructor -- uses a default GitHubClient.
    *******************************************************************************/
   public GitHubIssueSync()
   {
      this.gitHubClient = new GitHubClient();
   }



   /*******************************************************************************
    ** Constructor -- accepts an injected GitHubClient (useful for testing).
    *******************************************************************************/
   public GitHubIssueSync(GitHubClient gitHubClient)
   {
      this.gitHubClient = gitHubClient;
   }



   /*******************************************************************************
    ** Create a GitHub Issue and a corresponding Concilium WorkItem record.
    **
    ** @param masterProjectId the Concilium master project ID
    ** @param repo            the owner/repo slug, e.g. "Kof22/Concilium"
    ** @param title           the issue title
    ** @param body            the issue body (Markdown)
    ** @param itemType        the work item type: epic, story, task, subtask
    ** @param labels          optional label names to apply
    ** @return the created WorkItem with externalId and externalUrl populated
    ** @throws QException if the GitHub call or database insert fails
    *******************************************************************************/
   public WorkItem createAndTrack(Long masterProjectId, String repo, String title, String body,
                                  String itemType, List<String> labels) throws QException
   {
      LOG.info("Creating tracked GitHub issue",
         new LogPair("masterProjectId", masterProjectId),
         new LogPair("repo", repo),
         new LogPair("title", title),
         new LogPair("itemType", itemType));

      ////////////////////////////////////////
      // Create the issue on GitHub via CLI //
      ////////////////////////////////////////
      GitHubIssue ghIssue = gitHubClient.createIssue(repo, title, body, labels);

      ///////////////////////////////////////////////////////
      // Build a WorkItem QRecord and insert into Concilium //
      ///////////////////////////////////////////////////////
      QRecord workItemRecord = new QRecord()
         .withValue("masterProjectId", masterProjectId)
         .withValue("trackerType", TRACKER_TYPE)
         .withValue("trackerProjectKey", repo)
         .withValue("externalId", String.valueOf(ghIssue.getNumber()))
         .withValue("externalUrl", ghIssue.getUrl())
         .withValue("itemType", itemType)
         .withValue("title", ghIssue.getTitle())
         .withValue("status", mapGitHubState(ghIssue.getState()));

      InsertInput insertInput = new InsertInput();
      insertInput.setTableName(WorkItem.TABLE_NAME);
      insertInput.setRecords(List.of(workItemRecord));

      InsertOutput insertOutput = new InsertAction().execute(insertInput);
      QRecord insertedRecord = insertOutput.getRecords().get(0);

      LOG.info("Created tracked GitHub issue",
         new LogPair("workItemId", insertedRecord.getValue("id")),
         new LogPair("issueNumber", ghIssue.getNumber()),
         new LogPair("url", ghIssue.getUrl()));

      WorkItem workItem = new WorkItem();
      workItem.setId(insertedRecord.getValueLong("id"));
      workItem.setMasterProjectId(masterProjectId);
      workItem.setTrackerType(TRACKER_TYPE);
      workItem.setTrackerProjectKey(repo);
      workItem.setExternalId(String.valueOf(ghIssue.getNumber()));
      workItem.setExternalUrl(ghIssue.getUrl());
      workItem.setItemType(itemType);
      workItem.setTitle(ghIssue.getTitle());
      workItem.setStatus(mapGitHubState(ghIssue.getState()));

      return (workItem);
   }



   /*******************************************************************************
    ** Sync the status of a WorkItem from GitHub Issues.
    **
    ** Reads the current GitHub Issue state and updates the local WorkItem
    ** record so that Concilium's view stays in sync.
    **
    ** @param workItemId the Concilium WorkItem primary key
    ** @throws QException if the lookup or update fails
    *******************************************************************************/
   public void syncStatus(Long workItemId) throws QException
   {
      LOG.info("Syncing work item status from GitHub", new LogPair("workItemId", workItemId));

      /////////////////////////////////
      // Look up the WorkItem record //
      /////////////////////////////////
      GetInput getInput = new GetInput();
      getInput.setTableName(WorkItem.TABLE_NAME);
      getInput.setPrimaryKey(workItemId);

      GetOutput getOutput = new GetAction().execute(getInput);
      QRecord workItemRecord = getOutput.getRecord();

      if(workItemRecord == null)
      {
         throw (new QException("WorkItem not found: " + workItemId));
      }

      String trackerType = workItemRecord.getValueString("trackerType");
      if(!TRACKER_TYPE.equals(trackerType))
      {
         throw (new QException("WorkItem " + workItemId + " is not a GitHub issue (trackerType=" + trackerType + ")"));
      }

      String repo = workItemRecord.getValueString("trackerProjectKey");
      Integer issueNumber = Integer.valueOf(workItemRecord.getValueString("externalId"));

      /////////////////////////////////////
      // Fetch current state from GitHub //
      /////////////////////////////////////
      GitHubIssue ghIssue = gitHubClient.getIssue(repo, issueNumber);

      //////////////////////////////////////////
      // Update the local WorkItem if changed //
      //////////////////////////////////////////
      String newStatus = mapGitHubState(ghIssue.getState());
      String currentStatus = workItemRecord.getValueString("status");

      if(!newStatus.equals(currentStatus))
      {
         workItemRecord.setValue("status", newStatus);
         workItemRecord.setValue("title", ghIssue.getTitle());

         UpdateInput updateInput = new UpdateInput();
         updateInput.setTableName(WorkItem.TABLE_NAME);
         updateInput.setRecords(List.of(workItemRecord));

         new UpdateAction().execute(updateInput);

         LOG.info("WorkItem status synced",
            new LogPair("workItemId", workItemId),
            new LogPair("oldStatus", currentStatus),
            new LogPair("newStatus", newStatus));
      }
      else
      {
         LOG.info("WorkItem status unchanged", new LogPair("workItemId", workItemId), new LogPair("status", currentStatus));
      }
   }



   /*******************************************************************************
    ** Map a GitHub issue state string to a Concilium WorkItem status.
    **
    ** @param gitHubState the state as returned by gh CLI ("OPEN", "CLOSED", etc.)
    ** @return the normalised Concilium status
    *******************************************************************************/
   String mapGitHubState(String gitHubState)
   {
      if(gitHubState == null)
      {
         return ("open");
      }

      return switch(gitHubState.toUpperCase())
      {
         case "CLOSED" -> "closed";
         case "OPEN" -> "open";
         default -> gitHubState.toLowerCase();
      };
   }
}
