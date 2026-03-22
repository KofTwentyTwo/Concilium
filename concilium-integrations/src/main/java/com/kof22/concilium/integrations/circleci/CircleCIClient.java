package com.kof22.concilium.integrations.circleci;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;


/*******************************************************************************
 ** Client for the CircleCI v2 REST API.
 **
 ** Queries pipeline status and workflow/job details using the built-in
 ** {@link java.net.http.HttpClient} (Java 21).  Requires the environment
 ** variable {@code CIRCLECI_TOKEN} to be set with a valid API token.
 *******************************************************************************/
public class CircleCIClient
{
   private static final QLogger LOG = QLogger.getLogger(CircleCIClient.class);

   static final String BASE_URL = "https://circleci.com/api/v2";

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

   private final String apiToken;
   private final HttpClient httpClient;



   /*******************************************************************************
    ** Constructor -- reads the API token from the CIRCLECI_TOKEN environment
    ** variable.
    *******************************************************************************/
   public CircleCIClient()
   {
      this.apiToken = System.getenv("CIRCLECI_TOKEN");
      this.httpClient = HttpClient.newHttpClient();
   }



   /*******************************************************************************
    ** Constructor -- accepts an explicit API token and HttpClient (useful for
    ** testing).
    *******************************************************************************/
   public CircleCIClient(String apiToken, HttpClient httpClient)
   {
      this.apiToken = apiToken;
      this.httpClient = httpClient;
   }



   /*******************************************************************************
    ** Get the latest pipeline status for a project and branch.
    **
    ** Calls: GET /project/{projectSlug}/pipeline?branch={branch}
    **
    ** @param projectSlug the CircleCI project slug, e.g. "gh/Kof22/Concilium"
    ** @param branch      the git branch name
    ** @return the latest PipelineStatus, or null if no pipelines found
    ** @throws QException if the API call fails
    *******************************************************************************/
   public PipelineStatus getLatestPipelineStatus(String projectSlug, String branch) throws QException
   {
      String url = buildPipelineUrl(projectSlug, branch);

      LOG.info("Fetching latest pipeline status",
         new LogPair("projectSlug", projectSlug),
         new LogPair("branch", branch),
         new LogPair("url", url));

      String responseBody = executeGet(url);

      try
      {
         JsonNode root = OBJECT_MAPPER.readTree(responseBody);
         JsonNode items = root.get("items");

         if(items == null || !items.isArray() || items.isEmpty())
         {
            LOG.info("No pipelines found", new LogPair("projectSlug", projectSlug), new LogPair("branch", branch));
            return (null);
         }

         /////////////////////////////////////
         // Take the first (latest) pipeline //
         /////////////////////////////////////
         JsonNode latest = items.get(0);

         PipelineStatus status = new PipelineStatus()
            .withPipelineId(latest.has("id") ? latest.get("id").asText() : null)
            .withPipelineNumber(latest.has("number") ? latest.get("number").asInt() : null)
            .withState(latest.has("state") ? latest.get("state").asText() : null)
            .withBranch(branch);

         //////////////////////////////////////////////////////////////
         // If the pipeline has a vcs.branch field, prefer that      //
         //////////////////////////////////////////////////////////////
         if(latest.has("vcs") && latest.get("vcs").has("branch"))
         {
            status.setBranch(latest.get("vcs").get("branch").asText());
         }

         ///////////////////////////////////////////////////////////////////
         // Fetch workflows for this pipeline if we have a pipeline ID   //
         ///////////////////////////////////////////////////////////////////
         if(status.getPipelineId() != null)
         {
            List<WorkflowStatus> workflows = fetchWorkflowsForPipeline(status.getPipelineId());
            status.setWorkflows(workflows);
         }

         return (status);
      }
      catch(QException qe)
      {
         throw (qe);
      }
      catch(Exception e)
      {
         throw (new QException("Failed to parse pipeline status response", e));
      }
   }



   /*******************************************************************************
    ** Get the jobs for a specific workflow.
    **
    ** Calls: GET /workflow/{workflowId}/job
    **
    ** @param workflowId the CircleCI workflow ID
    ** @return list of JobStatus objects
    ** @throws QException if the API call fails
    *******************************************************************************/
   public List<JobStatus> getWorkflowJobs(String workflowId) throws QException
   {
      String url = buildWorkflowJobsUrl(workflowId);

      LOG.info("Fetching workflow jobs", new LogPair("workflowId", workflowId), new LogPair("url", url));

      String responseBody = executeGet(url);

      try
      {
         JsonNode root = OBJECT_MAPPER.readTree(responseBody);
         JsonNode items = root.get("items");

         List<JobStatus> jobs = new ArrayList<>();

         if(items != null && items.isArray())
         {
            for(JsonNode jobNode : items)
            {
               JobStatus job = new JobStatus()
                  .withJobName(jobNode.has("name") ? jobNode.get("name").asText() : null)
                  .withStatus(jobNode.has("status") ? jobNode.get("status").asText() : null)
                  .withJobNumber(jobNode.has("job_number") ? jobNode.get("job_number").asInt() : null);

               jobs.add(job);
            }
         }

         return (jobs);
      }
      catch(Exception e)
      {
         throw (new QException("Failed to parse workflow jobs response", e));
      }
   }



   /*******************************************************************************
    ** Build the URL for fetching pipelines for a project + branch.
    ** Package-private for testability.
    **
    ** @param projectSlug the CircleCI project slug
    ** @param branch      the branch name
    ** @return the full URL string
    *******************************************************************************/
   String buildPipelineUrl(String projectSlug, String branch)
   {
      return (BASE_URL + "/project/" + projectSlug + "/pipeline?branch=" + encodeQueryParam(branch));
   }



   /*******************************************************************************
    ** Build the URL for fetching jobs in a workflow.
    ** Package-private for testability.
    **
    ** @param workflowId the CircleCI workflow ID
    ** @return the full URL string
    *******************************************************************************/
   String buildWorkflowJobsUrl(String workflowId)
   {
      return (BASE_URL + "/workflow/" + workflowId + "/job");
   }



   /*******************************************************************************
    ** Fetch workflow statuses for a given pipeline ID.
    **
    ** Calls: GET /pipeline/{pipelineId}/workflow
    *******************************************************************************/
   private List<WorkflowStatus> fetchWorkflowsForPipeline(String pipelineId) throws QException
   {
      String url = BASE_URL + "/pipeline/" + pipelineId + "/workflow";

      String responseBody = executeGet(url);

      try
      {
         JsonNode root = OBJECT_MAPPER.readTree(responseBody);
         JsonNode items = root.get("items");

         List<WorkflowStatus> workflows = new ArrayList<>();

         if(items != null && items.isArray())
         {
            for(JsonNode wfNode : items)
            {
               WorkflowStatus wf = new WorkflowStatus()
                  .withWorkflowId(wfNode.has("id") ? wfNode.get("id").asText() : null)
                  .withName(wfNode.has("name") ? wfNode.get("name").asText() : null)
                  .withStatus(wfNode.has("status") ? wfNode.get("status").asText() : null);

               workflows.add(wf);
            }
         }

         return (workflows);
      }
      catch(Exception e)
      {
         throw (new QException("Failed to parse workflows response for pipeline " + pipelineId, e));
      }
   }



   /*******************************************************************************
    ** Execute an authenticated GET request against the CircleCI API.
    **
    ** @param url the full URL
    ** @return the response body as a String
    ** @throws QException if the request fails or returns a non-2xx status
    *******************************************************************************/
   private String executeGet(String url) throws QException
   {
      try
      {
         HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .GET();

         if(apiToken != null && !apiToken.isEmpty())
         {
            requestBuilder.header("Circle-Token", apiToken);
         }

         HttpRequest request = requestBuilder.build();
         HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

         if(response.statusCode() < 200 || response.statusCode() >= 300)
         {
            LOG.error("CircleCI API request failed",
               new LogPair("url", url),
               new LogPair("statusCode", response.statusCode()),
               new LogPair("body", response.body()));
            throw (new QException("CircleCI API returned status " + response.statusCode() + ": " + response.body()));
         }

         return (response.body());
      }
      catch(QException qe)
      {
         throw (qe);
      }
      catch(Exception e)
      {
         LOG.error("Error calling CircleCI API", e, new LogPair("url", url));
         throw (new QException("Error calling CircleCI API", e));
      }
   }



   /*******************************************************************************
    ** Minimal URL-encoding for a single query parameter value.
    *******************************************************************************/
   private String encodeQueryParam(String value)
   {
      if(value == null)
      {
         return ("");
      }
      return (value.replace(" ", "%20")
         .replace("/", "%2F")
         .replace("#", "%23"));
   }
}
