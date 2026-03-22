package com.kof22.concilium.integrations.claude;


import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.logging.QLogger;


/*******************************************************************************
 ** Lightweight executor for Anthropic Haiku API calls. Intended for fast
 ** classification, summarization, and routing tasks that do not require the
 ** full Claude Code CLI environment.
 **
 ** This is a skeleton implementation. The Anthropic SDK dependency will be
 ** added in a future task, at which point this class will make direct HTTP
 ** calls to the Anthropic Messages API.
 *******************************************************************************/
public class ClaudeApiExecutor
{
   private static final QLogger LOG = QLogger.getLogger(ClaudeApiExecutor.class);



   /*******************************************************************************
    ** Classify or summarize text using a lightweight Anthropic model (e.g., Haiku).
    **
    ** @param prompt       the user prompt to classify or summarize
    ** @param systemPrompt the system-level instructions for the model
    ** @return the model's response text
    ** @throws QException always, until the Anthropic SDK is integrated
    *******************************************************************************/
   public String classify(String prompt, String systemPrompt) throws QException
   {
      throw new QException("ClaudeApiExecutor not yet implemented - use ClaudeCodeExecutor for now");
   }
}
