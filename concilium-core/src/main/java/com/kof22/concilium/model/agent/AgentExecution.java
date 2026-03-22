package com.kof22.concilium.model.agent;


import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.kingsrook.qqq.backend.core.model.data.QField;
import com.kingsrook.qqq.backend.core.model.data.QRecordEntity;
import com.kingsrook.qqq.backend.core.model.metadata.fields.DynamicDefaultValueBehavior;
import com.kingsrook.qqq.backend.core.model.metadata.producers.annotations.QMetaDataProducingEntity;


/*******************************************************************************
 ** QRecordEntity for the agent_execution table.
 **
 ** Records each discrete execution of an agent, including the task prompt,
 ** structured output, tool call summary, token usage, and timing. Linked
 ** to the originating agent and optionally to a work item.
 *******************************************************************************/
@Entity
@Table(name = AgentExecution.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class AgentExecution extends QRecordEntity
{
   public static final String TABLE_NAME = "agent_execution";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "agent_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = Agent.TABLE_NAME)
   private Long agentId;

   @Column(name = "work_item_id")
   @QField(possibleValueSourceName = "work_item")
   private Long workItemId;

   @Column(name = "task_prompt")
   @QField(maxLength = 50000)
   private String taskPrompt;

   @Column(name = "prompt_hash")
   @QField(maxLength = 64)
   private String promptHash;

   @Column(name = "structured_output")
   @QField(maxLength = 500000)
   private String structuredOutput;

   @Column(name = "tool_calls_summary")
   @QField(maxLength = 50000)
   private String toolCallsSummary;

   @Column(name = "token_usage")
   @QField()
   private Long tokenUsage;

   @Column(name = "duration_ms")
   @QField()
   private Long durationMs;

   @Column(name = "cost_estimate")
   @QField(maxLength = 50)
   private String costEstimate;

   @Column(name = "status", nullable = false)
   @QField(isRequired = true, maxLength = 50)
   private String status;

   @Column(name = "execution_mode")
   @QField(maxLength = 50)
   private String executionMode;

   @Column(name = "started_at")
   @QField()
   private Instant startedAt;

   @Column(name = "completed_at")
   @QField()
   private Instant completedAt;

   @Column(name = "create_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;

   @Column(name = "modify_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.MODIFY_DATE)
   private Instant modifyDate;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public AgentExecution()
   {
   }



   /*******************************************************************************
    ** Getter for id
    *******************************************************************************/
   public Long getId()
   {
      return (this.id);
   }



   /*******************************************************************************
    ** Setter for id
    *******************************************************************************/
   public void setId(Long id)
   {
      this.id = id;
   }



   /*******************************************************************************
    ** Fluent setter for id
    *******************************************************************************/
   public AgentExecution withId(Long id)
   {
      this.id = id;
      return (this);
   }



   /*******************************************************************************
    ** Getter for agentId
    *******************************************************************************/
   public Long getAgentId()
   {
      return (this.agentId);
   }



   /*******************************************************************************
    ** Setter for agentId
    *******************************************************************************/
   public void setAgentId(Long agentId)
   {
      this.agentId = agentId;
   }



   /*******************************************************************************
    ** Fluent setter for agentId
    *******************************************************************************/
   public AgentExecution withAgentId(Long agentId)
   {
      this.agentId = agentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workItemId
    *******************************************************************************/
   public Long getWorkItemId()
   {
      return (this.workItemId);
   }



   /*******************************************************************************
    ** Setter for workItemId
    *******************************************************************************/
   public void setWorkItemId(Long workItemId)
   {
      this.workItemId = workItemId;
   }



   /*******************************************************************************
    ** Fluent setter for workItemId
    *******************************************************************************/
   public AgentExecution withWorkItemId(Long workItemId)
   {
      this.workItemId = workItemId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for taskPrompt
    *******************************************************************************/
   public String getTaskPrompt()
   {
      return (this.taskPrompt);
   }



   /*******************************************************************************
    ** Setter for taskPrompt
    *******************************************************************************/
   public void setTaskPrompt(String taskPrompt)
   {
      this.taskPrompt = taskPrompt;
   }



   /*******************************************************************************
    ** Fluent setter for taskPrompt
    *******************************************************************************/
   public AgentExecution withTaskPrompt(String taskPrompt)
   {
      this.taskPrompt = taskPrompt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for promptHash
    *******************************************************************************/
   public String getPromptHash()
   {
      return (this.promptHash);
   }



   /*******************************************************************************
    ** Setter for promptHash
    *******************************************************************************/
   public void setPromptHash(String promptHash)
   {
      this.promptHash = promptHash;
   }



   /*******************************************************************************
    ** Fluent setter for promptHash
    *******************************************************************************/
   public AgentExecution withPromptHash(String promptHash)
   {
      this.promptHash = promptHash;
      return (this);
   }



   /*******************************************************************************
    ** Getter for structuredOutput
    *******************************************************************************/
   public String getStructuredOutput()
   {
      return (this.structuredOutput);
   }



   /*******************************************************************************
    ** Setter for structuredOutput
    *******************************************************************************/
   public void setStructuredOutput(String structuredOutput)
   {
      this.structuredOutput = structuredOutput;
   }



   /*******************************************************************************
    ** Fluent setter for structuredOutput
    *******************************************************************************/
   public AgentExecution withStructuredOutput(String structuredOutput)
   {
      this.structuredOutput = structuredOutput;
      return (this);
   }



   /*******************************************************************************
    ** Getter for toolCallsSummary
    *******************************************************************************/
   public String getToolCallsSummary()
   {
      return (this.toolCallsSummary);
   }



   /*******************************************************************************
    ** Setter for toolCallsSummary
    *******************************************************************************/
   public void setToolCallsSummary(String toolCallsSummary)
   {
      this.toolCallsSummary = toolCallsSummary;
   }



   /*******************************************************************************
    ** Fluent setter for toolCallsSummary
    *******************************************************************************/
   public AgentExecution withToolCallsSummary(String toolCallsSummary)
   {
      this.toolCallsSummary = toolCallsSummary;
      return (this);
   }



   /*******************************************************************************
    ** Getter for tokenUsage
    *******************************************************************************/
   public Long getTokenUsage()
   {
      return (this.tokenUsage);
   }



   /*******************************************************************************
    ** Setter for tokenUsage
    *******************************************************************************/
   public void setTokenUsage(Long tokenUsage)
   {
      this.tokenUsage = tokenUsage;
   }



   /*******************************************************************************
    ** Fluent setter for tokenUsage
    *******************************************************************************/
   public AgentExecution withTokenUsage(Long tokenUsage)
   {
      this.tokenUsage = tokenUsage;
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
   public AgentExecution withDurationMs(Long durationMs)
   {
      this.durationMs = durationMs;
      return (this);
   }



   /*******************************************************************************
    ** Getter for costEstimate
    *******************************************************************************/
   public String getCostEstimate()
   {
      return (this.costEstimate);
   }



   /*******************************************************************************
    ** Setter for costEstimate
    *******************************************************************************/
   public void setCostEstimate(String costEstimate)
   {
      this.costEstimate = costEstimate;
   }



   /*******************************************************************************
    ** Fluent setter for costEstimate
    *******************************************************************************/
   public AgentExecution withCostEstimate(String costEstimate)
   {
      this.costEstimate = costEstimate;
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
   public AgentExecution withStatus(String status)
   {
      this.status = status;
      return (this);
   }



   /*******************************************************************************
    ** Getter for executionMode
    *******************************************************************************/
   public String getExecutionMode()
   {
      return (this.executionMode);
   }



   /*******************************************************************************
    ** Setter for executionMode
    *******************************************************************************/
   public void setExecutionMode(String executionMode)
   {
      this.executionMode = executionMode;
   }



   /*******************************************************************************
    ** Fluent setter for executionMode
    *******************************************************************************/
   public AgentExecution withExecutionMode(String executionMode)
   {
      this.executionMode = executionMode;
      return (this);
   }



   /*******************************************************************************
    ** Getter for startedAt
    *******************************************************************************/
   public Instant getStartedAt()
   {
      return (this.startedAt);
   }



   /*******************************************************************************
    ** Setter for startedAt
    *******************************************************************************/
   public void setStartedAt(Instant startedAt)
   {
      this.startedAt = startedAt;
   }



   /*******************************************************************************
    ** Fluent setter for startedAt
    *******************************************************************************/
   public AgentExecution withStartedAt(Instant startedAt)
   {
      this.startedAt = startedAt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for completedAt
    *******************************************************************************/
   public Instant getCompletedAt()
   {
      return (this.completedAt);
   }



   /*******************************************************************************
    ** Setter for completedAt
    *******************************************************************************/
   public void setCompletedAt(Instant completedAt)
   {
      this.completedAt = completedAt;
   }



   /*******************************************************************************
    ** Fluent setter for completedAt
    *******************************************************************************/
   public AgentExecution withCompletedAt(Instant completedAt)
   {
      this.completedAt = completedAt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for createDate
    *******************************************************************************/
   public Instant getCreateDate()
   {
      return (this.createDate);
   }



   /*******************************************************************************
    ** Setter for createDate
    *******************************************************************************/
   public void setCreateDate(Instant createDate)
   {
      this.createDate = createDate;
   }



   /*******************************************************************************
    ** Fluent setter for createDate
    *******************************************************************************/
   public AgentExecution withCreateDate(Instant createDate)
   {
      this.createDate = createDate;
      return (this);
   }



   /*******************************************************************************
    ** Getter for modifyDate
    *******************************************************************************/
   public Instant getModifyDate()
   {
      return (this.modifyDate);
   }



   /*******************************************************************************
    ** Setter for modifyDate
    *******************************************************************************/
   public void setModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
   }



   /*******************************************************************************
    ** Fluent setter for modifyDate
    *******************************************************************************/
   public AgentExecution withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
