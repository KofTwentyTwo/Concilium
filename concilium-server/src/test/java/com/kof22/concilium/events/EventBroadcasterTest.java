package com.kof22.concilium.events;


import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Unit tests for {@link EventBroadcaster} and {@link ConciliumEvent}.
 *******************************************************************************/
class EventBroadcasterTest
{


   /*******************************************************************************
    ** Verify ConciliumEvent construction via fluent setters and getters.
    *******************************************************************************/
   @Test
   void testConciliumEventConstruction()
   {
      Instant now = Instant.parse("2026-03-22T12:00:00Z");

      ConciliumEvent event = new ConciliumEvent()
         .withEventType(EventType.AGENT_EXECUTION_STARTED)
         .withEntityType("agentExecution")
         .withEntityId(42L)
         .withMasterProjectId(1L)
         .withPayload("{\"agentName\":\"repo-agent-concilium\"}")
         .withTimestamp(now);

      assertThat(event.getEventType()).isEqualTo("agent.execution.started");
      assertThat(event.getEntityType()).isEqualTo("agentExecution");
      assertThat(event.getEntityId()).isEqualTo(42L);
      assertThat(event.getMasterProjectId()).isEqualTo(1L);
      assertThat(event.getPayload()).isEqualTo("{\"agentName\":\"repo-agent-concilium\"}");
      assertThat(event.getTimestamp()).isEqualTo(now);
   }



   /*******************************************************************************
    ** Verify ConciliumEvent construction via full constructor.
    *******************************************************************************/
   @Test
   void testConciliumEventFullConstructor()
   {
      Instant now = Instant.now();

      ConciliumEvent event = new ConciliumEvent(
         EventType.AGENT_EXECUTION_COMPLETED,
         "agentExecution",
         99L,
         2L,
         "{\"durationMs\":1500}",
         now
      );

      assertThat(event.getEventType()).isEqualTo("agent.execution.completed");
      assertThat(event.getEntityType()).isEqualTo("agentExecution");
      assertThat(event.getEntityId()).isEqualTo(99L);
      assertThat(event.getMasterProjectId()).isEqualTo(2L);
      assertThat(event.getPayload()).isEqualTo("{\"durationMs\":1500}");
      assertThat(event.getTimestamp()).isEqualTo(now);
   }



   /*******************************************************************************
    ** Verify ConciliumEvent serializes to JSON with ISO-8601 timestamps.
    *******************************************************************************/
   @Test
   void testEventSerializesToJson() throws Exception
   {
      Instant timestamp = Instant.parse("2026-03-22T15:30:00Z");

      ConciliumEvent event = new ConciliumEvent()
         .withEventType(EventType.WORK_ITEM_STATUS_CHANGED)
         .withEntityType("workItem")
         .withEntityId(7L)
         .withMasterProjectId(1L)
         .withPayload("{\"oldStatus\":\"open\",\"newStatus\":\"in_progress\"}")
         .withTimestamp(timestamp);

      ObjectMapper objectMapper = EventBroadcaster.getObjectMapper();
      String json = objectMapper.writeValueAsString(event);

      assertThat(json).contains("\"eventType\":\"workItem.statusChanged\"");
      assertThat(json).contains("\"entityType\":\"workItem\"");
      assertThat(json).contains("\"entityId\":7");
      assertThat(json).contains("\"masterProjectId\":1");
      assertThat(json).contains("\"timestamp\":\"2026-03-22T15:30:00Z\"");
      assertThat(json).contains("\"payload\":");
   }



   /*******************************************************************************
    ** Verify ConciliumEvent round-trips through JSON serialization.
    *******************************************************************************/
   @Test
   void testEventRoundTripsJson() throws Exception
   {
      Instant timestamp = Instant.parse("2026-03-22T10:00:00Z");

      ConciliumEvent original = new ConciliumEvent()
         .withEventType(EventType.AGENT_MESSAGE_SENT)
         .withEntityType("agentMessage")
         .withEntityId(123L)
         .withMasterProjectId(5L)
         .withPayload("{\"to\":\"master-agent\"}")
         .withTimestamp(timestamp);

      ObjectMapper objectMapper = EventBroadcaster.getObjectMapper();
      String json = objectMapper.writeValueAsString(original);
      ConciliumEvent deserialized = objectMapper.readValue(json, ConciliumEvent.class);

      assertThat(deserialized.getEventType()).isEqualTo(original.getEventType());
      assertThat(deserialized.getEntityType()).isEqualTo(original.getEntityType());
      assertThat(deserialized.getEntityId()).isEqualTo(original.getEntityId());
      assertThat(deserialized.getMasterProjectId()).isEqualTo(original.getMasterProjectId());
      assertThat(deserialized.getPayload()).isEqualTo(original.getPayload());
      assertThat(deserialized.getTimestamp()).isEqualTo(original.getTimestamp());
   }



   /*******************************************************************************
    ** Verify client count starts at zero with no connected clients.
    *******************************************************************************/
   @Test
   void testClientCountStartsAtZero()
   {
      assertThat(EventBroadcaster.getClientCount()).isEqualTo(0);
   }



   /*******************************************************************************
    ** Verify EventType constants have expected values.
    *******************************************************************************/
   @Test
   void testEventTypeConstants()
   {
      assertThat(EventType.AGENT_EXECUTION_STARTED).isEqualTo("agent.execution.started");
      assertThat(EventType.AGENT_EXECUTION_TOOL_CALL).isEqualTo("agent.execution.toolCall");
      assertThat(EventType.AGENT_EXECUTION_COMPLETED).isEqualTo("agent.execution.completed");
      assertThat(EventType.AGENT_MESSAGE_SENT).isEqualTo("agent.message.sent");
      assertThat(EventType.WORK_ITEM_STATUS_CHANGED).isEqualTo("workItem.statusChanged");
      assertThat(EventType.WORKFLOW_STEP_COMPLETED).isEqualTo("workflow.stepCompleted");
   }



   /*******************************************************************************
    ** Verify serialization handles null fields gracefully.
    *******************************************************************************/
   @Test
   void testEventSerializesWithNullFields() throws Exception
   {
      ConciliumEvent event = new ConciliumEvent()
         .withEventType(EventType.WORKFLOW_STEP_COMPLETED);

      ObjectMapper objectMapper = EventBroadcaster.getObjectMapper();
      String json = objectMapper.writeValueAsString(event);

      assertThat(json).contains("\"eventType\":\"workflow.stepCompleted\"");
      assertThat(json).contains("\"entityType\":null");
      assertThat(json).contains("\"entityId\":null");
   }
}
