package com.kof22.concilium.integrations.claude;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;


/*******************************************************************************
 ** Spawns a Claude Code CLI process and captures structured JSON output.
 **
 ** Builds the CLI command from a {@link ClaudeExecutionRequest}, sets the
 ** working directory, captures stdout and stderr, waits for completion with
 ** a configurable timeout, and returns a {@link ClaudeExecutionResult}.
 *******************************************************************************/
public class ClaudeCodeExecutor
{
   private static final QLogger LOG = QLogger.getLogger(ClaudeCodeExecutor.class);



   /*******************************************************************************
    ** Execute a Claude Code CLI invocation based on the given request.
    **
    ** @param request the execution request containing prompt, system prompt,
    **                working directory, tools, max turns, and timeout
    ** @return a result containing the raw JSON output, exit code, and duration
    ** @throws QException if the request is invalid, the process fails to start,
    **                    or a timeout occurs
    *******************************************************************************/
   public ClaudeExecutionResult execute(ClaudeExecutionRequest request) throws QException
   {
      validateRequest(request);

      List<String> command = buildCommand(request);
      LOG.info("Executing Claude Code CLI", new LogPair("workingDirectory", request.getWorkingDirectory()), new LogPair("maxTurns", request.getMaxTurns()));

      long startTime = System.currentTimeMillis();

      try
      {
         ProcessBuilder processBuilder = new ProcessBuilder(command);
         processBuilder.redirectErrorStream(false);

         if(request.getWorkingDirectory() != null)
         {
            processBuilder.directory(new File(request.getWorkingDirectory()));
         }

         Process process = processBuilder.start();

         ///////////////////////////////////////////////////////////////////
         // Capture stdout and stderr in parallel to avoid buffer deadlock //
         ///////////////////////////////////////////////////////////////////
         StringBuilder stdout = new StringBuilder();
         StringBuilder stderr = new StringBuilder();

         Thread stdoutReader = new Thread(() -> readStream(process, stdout, true));
         Thread stderrReader = new Thread(() -> readStream(process, stderr, false));
         stdoutReader.start();
         stderrReader.start();

         Boolean completed = process.waitFor(request.getTimeoutMs(), TimeUnit.MILLISECONDS);

         if(!completed)
         {
            process.destroyForcibly();
            long durationMs = System.currentTimeMillis() - startTime;
            LOG.warn("Claude Code CLI timed out", new LogPair("timeoutMs", request.getTimeoutMs()), new LogPair("durationMs", durationMs));

            return new ClaudeExecutionResult()
               .withSuccess(false)
               .withErrorMessage("Process timed out after " + request.getTimeoutMs() + "ms")
               .withExitCode(-1)
               .withDurationMs(durationMs);
         }

         stdoutReader.join(5000);
         stderrReader.join(5000);

         Integer exitCode = process.exitValue();
         long durationMs = System.currentTimeMillis() - startTime;
         String rawOutput = stdout.toString();
         String errorOutput = stderr.toString();

         LOG.info("Claude Code CLI completed", new LogPair("exitCode", exitCode), new LogPair("durationMs", durationMs), new LogPair("outputLength", rawOutput.length()));

         if(exitCode != 0)
         {
            LOG.warn("Claude Code CLI exited with non-zero code", new LogPair("exitCode", exitCode), new LogPair("stderr", errorOutput));

            return new ClaudeExecutionResult()
               .withSuccess(false)
               .withRawOutput(rawOutput)
               .withErrorMessage("Process exited with code " + exitCode + ": " + errorOutput)
               .withExitCode(exitCode)
               .withDurationMs(durationMs);
         }

         return new ClaudeExecutionResult()
            .withSuccess(true)
            .withRawOutput(rawOutput)
            .withExitCode(exitCode)
            .withDurationMs(durationMs);
      }
      catch(InterruptedException e)
      {
         Thread.currentThread().interrupt();
         long durationMs = System.currentTimeMillis() - startTime;
         throw new QException("Claude Code execution was interrupted after " + durationMs + "ms", e);
      }
      catch(Exception e)
      {
         long durationMs = System.currentTimeMillis() - startTime;
         throw new QException("Failed to execute Claude Code CLI after " + durationMs + "ms: " + e.getMessage(), e);
      }
   }



   /*******************************************************************************
    ** Validate the execution request has required fields.
    *******************************************************************************/
   private void validateRequest(ClaudeExecutionRequest request) throws QException
   {
      if(request == null)
      {
         throw new QException("ClaudeExecutionRequest must not be null");
      }

      if(request.getPrompt() == null || request.getPrompt().isBlank())
      {
         throw new QException("ClaudeExecutionRequest prompt must not be null or blank");
      }
   }



   /*******************************************************************************
    ** Build the CLI command arguments from the request.
    **
    ** Produces a command like:
    ** claude -p --output-format json --permission-mode accept
    **   --max-turns {maxTurns} --allowedTools "{tools}"
    **   --system-prompt "{systemPrompt}" "{prompt}"
    *******************************************************************************/
   List<String> buildCommand(ClaudeExecutionRequest request)
   {
      List<String> command = new ArrayList<>();
      command.add("claude");
      command.add("-p");
      command.add("--output-format");
      command.add("json");
      command.add("--permission-mode");
      command.add("accept");

      if(request.getMaxTurns() != null)
      {
         command.add("--max-turns");
         command.add(String.valueOf(request.getMaxTurns()));
      }

      if(request.getAllowedTools() != null && !request.getAllowedTools().isBlank())
      {
         command.add("--allowedTools");
         command.add(request.getAllowedTools());
      }

      if(request.getSystemPrompt() != null && !request.getSystemPrompt().isBlank())
      {
         command.add("--system-prompt");
         command.add(request.getSystemPrompt());
      }

      command.add(request.getPrompt());

      return command;
   }



   /*******************************************************************************
    ** Read a stream (stdout or stderr) from the process into a StringBuilder.
    *******************************************************************************/
   private static void readStream(Process process, StringBuilder output, boolean isStdout)
   {
      try(BufferedReader reader = new BufferedReader(new InputStreamReader(
         isStdout ? process.getInputStream() : process.getErrorStream())))
      {
         String line;
         while((line = reader.readLine()) != null)
         {
            if(output.length() > 0)
            {
               output.append("\n");
            }
            output.append(line);
         }
      }
      catch(Exception e)
      {
         LOG.warn("Error reading process stream", new LogPair("isStdout", isStdout), new LogPair("error", e.getMessage()));
      }
   }
}
