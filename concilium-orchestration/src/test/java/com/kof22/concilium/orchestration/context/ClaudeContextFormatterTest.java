package com.kof22.concilium.orchestration.context;


import java.util.ArrayList;
import java.util.List;
import com.kof22.concilium.integrations.claude.ClaudeContextFormatter;
import com.kof22.concilium.model.agent.Agent;
import com.kof22.concilium.model.agent.AgentMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Unit tests for {@link ClaudeContextFormatter}, verifying that agent context
 ** is assembled correctly from system prompt, memories, and task description.
 *******************************************************************************/
class ClaudeContextFormatterTest
{


   /*******************************************************************************
    ** Verify that a fully populated context includes all sections.
    *******************************************************************************/
   @Test
   void testFormatContextWithAllSections()
   {
      Agent agent = new Agent()
         .withId(1L)
         .withName("Test Agent")
         .withSystemPromptTemplate("You are a repo agent for the backend service.");

      List<AgentMemory> memories = new ArrayList<>();
      memories.add(new AgentMemory()
         .withSubject("Architecture")
         .withContent("The backend uses a layered architecture with service, repository, and controller layers."));
      memories.add(new AgentMemory()
         .withSubject("Recent Changes")
         .withContent("Added pagination support to the query endpoint."));

      String taskDescription = "Implement the new health check endpoint.";

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, memories, taskDescription);

      assertThat(context).contains("You are a repo agent for the backend service.");
      assertThat(context).contains("## Memory");
      assertThat(context).contains("### Architecture");
      assertThat(context).contains("The backend uses a layered architecture");
      assertThat(context).contains("### Recent Changes");
      assertThat(context).contains("Added pagination support");
      assertThat(context).contains("## Task");
      assertThat(context).contains("Implement the new health check endpoint.");
   }



   /*******************************************************************************
    ** Verify that context works with no memories.
    *******************************************************************************/
   @Test
   void testFormatContextWithNoMemories()
   {
      Agent agent = new Agent()
         .withId(2L)
         .withName("Empty Agent")
         .withSystemPromptTemplate("You are a specialist agent.");

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, new ArrayList<>(), "Run the tests.");

      assertThat(context).contains("You are a specialist agent.");
      assertThat(context).doesNotContain("## Memory");
      assertThat(context).contains("## Task");
      assertThat(context).contains("Run the tests.");
   }



   /*******************************************************************************
    ** Verify that context works with null memories list.
    *******************************************************************************/
   @Test
   void testFormatContextWithNullMemories()
   {
      Agent agent = new Agent()
         .withId(3L)
         .withName("Null Memory Agent")
         .withSystemPromptTemplate("Base prompt.");

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, null, "Do the work.");

      assertThat(context).contains("Base prompt.");
      assertThat(context).doesNotContain("## Memory");
      assertThat(context).contains("## Task");
      assertThat(context).contains("Do the work.");
   }



   /*******************************************************************************
    ** Verify that context works with null system prompt template.
    *******************************************************************************/
   @Test
   void testFormatContextWithNullSystemPrompt()
   {
      Agent agent = new Agent()
         .withId(4L)
         .withName("No Prompt Agent")
         .withSystemPromptTemplate(null);

      List<AgentMemory> memories = new ArrayList<>();
      memories.add(new AgentMemory()
         .withSubject("Note")
         .withContent("Some context note."));

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, memories, "Task here.");

      assertThat(context).contains("## Memory");
      assertThat(context).contains("### Note");
      assertThat(context).contains("## Task");
      assertThat(context).contains("Task here.");
   }



   /*******************************************************************************
    ** Verify that context works with blank task description.
    *******************************************************************************/
   @Test
   void testFormatContextWithBlankTask()
   {
      Agent agent = new Agent()
         .withId(5L)
         .withName("Agent")
         .withSystemPromptTemplate("System prompt.");

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, new ArrayList<>(), "  ");

      assertThat(context).contains("System prompt.");
      assertThat(context).doesNotContain("## Task");
   }



   /*******************************************************************************
    ** Verify that memory entries with null subject are handled gracefully.
    *******************************************************************************/
   @Test
   void testFormatContextWithNullSubjectMemory()
   {
      Agent agent = new Agent()
         .withId(6L)
         .withName("Agent")
         .withSystemPromptTemplate("Prompt.");

      List<AgentMemory> memories = new ArrayList<>();
      memories.add(new AgentMemory()
         .withSubject(null)
         .withContent("Content without a subject heading."));

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, memories, "Task.");

      assertThat(context).contains("## Memory");
      assertThat(context).contains("Content without a subject heading.");
      assertThat(context).doesNotContain("### null");
   }



   /*******************************************************************************
    ** Verify that the order of sections is correct: prompt, memory, task.
    *******************************************************************************/
   @Test
   void testSectionOrdering()
   {
      Agent agent = new Agent()
         .withId(7L)
         .withName("Agent")
         .withSystemPromptTemplate("SYSTEM_PROMPT_START");

      List<AgentMemory> memories = new ArrayList<>();
      memories.add(new AgentMemory()
         .withSubject("Memory Subject")
         .withContent("MEMORY_CONTENT"));

      ClaudeContextFormatter formatter = new ClaudeContextFormatter();
      String context = formatter.formatContext(agent, memories, "TASK_DESCRIPTION");

      Integer promptIndex = context.indexOf("SYSTEM_PROMPT_START");
      Integer memoryIndex = context.indexOf("## Memory");
      Integer taskIndex = context.indexOf("## Task");

      assertThat(promptIndex).isLessThan(memoryIndex);
      assertThat(memoryIndex).isLessThan(taskIndex);
   }
}
