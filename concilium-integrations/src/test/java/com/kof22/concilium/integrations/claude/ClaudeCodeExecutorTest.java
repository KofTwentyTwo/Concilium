package com.kof22.concilium.integrations.claude;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/*******************************************************************************
 ** Unit tests for {@link ClaudeCodeExecutor}, verifying command-line argument
 ** construction and request/result POJO behavior.
 *******************************************************************************/
class ClaudeCodeExecutorTest
{


   /*******************************************************************************
    ** Verify that the command builder produces the correct CLI arguments for a
    ** fully populated request.
    *******************************************************************************/
   @Test
   void testBuildCommandFullRequest()
   {
      ClaudeExecutionRequest request = new ClaudeExecutionRequest()
         .withPrompt("Implement the feature")
         .withSystemPrompt("You are a helpful assistant")
         .withWorkingDirectory("/tmp/test")
         .withAllowedTools("Read,Write,Bash")
         .withMaxTurns(25);

      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();
      List<String> command = executor.buildCommand(request);

      assertThat(command).containsExactly(
         "claude",
         "-p",
         "--output-format",
         "json",
         "--permission-mode",
         "accept",
         "--max-turns",
         "25",
         "--allowedTools",
         "Read,Write,Bash",
         "--system-prompt",
         "You are a helpful assistant",
         "Implement the feature"
      );
   }



   /*******************************************************************************
    ** Verify that the command builder handles a minimal request with only the
    ** required prompt field.
    *******************************************************************************/
   @Test
   void testBuildCommandMinimalRequest()
   {
      ClaudeExecutionRequest request = new ClaudeExecutionRequest()
         .withPrompt("Do something");

      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();
      List<String> command = executor.buildCommand(request);

      assertThat(command).containsExactly(
         "claude",
         "-p",
         "--output-format",
         "json",
         "--permission-mode",
         "accept",
         "--max-turns",
         "50",
         "Do something"
      );
   }



   /*******************************************************************************
    ** Verify that null tools and null system prompt are omitted from the command.
    *******************************************************************************/
   @Test
   void testBuildCommandOmitsNullOptionalFields()
   {
      ClaudeExecutionRequest request = new ClaudeExecutionRequest()
         .withPrompt("Test prompt")
         .withAllowedTools(null)
         .withSystemPrompt(null)
         .withMaxTurns(10);

      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();
      List<String> command = executor.buildCommand(request);

      assertThat(command).doesNotContain("--allowedTools");
      assertThat(command).doesNotContain("--system-prompt");
      assertThat(command).contains("--max-turns", "10");
   }



   /*******************************************************************************
    ** Verify that blank tools and blank system prompt are omitted from the command.
    *******************************************************************************/
   @Test
   void testBuildCommandOmitsBlankOptionalFields()
   {
      ClaudeExecutionRequest request = new ClaudeExecutionRequest()
         .withPrompt("Test prompt")
         .withAllowedTools("  ")
         .withSystemPrompt("  ");

      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();
      List<String> command = executor.buildCommand(request);

      assertThat(command).doesNotContain("--allowedTools");
      assertThat(command).doesNotContain("--system-prompt");
   }



   /*******************************************************************************
    ** Verify that execute rejects a null request.
    *******************************************************************************/
   @Test
   void testExecuteRejectsNullRequest()
   {
      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();

      assertThatThrownBy(() -> executor.execute(null))
         .hasMessageContaining("must not be null");
   }



   /*******************************************************************************
    ** Verify that execute rejects a request with a blank prompt.
    *******************************************************************************/
   @Test
   void testExecuteRejectsBlankPrompt()
   {
      ClaudeCodeExecutor executor = new ClaudeCodeExecutor();
      ClaudeExecutionRequest request = new ClaudeExecutionRequest()
         .withPrompt("  ");

      assertThatThrownBy(() -> executor.execute(request))
         .hasMessageContaining("prompt must not be null or blank");
   }



   /*******************************************************************************
    ** Verify ClaudeExecutionRequest default values.
    *******************************************************************************/
   @Test
   void testRequestDefaults()
   {
      ClaudeExecutionRequest request = new ClaudeExecutionRequest();

      assertThat(request.getMaxTurns()).isEqualTo(50);
      assertThat(request.getTimeoutMs()).isEqualTo(600000L);
      assertThat(request.getPrompt()).isNull();
      assertThat(request.getSystemPrompt()).isNull();
      assertThat(request.getWorkingDirectory()).isNull();
      assertThat(request.getAllowedTools()).isNull();
   }



   /*******************************************************************************
    ** Verify ClaudeExecutionResult fluent setters and getters.
    *******************************************************************************/
   @Test
   void testResultPojoRoundTrip()
   {
      ClaudeExecutionResult result = new ClaudeExecutionResult()
         .withSuccess(true)
         .withRawOutput("{\"result\":\"ok\"}")
         .withErrorMessage(null)
         .withExitCode(0)
         .withDurationMs(5000L);

      assertThat(result.getSuccess()).isTrue();
      assertThat(result.getRawOutput()).isEqualTo("{\"result\":\"ok\"}");
      assertThat(result.getErrorMessage()).isNull();
      assertThat(result.getExitCode()).isEqualTo(0);
      assertThat(result.getDurationMs()).isEqualTo(5000L);
   }



   /*******************************************************************************
    ** Verify ClaudeExecutionResult represents a failure correctly.
    *******************************************************************************/
   @Test
   void testResultPojoFailure()
   {
      ClaudeExecutionResult result = new ClaudeExecutionResult()
         .withSuccess(false)
         .withRawOutput("")
         .withErrorMessage("Process timed out")
         .withExitCode(-1)
         .withDurationMs(600000L);

      assertThat(result.getSuccess()).isFalse();
      assertThat(result.getErrorMessage()).isEqualTo("Process timed out");
      assertThat(result.getExitCode()).isEqualTo(-1);
   }
}
