package com.kof22.concilium.model.communication;


import java.time.Instant;

import com.kingsrook.qqq.backend.core.model.data.QField;
import com.kingsrook.qqq.backend.core.model.data.QRecordEntity;
import com.kingsrook.qqq.backend.core.model.metadata.fields.DynamicDefaultValueBehavior;
import com.kingsrook.qqq.backend.core.model.metadata.producers.annotations.QMetaDataProducingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = AgentMessage.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class AgentMessage extends QRecordEntity
{
   public static final String TABLE_NAME = "agent_message";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(possibleValueSourceName = "master_project")
   private Long masterProjectId;

   @Column(name = "sender_agent_id", nullable = false)
   @QField(possibleValueSourceName = "agent")
   private Long senderAgentId;

   @Column(name = "recipient_agent_id", nullable = false)
   @QField(possibleValueSourceName = "agent")
   private Long recipientAgentId;

   @Column(name = "work_item_id")
   @QField(possibleValueSourceName = "work_item")
   private Long workItemId;

   @Column(name = "message_type", nullable = false, length = 50)
   @QField(maxLength = 50)
   private String messageType;

   @Column(name = "payload", length = 100000)
   @QField(maxLength = 100000)
   private String payload;

   @Column(name = "referenced_artifacts", length = 10000)
   @QField(maxLength = 10000)
   private String referencedArtifacts;

   @Column(name = "status", nullable = false, length = 50)
   @QField(maxLength = 50, defaultValue = "pending")
   private String status;

   @Column(name = "create_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;

   @Column(name = "modify_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.MODIFY_DATE)
   private Instant modifyDate;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public AgentMessage()
   {
   }



   /*******************************************************************************
    ** Getter for id
    *******************************************************************************/
   public Long getId()
   {
      return (this.id);
   }



   /*******************************************************************************
    ** Setter for id
    *******************************************************************************/
   public void setId(Long id)
   {
      this.id = id;
   }



   /*******************************************************************************
    ** Fluent setter for id
    *******************************************************************************/
   public AgentMessage withId(Long id)
   {
      this.id = id;
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
   public AgentMessage withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for senderAgentId
    *******************************************************************************/
   public Long getSenderAgentId()
   {
      return (this.senderAgentId);
   }



   /*******************************************************************************
    ** Setter for senderAgentId
    *******************************************************************************/
   public void setSenderAgentId(Long senderAgentId)
   {
      this.senderAgentId = senderAgentId;
   }



   /*******************************************************************************
    ** Fluent setter for senderAgentId
    *******************************************************************************/
   public AgentMessage withSenderAgentId(Long senderAgentId)
   {
      this.senderAgentId = senderAgentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for recipientAgentId
    *******************************************************************************/
   public Long getRecipientAgentId()
   {
      return (this.recipientAgentId);
   }



   /*******************************************************************************
    ** Setter for recipientAgentId
    *******************************************************************************/
   public void setRecipientAgentId(Long recipientAgentId)
   {
      this.recipientAgentId = recipientAgentId;
   }



   /*******************************************************************************
    ** Fluent setter for recipientAgentId
    *******************************************************************************/
   public AgentMessage withRecipientAgentId(Long recipientAgentId)
   {
      this.recipientAgentId = recipientAgentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workItemId
    *******************************************************************************/
   public Long getWorkItemId()
   {
      return (this.workItemId);
   }



   /*******************************************************************************
    ** Setter for workItemId
    *******************************************************************************/
   public void setWorkItemId(Long workItemId)
   {
      this.workItemId = workItemId;
   }



   /*******************************************************************************
    ** Fluent setter for workItemId
    *******************************************************************************/
   public AgentMessage withWorkItemId(Long workItemId)
   {
      this.workItemId = workItemId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for messageType
    *******************************************************************************/
   public String getMessageType()
   {
      return (this.messageType);
   }



   /*******************************************************************************
    ** Setter for messageType
    *******************************************************************************/
   public void setMessageType(String messageType)
   {
      this.messageType = messageType;
   }



   /*******************************************************************************
    ** Fluent setter for messageType
    *******************************************************************************/
   public AgentMessage withMessageType(String messageType)
   {
      this.messageType = messageType;
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
   public AgentMessage withPayload(String payload)
   {
      this.payload = payload;
      return (this);
   }



   /*******************************************************************************
    ** Getter for referencedArtifacts
    *******************************************************************************/
   public String getReferencedArtifacts()
   {
      return (this.referencedArtifacts);
   }



   /*******************************************************************************
    ** Setter for referencedArtifacts
    *******************************************************************************/
   public void setReferencedArtifacts(String referencedArtifacts)
   {
      this.referencedArtifacts = referencedArtifacts;
   }



   /*******************************************************************************
    ** Fluent setter for referencedArtifacts
    *******************************************************************************/
   public AgentMessage withReferencedArtifacts(String referencedArtifacts)
   {
      this.referencedArtifacts = referencedArtifacts;
      return (this);
   }



   /*******************************************************************************
    ** Getter for status
    *******************************************************************************/
   public String getStatus()
   {
      return (this.status);
   }



   /*******************************************************************************
    ** Setter for status
    *******************************************************************************/
   public void setStatus(String status)
   {
      this.status = status;
   }



   /*******************************************************************************
    ** Fluent setter for status
    *******************************************************************************/
   public AgentMessage withStatus(String status)
   {
      this.status = status;
      return (this);
   }



   /*******************************************************************************
    ** Getter for createDate
    *******************************************************************************/
   public Instant getCreateDate()
   {
      return (this.createDate);
   }



   /*******************************************************************************
    ** Setter for createDate
    *******************************************************************************/
   public void setCreateDate(Instant createDate)
   {
      this.createDate = createDate;
   }



   /*******************************************************************************
    ** Fluent setter for createDate
    *******************************************************************************/
   public AgentMessage withCreateDate(Instant createDate)
   {
      this.createDate = createDate;
      return (this);
   }



   /*******************************************************************************
    ** Getter for modifyDate
    *******************************************************************************/
   public Instant getModifyDate()
   {
      return (this.modifyDate);
   }



   /*******************************************************************************
    ** Setter for modifyDate
    *******************************************************************************/
   public void setModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
   }



   /*******************************************************************************
    ** Fluent setter for modifyDate
    *******************************************************************************/
   public AgentMessage withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
