package com.kof22.concilium.integrations.circleci;


/*******************************************************************************
 ** Simple POJO representing the status of a single CircleCI job within a
 ** workflow.
 *******************************************************************************/
public class JobStatus
{
   private String  jobName;
   private String  status;
   private Integer jobNumber;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public JobStatus()
   {
   }



   /*******************************************************************************
    ** Getter for jobName
    *******************************************************************************/
   public String getJobName()
   {
      return (this.jobName);
   }



   /*******************************************************************************
    ** Setter for jobName
    *******************************************************************************/
   public void setJobName(String jobName)
   {
      this.jobName = jobName;
   }



   /*******************************************************************************
    ** Fluent setter for jobName
    *******************************************************************************/
   public JobStatus withJobName(String jobName)
   {
      this.jobName = jobName;
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
   public JobStatus withStatus(String status)
   {
      this.status = status;
      return (this);
   }



   /*******************************************************************************
    ** Getter for jobNumber
    *******************************************************************************/
   public Integer getJobNumber()
   {
      return (this.jobNumber);
   }



   /*******************************************************************************
    ** Setter for jobNumber
    *******************************************************************************/
   public void setJobNumber(Integer jobNumber)
   {
      this.jobNumber = jobNumber;
   }



   /*******************************************************************************
    ** Fluent setter for jobNumber
    *******************************************************************************/
   public JobStatus withJobNumber(Integer jobNumber)
   {
      this.jobNumber = jobNumber;
      return (this);
   }
}
