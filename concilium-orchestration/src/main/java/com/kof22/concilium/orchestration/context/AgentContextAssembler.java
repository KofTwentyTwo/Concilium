package com.kof22.concilium.orchestration.context;


import java.util.ArrayList;
import java.util.List;
import com.kingsrook.qqq.backend.core.actions.tables.QueryAction;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QCriteriaOperator;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QFilterCriteria;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QFilterOrderBy;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QQueryFilter;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QueryInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QueryOutput;
import com.kingsrook.qqq.backend.core.model.data.QRecord;
import com.kof22.concilium.integrations.claude.ClaudeContextFormatter;
import com.kof22.concilium.model.agent.Agent;
import com.kof22.concilium.model.agent.AgentMemory;


/*******************************************************************************
 ** Orchestration-level class that queries agent memory and delegates to
 ** {@link ClaudeContextFormatter} to assemble a full context string for an
 ** agent invocation.
 **
 ** Loads the Agent record, retrieves the top relevant memories by
 ** relevanceScore, and passes everything to the formatter.
 *******************************************************************************/
public class AgentContextAssembler
{
   private static final QLogger LOG = QLogger.getLogger(AgentContextAssembler.class);

   private static final Integer MEMORY_LIMIT = 20;

   private ClaudeContextFormatter contextFormatter;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public AgentContextAssembler()
   {
      this.contextFormatter = new ClaudeContextFormatter();
   }



   /*******************************************************************************
    ** Constructor that accepts a custom formatter for testing.
    *******************************************************************************/
   public AgentContextAssembler(ClaudeContextFormatter contextFormatter)
   {
      this.contextFormatter = contextFormatter;
   }



   /*******************************************************************************
    ** Assemble a full context string for the given agent and task.
    **
    ** 1. Loads the Agent record by ID
    ** 2. Queries AgentMemory for this agent, ordered by relevanceScore DESC,
    **    limited to the top 20 entries
    ** 3. Passes to ClaudeContextFormatter
    ** 4. Returns the assembled context string
    **
    ** @param agentId         the ID of the agent to assemble context for
    ** @param taskDescription the description of the task to execute
    ** @return the fully assembled context string
    ** @throws QException if the agent is not found or a query fails
    *******************************************************************************/
   public String assembleContext(Long agentId, String taskDescription) throws QException
   {
      LOG.info("Assembling agent context", new LogPair("agentId", agentId));

      /////////////////////////////////////////////
      // Load the agent record                   //
      /////////////////////////////////////////////
      Agent agent = loadAgent(agentId);

      /////////////////////////////////////////////
      // Query relevant memories                 //
      /////////////////////////////////////////////
      List<AgentMemory> memories = loadMemories(agentId);

      LOG.info("Loaded agent memories", new LogPair("agentId", agentId), new LogPair("memoryCount", memories.size()));

      /////////////////////////////////////////////
      // Delegate to formatter                   //
      /////////////////////////////////////////////
      return contextFormatter.formatContext(agent, memories, taskDescription);
   }



   /*******************************************************************************
    ** Load the Agent record by ID.
    *******************************************************************************/
   private Agent loadAgent(Long agentId) throws QException
   {
      QueryInput queryInput = new QueryInput();
      queryInput.setTableName(Agent.TABLE_NAME);
      queryInput.setFilter(new QQueryFilter()
         .withCriteria(new QFilterCriteria("id", QCriteriaOperator.EQUALS, agentId))
         .withLimit(1));

      QueryOutput queryOutput = new QueryAction().execute(queryInput);

      if(queryOutput.getRecords().isEmpty())
      {
         throw new QException("Agent not found with id: " + agentId);
      }

      QRecord record = queryOutput.getRecords().get(0);
      return buildAgentFromRecord(record);
   }



   /*******************************************************************************
    ** Build an Agent entity from a QRecord's values.
    *******************************************************************************/
   private Agent buildAgentFromRecord(QRecord record)
   {
      return new Agent()
         .withId(record.getValueLong("id"))
         .withName(record.getValueString("name"))
         .withAgentType(record.getValueString("agentType"))
         .withSystemPromptTemplate(record.getValueString("systemPromptTemplate"))
         .withMasterProjectId(record.getValueLong("masterProjectId"))
         .withRepositoryId(record.getValueLong("repositoryId"))
         .withModelId(record.getValueString("modelId"))
         .withMaxTurns(record.getValueInteger("maxTurns"))
         .withToolPermissions(record.getValueString("toolPermissions"))
         .withApprovalPolicy(record.getValueString("approvalPolicy"))
         .withStatus(record.getValueString("status"));
   }



   /*******************************************************************************
    ** Load relevant memory entries for the given agent, ordered by relevance.
    *******************************************************************************/
   private List<AgentMemory> loadMemories(Long agentId) throws QException
   {
      QQueryFilter filter = new QQueryFilter()
         .withCriteria(new QFilterCriteria("agentId", QCriteriaOperator.EQUALS, agentId))
         .withOrderBy(new QFilterOrderBy("relevanceScore", false))
         .withLimit(MEMORY_LIMIT);

      QueryInput queryInput = new QueryInput();
      queryInput.setTableName(AgentMemory.TABLE_NAME);
      queryInput.setFilter(filter);

      QueryOutput queryOutput = new QueryAction().execute(queryInput);

      List<AgentMemory> memories = new ArrayList<>();
      for(QRecord record : queryOutput.getRecords())
      {
         memories.add(buildAgentMemoryFromRecord(record));
      }
      return memories;
   }



   /*******************************************************************************
    ** Build an AgentMemory entity from a QRecord's values.
    *******************************************************************************/
   private AgentMemory buildAgentMemoryFromRecord(QRecord record)
   {
      return new AgentMemory()
         .withId(record.getValueLong("id"))
         .withAgentId(record.getValueLong("agentId"))
         .withMemoryType(record.getValueString("memoryType"))
         .withCategory(record.getValueString("category"))
         .withSubject(record.getValueString("subject"))
         .withContent(record.getValueString("content"))
         .withRelevanceScore(record.getValueInteger("relevanceScore"));
   }
}
