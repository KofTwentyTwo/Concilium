package com.kof22.concilium.integrations.circleci;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Unit tests for {@link CircleCIClient}.
 **
 ** These tests verify URL construction and POJO behaviour without making
 ** actual HTTP calls to the CircleCI API.
 *******************************************************************************/
class CircleCIClientTest
{
   private final CircleCIClient client = new CircleCIClient("fake-token", null);



   /*******************************************************************************
    ** Verify that the pipeline URL is constructed correctly for a standard
    ** project slug and branch.
    *******************************************************************************/
   @Test
   void testBuildPipelineUrl()
   {
      String url = client.buildPipelineUrl("gh/Kof22/Concilium", "main");

      assertThat(url).isEqualTo(
         "https://circleci.com/api/v2/project/gh/Kof22/Concilium/pipeline?branch=main"
      );
   }



   /*******************************************************************************
    ** Verify that the pipeline URL encodes special characters in the branch name.
    *******************************************************************************/
   @Test
   void testBuildPipelineUrlWithSpecialBranch()
   {
      String url = client.buildPipelineUrl("gh/Kof22/Concilium", "feature/epic-8");

      assertThat(url).isEqualTo(
         "https://circleci.com/api/v2/project/gh/Kof22/Concilium/pipeline?branch=feature%2Fepic-8"
      );
   }



   /*******************************************************************************
    ** Verify that the workflow jobs URL is constructed correctly.
    *******************************************************************************/
   @Test
   void testBuildWorkflowJobsUrl()
   {
      String url = client.buildWorkflowJobsUrl("abc-123-def");

      assertThat(url).isEqualTo(
         "https://circleci.com/api/v2/workflow/abc-123-def/job"
      );
   }



   /*******************************************************************************
    ** Verify that the BASE_URL constant points to CircleCI v2 API.
    *******************************************************************************/
   @Test
   void testBaseUrl()
   {
      assertThat(CircleCIClient.BASE_URL).isEqualTo("https://circleci.com/api/v2");
   }



   /*******************************************************************************
    ** Verify PipelineStatus POJO getters, setters, and fluent setters.
    *******************************************************************************/
   @Test
   void testPipelineStatusPojo()
   {
      List<WorkflowStatus> workflows = List.of(
         new WorkflowStatus()
            .withWorkflowId("wf-1")
            .withName("build-and-test")
            .withStatus("success")
      );

      PipelineStatus status = new PipelineStatus()
         .withPipelineId("pipeline-abc")
         .withPipelineNumber(42)
         .withState("created")
         .withBranch("main")
         .withWorkflows(workflows);

      assertThat(status.getPipelineId()).isEqualTo("pipeline-abc");
      assertThat(status.getPipelineNumber()).isEqualTo(42);
      assertThat(status.getState()).isEqualTo("created");
      assertThat(status.getBranch()).isEqualTo("main");
      assertThat(status.getWorkflows()).hasSize(1);
      assertThat(status.getWorkflows().get(0).getName()).isEqualTo("build-and-test");

      // Test standard setters
      status.setPipelineId("pipeline-xyz");
      status.setPipelineNumber(100);
      status.setState("errored");
      status.setBranch("develop");
      assertThat(status.getPipelineId()).isEqualTo("pipeline-xyz");
      assertThat(status.getPipelineNumber()).isEqualTo(100);
      assertThat(status.getState()).isEqualTo("errored");
      assertThat(status.getBranch()).isEqualTo("develop");
   }



   /*******************************************************************************
    ** Verify PipelineStatus default constructor initialises workflows list.
    *******************************************************************************/
   @Test
   void testPipelineStatusDefaultWorkflows()
   {
      PipelineStatus status = new PipelineStatus();

      assertThat(status.getWorkflows()).isNotNull();
      assertThat(status.getWorkflows()).isEmpty();
   }



   /*******************************************************************************
    ** Verify WorkflowStatus POJO getters, setters, and fluent setters.
    *******************************************************************************/
   @Test
   void testWorkflowStatusPojo()
   {
      WorkflowStatus wf = new WorkflowStatus()
         .withWorkflowId("wf-abc")
         .withName("deploy")
         .withStatus("running");

      assertThat(wf.getWorkflowId()).isEqualTo("wf-abc");
      assertThat(wf.getName()).isEqualTo("deploy");
      assertThat(wf.getStatus()).isEqualTo("running");

      // Test standard setters
      wf.setWorkflowId("wf-xyz");
      wf.setName("test");
      wf.setStatus("failed");
      assertThat(wf.getWorkflowId()).isEqualTo("wf-xyz");
      assertThat(wf.getName()).isEqualTo("test");
      assertThat(wf.getStatus()).isEqualTo("failed");
   }



   /*******************************************************************************
    ** Verify JobStatus POJO getters, setters, and fluent setters.
    *******************************************************************************/
   @Test
   void testJobStatusPojo()
   {
      JobStatus job = new JobStatus()
         .withJobName("build")
         .withStatus("success")
         .withJobNumber(7);

      assertThat(job.getJobName()).isEqualTo("build");
      assertThat(job.getStatus()).isEqualTo("success");
      assertThat(job.getJobNumber()).isEqualTo(7);

      // Test standard setters
      job.setJobName("test");
      job.setStatus("failed");
      job.setJobNumber(8);
      assertThat(job.getJobName()).isEqualTo("test");
      assertThat(job.getStatus()).isEqualTo("failed");
      assertThat(job.getJobNumber()).isEqualTo(8);
   }
}
