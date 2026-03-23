package com.kof22.concilium.integrations.circleci;


/*******************************************************************************
 ** Simple POJO representing the status of a CircleCI workflow within a
 ** pipeline.
 *******************************************************************************/
public class WorkflowStatus
{
   private String workflowId;
   private String name;
   private String status;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public WorkflowStatus()
   {
   }



   /*******************************************************************************
    ** Getter for workflowId
    *******************************************************************************/
   public String getWorkflowId()
   {
      return (this.workflowId);
   }



   /*******************************************************************************
    ** Setter for workflowId
    *******************************************************************************/
   public void setWorkflowId(String workflowId)
   {
      this.workflowId = workflowId;
   }



   /*******************************************************************************
    ** Fluent setter for workflowId
    *******************************************************************************/
   public WorkflowStatus withWorkflowId(String workflowId)
   {
      this.workflowId = workflowId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for name
    *******************************************************************************/
   public String getName()
   {
      return (this.name);
   }



   /*******************************************************************************
    ** Setter for name
    *******************************************************************************/
   public void setName(String name)
   {
      this.name = name;
   }



   /*******************************************************************************
    ** Fluent setter for name
    *******************************************************************************/
   public WorkflowStatus withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for status
    *******************************************************************************/
   public String getStatus()
   {
      return (this.status);
   }



   /*******************************************************************************
    ** Setter for status
    *******************************************************************************/
   public void setStatus(String status)
   {
      this.status = status;
   }



   /*******************************************************************************
    ** Fluent setter for status
    *******************************************************************************/
   public WorkflowStatus withStatus(String status)
   {
      this.status = status;
      return (this);
   }
}
