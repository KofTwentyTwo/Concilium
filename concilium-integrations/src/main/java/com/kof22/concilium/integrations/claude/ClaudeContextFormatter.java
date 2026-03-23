package com.kof22.concilium.integrations.claude;


import java.util.List;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kof22.concilium.model.agent.Agent;
import com.kof22.concilium.model.agent.AgentMemory;


/*******************************************************************************
 ** Assembles the system prompt for a Claude agent invocation by combining the
 ** agent's system prompt template, relevant memory entries, and the current
 ** task description into a single formatted context string.
 *******************************************************************************/
public class ClaudeContextFormatter
{
   private static final QLogger LOG = QLogger.getLogger(ClaudeContextFormatter.class);



   /*******************************************************************************
    ** Format a complete context string for an agent invocation.
    **
    ** The assembled context follows this structure:
    ** 1. The agent's systemPromptTemplate (identity and base instructions)
    ** 2. A "## Memory" section with relevant memory entries
    ** 3. A "## Task" section with the current task description
    **
    ** @param agent           the agent whose system prompt template to use
    ** @param memories        the relevant memory entries, ordered by relevance
    ** @param taskDescription the description of the current task to execute
    ** @return the fully assembled context string
    *******************************************************************************/
   public String formatContext(Agent agent, List<AgentMemory> memories, String taskDescription)
   {
      LOG.info("Formatting agent context", new LogPair("agentId", agent.getId()), new LogPair("agentName", agent.getName()), new LogPair("memoryCount", memories != null ? memories.size() : 0));

      StringBuilder context = new StringBuilder();

      /////////////////////////////////////////////
      // Start with the agent's system prompt    //
      /////////////////////////////////////////////
      if(agent.getSystemPromptTemplate() != null && !agent.getSystemPromptTemplate().isBlank())
      {
         context.append(agent.getSystemPromptTemplate());
      }

      /////////////////////////////////////////////
      // Append memory section if memories exist //
      /////////////////////////////////////////////
      if(memories != null && !memories.isEmpty())
      {
         context.append("\n\n## Memory\n\n");

         for(AgentMemory memory : memories)
         {
            if(memory.getSubject() != null)
            {
               context.append("### ").append(memory.getSubject()).append("\n");
            }
            if(memory.getContent() != null)
            {
               context.append(memory.getContent()).append("\n\n");
            }
         }
      }

      /////////////////////////////////////////////
      // Append task section                     //
      /////////////////////////////////////////////
      if(taskDescription != null && !taskDescription.isBlank())
      {
         context.append("\n\n## Task\n\n");
         context.append(taskDescription);
      }

      return context.toString();
   }
}
