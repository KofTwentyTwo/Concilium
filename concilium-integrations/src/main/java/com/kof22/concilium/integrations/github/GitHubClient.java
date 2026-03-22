package com.kof22.concilium.integrations.github;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;


/*******************************************************************************
 ** Client that wraps the {@code gh} CLI for GitHub Issue operations.
 **
 ** Every method builds a {@code gh} command, executes it via
 ** {@link ProcessBuilder}, parses the JSON output, and returns a result or
 ** throws {@link QException} on failure.  The {@code gh} CLI must be installed
 ** and authenticated on the host machine.
 *******************************************************************************/
public class GitHubClient
{
   private static final QLogger LOG = QLogger.getLogger(GitHubClient.class);

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();



   /*******************************************************************************
    ** Create a new GitHub Issue in the given repository.
    **
    ** @param repo   the owner/repo slug, e.g. "Kof22/Concilium"
    ** @param title  the issue title
    ** @param body   the issue body (Markdown)
    ** @param labels optional list of label names to apply
    ** @return the created GitHubIssue
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public GitHubIssue createIssue(String repo, String title, String body, List<String> labels) throws QException
   {
      List<String> command = new ArrayList<>();
      command.add("gh");
      command.add("issue");
      command.add("create");
      command.add("--repo");
      command.add(repo);
      command.add("--title");
      command.add(title);
      command.add("--body");
      command.add(body);

      if(labels != null && !labels.isEmpty())
      {
         command.add("--label");
         command.add(String.join(",", labels));
      }

      command.add("--json");
      command.add("number,title,body,state,url,labels,assignees");

      LOG.info("Creating GitHub issue", new LogPair("repo", repo), new LogPair("title", title));

      String output = executeCommand(command);
      return (parseIssue(output));
   }



   /*******************************************************************************
    ** Get a single GitHub Issue by number.
    **
    ** @param repo        the owner/repo slug
    ** @param issueNumber the issue number
    ** @return the GitHubIssue
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public GitHubIssue getIssue(String repo, Integer issueNumber) throws QException
   {
      List<String> command = List.of(
         "gh", "issue", "view",
         String.valueOf(issueNumber),
         "--repo", repo,
         "--json", "number,title,body,state,url,labels,assignees"
      );

      LOG.info("Getting GitHub issue", new LogPair("repo", repo), new LogPair("issueNumber", issueNumber));

      String output = executeCommand(command);
      return (parseIssue(output));
   }



   /*******************************************************************************
    ** Update the title and/or body of a GitHub Issue.
    **
    ** @param repo        the owner/repo slug
    ** @param issueNumber the issue number
    ** @param title       new title (may be null to leave unchanged)
    ** @param body        new body (may be null to leave unchanged)
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public void updateIssue(String repo, Integer issueNumber, String title, String body) throws QException
   {
      List<String> command = new ArrayList<>();
      command.add("gh");
      command.add("issue");
      command.add("edit");
      command.add(String.valueOf(issueNumber));
      command.add("--repo");
      command.add(repo);

      if(title != null)
      {
         command.add("--title");
         command.add(title);
      }

      if(body != null)
      {
         command.add("--body");
         command.add(body);
      }

      LOG.info("Updating GitHub issue", new LogPair("repo", repo), new LogPair("issueNumber", issueNumber));

      executeCommand(command);
   }



   /*******************************************************************************
    ** Close a GitHub Issue.
    **
    ** @param repo        the owner/repo slug
    ** @param issueNumber the issue number
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public void closeIssue(String repo, Integer issueNumber) throws QException
   {
      List<String> command = List.of(
         "gh", "issue", "close",
         String.valueOf(issueNumber),
         "--repo", repo
      );

      LOG.info("Closing GitHub issue", new LogPair("repo", repo), new LogPair("issueNumber", issueNumber));

      executeCommand(command);
   }



   /*******************************************************************************
    ** Add a comment to a GitHub Issue.
    **
    ** @param repo        the owner/repo slug
    ** @param issueNumber the issue number
    ** @param comment     the comment body (Markdown)
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public void addComment(String repo, Integer issueNumber, String comment) throws QException
   {
      List<String> command = List.of(
         "gh", "issue", "comment",
         String.valueOf(issueNumber),
         "--repo", repo,
         "--body", comment
      );

      LOG.info("Adding comment to GitHub issue", new LogPair("repo", repo), new LogPair("issueNumber", issueNumber));

      executeCommand(command);
   }



   /*******************************************************************************
    ** List issues in a repository, optionally filtered by state and labels.
    **
    ** @param repo   the owner/repo slug
    ** @param state  issue state filter: "open", "closed", or "all" (null defaults to "open")
    ** @param labels optional label filter
    ** @return list of matching GitHubIssue objects
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public List<GitHubIssue> listIssues(String repo, String state, List<String> labels) throws QException
   {
      List<String> command = new ArrayList<>();
      command.add("gh");
      command.add("issue");
      command.add("list");
      command.add("--repo");
      command.add(repo);

      if(state != null)
      {
         command.add("--state");
         command.add(state);
      }

      if(labels != null && !labels.isEmpty())
      {
         command.add("--label");
         command.add(String.join(",", labels));
      }

      command.add("--json");
      command.add("number,title,body,state,url,labels,assignees");

      LOG.info("Listing GitHub issues", new LogPair("repo", repo), new LogPair("state", state));

      String output = executeCommand(command);
      return (parseIssueList(output));
   }



   /*******************************************************************************
    ** Add labels to an existing GitHub Issue.
    **
    ** @param repo        the owner/repo slug
    ** @param issueNumber the issue number
    ** @param labels      label names to add
    ** @throws QException if the gh CLI call fails
    *******************************************************************************/
   public void addLabels(String repo, Integer issueNumber, List<String> labels) throws QException
   {
      List<String> command = new ArrayList<>();
      command.add("gh");
      command.add("issue");
      command.add("edit");
      command.add(String.valueOf(issueNumber));
      command.add("--repo");
      command.add(repo);
      command.add("--add-label");
      command.add(String.join(",", labels));

      LOG.info("Adding labels to GitHub issue", new LogPair("repo", repo), new LogPair("issueNumber", issueNumber), new LogPair("labels", labels));

      executeCommand(command);
   }



   /*******************************************************************************
    ** Build a command list for a given operation.  This is exposed as
    ** package-private so that unit tests can verify the command structure
    ** without actually executing the gh CLI.
    **
    ** @param args the arguments to pass to the gh CLI
    ** @return the full command list
    *******************************************************************************/
   List<String> buildCommand(String... args)
   {
      List<String> command = new ArrayList<>();
      command.add("gh");
      for(String arg : args)
      {
         command.add(arg);
      }
      return (command);
   }



   /*******************************************************************************
    ** Execute a gh CLI command and return stdout as a String.
    **
    ** @param command the full command list
    ** @return the stdout output
    ** @throws QException if the process exits non-zero
    *******************************************************************************/
   String executeCommand(List<String> command) throws QException
   {
      try
      {
         ProcessBuilder processBuilder = new ProcessBuilder(command);
         processBuilder.redirectErrorStream(false);

         Process process = processBuilder.start();

         String stdout;
         try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)))
         {
            stdout = reader.lines().collect(Collectors.joining("\n"));
         }

         String stderr;
         try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8)))
         {
            stderr = reader.lines().collect(Collectors.joining("\n"));
         }

         int exitCode = process.waitFor();

         if(exitCode != 0)
         {
            LOG.error("gh CLI command failed", new LogPair("exitCode", exitCode), new LogPair("stderr", stderr), new LogPair("command", String.join(" ", command)));
            throw (new QException("gh CLI command failed with exit code " + exitCode + ": " + stderr));
         }

         return (stdout);
      }
      catch(QException qe)
      {
         throw (qe);
      }
      catch(Exception e)
      {
         LOG.error("Error executing gh CLI command", e, new LogPair("command", String.join(" ", command)));
         throw (new QException("Error executing gh CLI command", e));
      }
   }



   /*******************************************************************************
    ** Parse a single GitHub Issue from JSON output.
    **
    ** @param json the JSON string from gh CLI
    ** @return a GitHubIssue
    ** @throws QException if JSON parsing fails
    *******************************************************************************/
   GitHubIssue parseIssue(String json) throws QException
   {
      try
      {
         JsonNode node = OBJECT_MAPPER.readTree(json);
         return (nodeToIssue(node));
      }
      catch(Exception e)
      {
         throw (new QException("Failed to parse GitHub issue JSON", e));
      }
   }



   /*******************************************************************************
    ** Parse a list of GitHub Issues from JSON array output.
    **
    ** @param json the JSON array string from gh CLI
    ** @return list of GitHubIssue objects
    ** @throws QException if JSON parsing fails
    *******************************************************************************/
   List<GitHubIssue> parseIssueList(String json) throws QException
   {
      try
      {
         JsonNode arrayNode = OBJECT_MAPPER.readTree(json);
         List<GitHubIssue> issues = new ArrayList<>();

         if(arrayNode.isArray())
         {
            for(JsonNode node : arrayNode)
            {
               issues.add(nodeToIssue(node));
            }
         }

         return (issues);
      }
      catch(Exception e)
      {
         throw (new QException("Failed to parse GitHub issue list JSON", e));
      }
   }



   /*******************************************************************************
    ** Convert a single JsonNode to a GitHubIssue.
    *******************************************************************************/
   private GitHubIssue nodeToIssue(JsonNode node)
   {
      GitHubIssue issue = new GitHubIssue();

      if(node.has("number"))
      {
         issue.setNumber(node.get("number").asInt());
      }

      if(node.has("title"))
      {
         issue.setTitle(node.get("title").asText());
      }

      if(node.has("body"))
      {
         issue.setBody(node.get("body").asText());
      }

      if(node.has("state"))
      {
         issue.setState(node.get("state").asText());
      }

      if(node.has("url"))
      {
         issue.setUrl(node.get("url").asText());
      }

      if(node.has("labels") && node.get("labels").isArray())
      {
         List<String> labels = new ArrayList<>();
         for(JsonNode labelNode : node.get("labels"))
         {
            if(labelNode.has("name"))
            {
               labels.add(labelNode.get("name").asText());
            }
            else if(labelNode.isTextual())
            {
               labels.add(labelNode.asText());
            }
         }
         issue.setLabels(labels);
      }

      if(node.has("assignees") && node.get("assignees").isArray() && node.get("assignees").size() > 0)
      {
         JsonNode firstAssignee = node.get("assignees").get(0);
         if(firstAssignee.has("login"))
         {
            issue.setAssignee(firstAssignee.get("login").asText());
         }
         else if(firstAssignee.isTextual())
         {
            issue.setAssignee(firstAssignee.asText());
         }
      }

      return (issue);
   }
}
