package com.kof22.concilium.model.audit;


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
@Table(name = AuditEntry.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class AuditEntry extends QRecordEntity
{
   public static final String TABLE_NAME = "audit_entry";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id")
   @QField(possibleValueSourceName = "master_project")
   private Long masterProjectId;

   @Column(name = "agent_id")
   @QField(possibleValueSourceName = "agent")
   private Long agentId;

   @Column(name = "agent_execution_id")
   @QField(possibleValueSourceName = "agent_execution")
   private Long agentExecutionId;

   @Column(name = "work_item_id")
   @QField(possibleValueSourceName = "work_item")
   private Long workItemId;

   @Column(name = "entity_type", nullable = false, length = 100)
   @QField(maxLength = 100)
   private String entityType;

   @Column(name = "entity_id")
   private Long entityId;

   @Column(name = "action", nullable = false, length = 50)
   @QField(maxLength = 50)
   private String action;

   @Column(name = "details", length = 10000)
   @QField(maxLength = 10000)
   private String details;

   @Column(name = "changes_json", length = 50000)
   @QField(maxLength = 50000)
   private String changesJson;

   @Column(name = "create_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public AuditEntry()
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
   public AuditEntry withId(Long id)
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
   public AuditEntry withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for agentId
    *******************************************************************************/
   public Long getAgentId()
   {
      return (this.agentId);
   }



   /*******************************************************************************
    ** Setter for agentId
    *******************************************************************************/
   public void setAgentId(Long agentId)
   {
      this.agentId = agentId;
   }



   /*******************************************************************************
    ** Fluent setter for agentId
    *******************************************************************************/
   public AuditEntry withAgentId(Long agentId)
   {
      this.agentId = agentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for agentExecutionId
    *******************************************************************************/
   public Long getAgentExecutionId()
   {
      return (this.agentExecutionId);
   }



   /*******************************************************************************
    ** Setter for agentExecutionId
    *******************************************************************************/
   public void setAgentExecutionId(Long agentExecutionId)
   {
      this.agentExecutionId = agentExecutionId;
   }



   /*******************************************************************************
    ** Fluent setter for agentExecutionId
    *******************************************************************************/
   public AuditEntry withAgentExecutionId(Long agentExecutionId)
   {
      this.agentExecutionId = agentExecutionId;
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
   public AuditEntry withWorkItemId(Long workItemId)
   {
      this.workItemId = workItemId;
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
   public AuditEntry withEntityType(String entityType)
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
   public AuditEntry withEntityId(Long entityId)
   {
      this.entityId = entityId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for action
    *******************************************************************************/
   public String getAction()
   {
      return (this.action);
   }



   /*******************************************************************************
    ** Setter for action
    *******************************************************************************/
   public void setAction(String action)
   {
      this.action = action;
   }



   /*******************************************************************************
    ** Fluent setter for action
    *******************************************************************************/
   public AuditEntry withAction(String action)
   {
      this.action = action;
      return (this);
   }



   /*******************************************************************************
    ** Getter for details
    *******************************************************************************/
   public String getDetails()
   {
      return (this.details);
   }



   /*******************************************************************************
    ** Setter for details
    *******************************************************************************/
   public void setDetails(String details)
   {
      this.details = details;
   }



   /*******************************************************************************
    ** Fluent setter for details
    *******************************************************************************/
   public AuditEntry withDetails(String details)
   {
      this.details = details;
      return (this);
   }



   /*******************************************************************************
    ** Getter for changesJson
    *******************************************************************************/
   public String getChangesJson()
   {
      return (this.changesJson);
   }



   /*******************************************************************************
    ** Setter for changesJson
    *******************************************************************************/
   public void setChangesJson(String changesJson)
   {
      this.changesJson = changesJson;
   }



   /*******************************************************************************
    ** Fluent setter for changesJson
    *******************************************************************************/
   public AuditEntry withChangesJson(String changesJson)
   {
      this.changesJson = changesJson;
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
   public AuditEntry withCreateDate(Instant createDate)
   {
      this.createDate = createDate;
      return (this);
   }
}
