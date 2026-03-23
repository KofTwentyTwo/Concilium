package com.kof22.concilium.events;


/*******************************************************************************
 ** Constants for Concilium real-time event type identifiers.
 **
 ** Each constant follows the convention {@code domain.entity.action} to provide
 ** a clear, hierarchical namespace for event filtering and routing.
 *******************************************************************************/
public final class EventType
{
   public static final String AGENT_EXECUTION_STARTED   = "agent.execution.started";
   public static final String AGENT_EXECUTION_TOOL_CALL = "agent.execution.toolCall";
   public static final String AGENT_EXECUTION_COMPLETED = "agent.execution.completed";
   public static final String AGENT_MESSAGE_SENT        = "agent.message.sent";
   public static final String WORK_ITEM_STATUS_CHANGED  = "workItem.statusChanged";
   public static final String WORKFLOW_STEP_COMPLETED   = "workflow.stepCompleted";



   /*******************************************************************************
    ** Private constructor -- utility class, not instantiable.
    *******************************************************************************/
   private EventType()
   {
   }
}
