package com.kof22.concilium.orchestration.result;


import java.time.Instant;
import java.util.List;
import com.kingsrook.qqq.backend.core.actions.tables.InsertAction;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertOutput;
import com.kingsrook.qqq.backend.core.model.data.QRecord;
import com.kof22.concilium.integrations.claude.ClaudeExecutionResult;
import com.kof22.concilium.model.agent.AgentExecution;


/*******************************************************************************
 ** Processes the output from a Claude execution and persists it as an
 ** {@link AgentExecution} record via QQQ's InsertAction.
 **
 ** Captures the raw output, exit code, duration, status, and timing
 ** information into the agent_execution table for traceability and audit.
 *******************************************************************************/
public class ResultCaptureProcessor
{
   private static final QLogger LOG = QLogger.getLogger(ResultCaptureProcessor.class);



   /*******************************************************************************
    ** Capture the result of a Claude execution and persist it as an
    ** AgentExecution record.
    **
    ** @param agentId    the ID of the agent that was executed
    ** @param workItemId the ID of the work item (may be null)
    ** @param result     the execution result from ClaudeCodeExecutor
    ** @return the created AgentExecution record
    ** @throws QException if the insert fails
    *******************************************************************************/
   public AgentExecution captureResult(Long agentId, Long workItemId, ClaudeExecutionResult result) throws QException
   {
      LOG.info("Capturing execution result", new LogPair("agentId", agentId), new LogPair("workItemId", workItemId), new LogPair("success", result.getSuccess()));

      /////////////////////////////////////////////
      // Build the AgentExecution record         //
      /////////////////////////////////////////////
      String status = Boolean.TRUE.equals(result.getSuccess()) ? "completed" : "failed";
      Instant now = Instant.now();

      QRecord record = new QRecord()
         .withValue("agentId", agentId)
         .withValue("workItemId", workItemId)
         .withValue("structuredOutput", result.getRawOutput())
         .withValue("durationMs", result.getDurationMs())
         .withValue("status", status)
         .withValue("executionMode", "claude_code_cli")
         .withValue("startedAt", now.minusMillis(result.getDurationMs() != null ? result.getDurationMs() : 0))
         .withValue("completedAt", now);

      if(result.getErrorMessage() != null)
      {
         /////////////////////////////////////////////////////////
         // Store the error in the tool_calls_summary field for  //
         // visibility, since there's no dedicated error column  //
         /////////////////////////////////////////////////////////
         record.withValue("toolCallsSummary", "ERROR: " + result.getErrorMessage());
      }

      /////////////////////////////////////////////
      // Insert via QQQ                          //
      /////////////////////////////////////////////
      InsertInput insertInput = new InsertInput();
      insertInput.setTableName(AgentExecution.TABLE_NAME);
      insertInput.setRecords(List.of(record));

      InsertOutput insertOutput = new InsertAction().execute(insertInput);

      if(insertOutput.getRecords().isEmpty())
      {
         throw new QException("Failed to insert AgentExecution record for agentId: " + agentId);
      }

      QRecord insertedRecord = insertOutput.getRecords().get(0);
      AgentExecution agentExecution = buildAgentExecutionFromRecord(insertedRecord);

      LOG.info("Execution result captured", new LogPair("agentId", agentId), new LogPair("executionId", agentExecution.getId()), new LogPair("status", status));

      return agentExecution;
   }



   /*******************************************************************************
    ** Build an AgentExecution entity from a QRecord's values.
    *******************************************************************************/
   private AgentExecution buildAgentExecutionFromRecord(QRecord record)
   {
      AgentExecution execution = new AgentExecution();
      execution.setId(record.getValueLong("id"));
      execution.setAgentId(record.getValueLong("agentId"));
      execution.setWorkItemId(record.getValueLong("workItemId"));
      execution.setTaskPrompt(record.getValueString("taskPrompt"));
      execution.setStructuredOutput(record.getValueString("structuredOutput"));
      execution.setToolCallsSummary(record.getValueString("toolCallsSummary"));
      execution.setDurationMs(record.getValueLong("durationMs"));
      execution.setStatus(record.getValueString("status"));
      execution.setExecutionMode(record.getValueString("executionMode"));
      return execution;
   }
}
