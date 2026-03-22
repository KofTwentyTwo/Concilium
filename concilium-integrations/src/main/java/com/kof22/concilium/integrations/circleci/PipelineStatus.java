package com.kof22.concilium.integrations.circleci;


import java.util.ArrayList;
import java.util.List;


/*******************************************************************************
 ** Simple POJO representing the status of a CircleCI pipeline, including
 ** its nested workflow statuses.
 *******************************************************************************/
public class PipelineStatus
{
   private String pipelineId;
   private Integer pipelineNumber;
   private String state;
   private String branch;
   private List<WorkflowStatus> workflows;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public PipelineStatus()
   {
      this.workflows = new ArrayList<>();
   }



   /*******************************************************************************
    ** Getter for pipelineId
    *******************************************************************************/
   public String getPipelineId()
   {
      return (this.pipelineId);
   }



   /*******************************************************************************
    ** Setter for pipelineId
    *******************************************************************************/
   public void setPipelineId(String pipelineId)
   {
      this.pipelineId = pipelineId;
   }



   /*******************************************************************************
    ** Fluent setter for pipelineId
    *******************************************************************************/
   public PipelineStatus withPipelineId(String pipelineId)
   {
      this.pipelineId = pipelineId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for pipelineNumber
    *******************************************************************************/
   public Integer getPipelineNumber()
   {
      return (this.pipelineNumber);
   }



   /*******************************************************************************
    ** Setter for pipelineNumber
    *******************************************************************************/
   public void setPipelineNumber(Integer pipelineNumber)
   {
      this.pipelineNumber = pipelineNumber;
   }



   /*******************************************************************************
    ** Fluent setter for pipelineNumber
    *******************************************************************************/
   public PipelineStatus withPipelineNumber(Integer pipelineNumber)
   {
      this.pipelineNumber = pipelineNumber;
      return (this);
   }



   /*******************************************************************************
    ** Getter for state
    *******************************************************************************/
   public String getState()
   {
      return (this.state);
   }



   /*******************************************************************************
    ** Setter for state
    *******************************************************************************/
   public void setState(String state)
   {
      this.state = state;
   }



   /*******************************************************************************
    ** Fluent setter for state
    *******************************************************************************/
   public PipelineStatus withState(String state)
   {
      this.state = state;
      return (this);
   }



   /*******************************************************************************
    ** Getter for branch
    *******************************************************************************/
   public String getBranch()
   {
      return (this.branch);
   }



   /*******************************************************************************
    ** Setter for branch
    *******************************************************************************/
   public void setBranch(String branch)
   {
      this.branch = branch;
   }



   /*******************************************************************************
    ** Fluent setter for branch
    *******************************************************************************/
   public PipelineStatus withBranch(String branch)
   {
      this.branch = branch;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workflows
    *******************************************************************************/
   public List<WorkflowStatus> getWorkflows()
   {
      return (this.workflows);
   }



   /*******************************************************************************
    ** Setter for workflows
    *******************************************************************************/
   public void setWorkflows(List<WorkflowStatus> workflows)
   {
      this.workflows = workflows;
   }



   /*******************************************************************************
    ** Fluent setter for workflows
    *******************************************************************************/
   public PipelineStatus withWorkflows(List<WorkflowStatus> workflows)
   {
      this.workflows = workflows;
      return (this);
   }
}
