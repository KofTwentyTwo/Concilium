package com.kof22.concilium.events;


import java.time.Instant;


/*******************************************************************************
 ** Base event class for Concilium real-time events.
 **
 ** Represents a single event occurrence within the system, suitable for
 ** serialization to JSON and broadcast to WebSocket clients.  Each event
 ** carries the event type, the affected entity, an optional master project
 ** scope, and a JSON payload with event-specific details.
 *******************************************************************************/
public class ConciliumEvent
{
   private String  eventType;
   private String  entityType;
   private Long    entityId;
   private Long    masterProjectId;
   private String  payload;
   private Instant timestamp;



   /*******************************************************************************
    ** Default constructor.
    *******************************************************************************/
   public ConciliumEvent()
   {
   }



   /*******************************************************************************
    ** Full constructor.
    *******************************************************************************/
   public ConciliumEvent(String eventType, String entityType, Long entityId, Long masterProjectId, String payload, Instant timestamp)
   {
      this.eventType = eventType;
      this.entityType = entityType;
      this.entityId = entityId;
      this.masterProjectId = masterProjectId;
      this.payload = payload;
      this.timestamp = timestamp;
   }



   /*******************************************************************************
    ** Getter for eventType
    *******************************************************************************/
   public String getEventType()
   {
      return (this.eventType);
   }



   /*******************************************************************************
    ** Setter for eventType
    *******************************************************************************/
   public void setEventType(String eventType)
   {
      this.eventType = eventType;
   }



   /*******************************************************************************
    ** Fluent setter for eventType
    *******************************************************************************/
   public ConciliumEvent withEventType(String eventType)
   {
      this.eventType = eventType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for entityType
    *******************************************************************************/
   public String getEntityType()
   {
      return (this.entityType);
   }



   /*******************************************************************************
    ** Setter for entityType
    *******************************************************************************/
   public void setEntityType(String entityType)
   {
      this.entityType = entityType;
   }



   /*******************************************************************************
    ** Fluent setter for entityType
    *******************************************************************************/
   public ConciliumEvent withEntityType(String entityType)
   {
      this.entityType = entityType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for entityId
    *******************************************************************************/
   public Long getEntityId()
   {
      return (this.entityId);
   }



   /*******************************************************************************
    ** Setter for entityId
    *******************************************************************************/
   public void setEntityId(Long entityId)
   {
      this.entityId = entityId;
   }



   /*******************************************************************************
    ** Fluent setter for entityId
    *******************************************************************************/
   public ConciliumEvent withEntityId(Long entityId)
   {
      this.entityId = entityId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for masterProjectId
    *******************************************************************************/
   public Long getMasterProjectId()
   {
      return (this.masterProjectId);
   }



   /*******************************************************************************
    ** Setter for masterProjectId
    *******************************************************************************/
   public void setMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
   }



   /*******************************************************************************
    ** Fluent setter for masterProjectId
    *******************************************************************************/
   public ConciliumEvent withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for payload
    *******************************************************************************/
   public String getPayload()
   {
      return (this.payload);
   }



   /*******************************************************************************
    ** Setter for payload
    *******************************************************************************/
   public void setPayload(String payload)
   {
      this.payload = payload;
   }



   /*******************************************************************************
    ** Fluent setter for payload
    *******************************************************************************/
   public ConciliumEvent withPayload(String payload)
   {
      this.payload = payload;
      return (this);
   }



   /*******************************************************************************
    ** Getter for timestamp
    *******************************************************************************/
   public Instant getTimestamp()
   {
      return (this.timestamp);
   }



   /*******************************************************************************
    ** Setter for timestamp
    *******************************************************************************/
   public void setTimestamp(Instant timestamp)
   {
      this.timestamp = timestamp;
   }



   /*******************************************************************************
    ** Fluent setter for timestamp
    *******************************************************************************/
   public ConciliumEvent withTimestamp(Instant timestamp)
   {
      this.timestamp = timestamp;
      return (this);
   }
}
