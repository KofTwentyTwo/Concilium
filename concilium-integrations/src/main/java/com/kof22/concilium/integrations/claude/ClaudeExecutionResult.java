package com.kof22.concilium.integrations.claude;


/*******************************************************************************
 ** POJO representing the result of a Claude Code CLI execution. Contains the
 ** success flag, raw JSON output, error message (if any), exit code, and
 ** execution duration in milliseconds.
 *******************************************************************************/
public class ClaudeExecutionResult
{
   private Boolean success;
   private String  rawOutput;
   private String  errorMessage;
   private Integer exitCode;
   private Long    durationMs;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public ClaudeExecutionResult()
   {
   }



   /*******************************************************************************
    ** Getter for success
    *******************************************************************************/
   public Boolean getSuccess()
   {
      return (this.success);
   }



   /*******************************************************************************
    ** Setter for success
    *******************************************************************************/
   public void setSuccess(Boolean success)
   {
      this.success = success;
   }



   /*******************************************************************************
    ** Fluent setter for success
    *******************************************************************************/
   public ClaudeExecutionResult withSuccess(Boolean success)
   {
      this.success = success;
      return (this);
   }



   /*******************************************************************************
    ** Getter for rawOutput
    *******************************************************************************/
   public String getRawOutput()
   {
      return (this.rawOutput);
   }



   /*******************************************************************************
    ** Setter for rawOutput
    *******************************************************************************/
   public void setRawOutput(String rawOutput)
   {
      this.rawOutput = rawOutput;
   }



   /*******************************************************************************
    ** Fluent setter for rawOutput
    *******************************************************************************/
   public ClaudeExecutionResult withRawOutput(String rawOutput)
   {
      this.rawOutput = rawOutput;
      return (this);
   }



   /*******************************************************************************
    ** Getter for errorMessage
    *******************************************************************************/
   public String getErrorMessage()
   {
      return (this.errorMessage);
   }



   /*******************************************************************************
    ** Setter for errorMessage
    *******************************************************************************/
   public void setErrorMessage(String errorMessage)
   {
      this.errorMessage = errorMessage;
   }



   /*******************************************************************************
    ** Fluent setter for errorMessage
    *******************************************************************************/
   public ClaudeExecutionResult withErrorMessage(String errorMessage)
   {
      this.errorMessage = errorMessage;
      return (this);
   }



   /*******************************************************************************
    ** Getter for exitCode
    *******************************************************************************/
   public Integer getExitCode()
   {
      return (this.exitCode);
   }



   /*******************************************************************************
    ** Setter for exitCode
    *******************************************************************************/
   public void setExitCode(Integer exitCode)
   {
      this.exitCode = exitCode;
   }



   /*******************************************************************************
    ** Fluent setter for exitCode
    *******************************************************************************/
   public ClaudeExecutionResult withExitCode(Integer exitCode)
   {
      this.exitCode = exitCode;
      return (this);
   }



   /*******************************************************************************
    ** Getter for durationMs
    *******************************************************************************/
   public Long getDurationMs()
   {
      return (this.durationMs);
   }



   /*******************************************************************************
    ** Setter for durationMs
    *******************************************************************************/
   public void setDurationMs(Long durationMs)
   {
      this.durationMs = durationMs;
   }



   /*******************************************************************************
    ** Fluent setter for durationMs
    *******************************************************************************/
   public ClaudeExecutionResult withDurationMs(Long durationMs)
   {
      this.durationMs = durationMs;
      return (this);
   }
}
