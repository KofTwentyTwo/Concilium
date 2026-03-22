package com.kof22.concilium.integrations.claude;


/*******************************************************************************
 ** POJO representing a request to execute Claude Code CLI. Uses a builder
 ** pattern for fluent construction. Contains the prompt, system prompt,
 ** working directory, allowed tools, max turns, and timeout configuration.
 *******************************************************************************/
public class ClaudeExecutionRequest
{
   private static final Integer DEFAULT_MAX_TURNS = 50;
   private static final Long    DEFAULT_TIMEOUT_MS = 600000L;

   private String  prompt;
   private String  systemPrompt;
   private String  workingDirectory;
   private String  allowedTools;
   private Integer maxTurns;
   private Long    timeoutMs;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public ClaudeExecutionRequest()
   {
      this.maxTurns = DEFAULT_MAX_TURNS;
      this.timeoutMs = DEFAULT_TIMEOUT_MS;
   }



   /*******************************************************************************
    ** Getter for prompt
    *******************************************************************************/
   public String getPrompt()
   {
      return (this.prompt);
   }



   /*******************************************************************************
    ** Setter for prompt
    *******************************************************************************/
   public void setPrompt(String prompt)
   {
      this.prompt = prompt;
   }



   /*******************************************************************************
    ** Fluent setter for prompt
    *******************************************************************************/
   public ClaudeExecutionRequest withPrompt(String prompt)
   {
      this.prompt = prompt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for systemPrompt
    *******************************************************************************/
   public String getSystemPrompt()
   {
      return (this.systemPrompt);
   }



   /*******************************************************************************
    ** Setter for systemPrompt
    *******************************************************************************/
   public void setSystemPrompt(String systemPrompt)
   {
      this.systemPrompt = systemPrompt;
   }



   /*******************************************************************************
    ** Fluent setter for systemPrompt
    *******************************************************************************/
   public ClaudeExecutionRequest withSystemPrompt(String systemPrompt)
   {
      this.systemPrompt = systemPrompt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workingDirectory
    *******************************************************************************/
   public String getWorkingDirectory()
   {
      return (this.workingDirectory);
   }



   /*******************************************************************************
    ** Setter for workingDirectory
    *******************************************************************************/
   public void setWorkingDirectory(String workingDirectory)
   {
      this.workingDirectory = workingDirectory;
   }



   /*******************************************************************************
    ** Fluent setter for workingDirectory
    *******************************************************************************/
   public ClaudeExecutionRequest withWorkingDirectory(String workingDirectory)
   {
      this.workingDirectory = workingDirectory;
      return (this);
   }



   /*******************************************************************************
    ** Getter for allowedTools
    *******************************************************************************/
   public String getAllowedTools()
   {
      return (this.allowedTools);
   }



   /*******************************************************************************
    ** Setter for allowedTools
    *******************************************************************************/
   public void setAllowedTools(String allowedTools)
   {
      this.allowedTools = allowedTools;
   }



   /*******************************************************************************
    ** Fluent setter for allowedTools
    *******************************************************************************/
   public ClaudeExecutionRequest withAllowedTools(String allowedTools)
   {
      this.allowedTools = allowedTools;
      return (this);
   }



   /*******************************************************************************
    ** Getter for maxTurns
    *******************************************************************************/
   public Integer getMaxTurns()
   {
      return (this.maxTurns);
   }



   /*******************************************************************************
    ** Setter for maxTurns
    *******************************************************************************/
   public void setMaxTurns(Integer maxTurns)
   {
      this.maxTurns = maxTurns;
   }



   /*******************************************************************************
    ** Fluent setter for maxTurns
    *******************************************************************************/
   public ClaudeExecutionRequest withMaxTurns(Integer maxTurns)
   {
      this.maxTurns = maxTurns;
      return (this);
   }



   /*******************************************************************************
    ** Getter for timeoutMs
    *******************************************************************************/
   public Long getTimeoutMs()
   {
      return (this.timeoutMs);
   }



   /*******************************************************************************
    ** Setter for timeoutMs
    *******************************************************************************/
   public void setTimeoutMs(Long timeoutMs)
   {
      this.timeoutMs = timeoutMs;
   }



   /*******************************************************************************
    ** Fluent setter for timeoutMs
    *******************************************************************************/
   public ClaudeExecutionRequest withTimeoutMs(Long timeoutMs)
   {
      this.timeoutMs = timeoutMs;
      return (this);
   }
}
