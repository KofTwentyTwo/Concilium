/*******************************************************************************
 ** QRecordEntity for the workflow table.
 **
 ** Represents a durable workflow instance within a master project.  Workflows
 ** coordinate multi-step operations such as initiative decomposition, repo
 ** task execution, validation cycles, and progress reconciliation.
 *******************************************************************************/
package com.kof22.concilium.model.orchestration;


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
@Table(name = Workflow.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class Workflow extends QRecordEntity
{
   public static final String TABLE_NAME = "workflow";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(possibleValueSourceName = "master_project")
   private Long masterProjectId;

   @Column(name = "name", nullable = false, length = 200)
   @QField(maxLength = 200)
   private String name;

   @Column(name = "workflow_type", nullable = false, length = 50)
   @QField(maxLength = 50)
   private String workflowType;

   @Column(name = "current_state", nullable = false, length = 50)
   @QField(maxLength = 50)
   private String currentState;

   @Column(name = "waiting_on_condition", length = 1000)
   @QField(maxLength = 1000)
   private String waitingOnCondition;

   @Column(name = "context_json", length = 50000)
   @QField(maxLength = 50000)
   private String contextJson;

   @Column(name = "started_at")
   private Instant startedAt;

   @Column(name = "completed_at")
   private Instant completedAt;

   @Column(name = "create_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;

   @Column(name = "modify_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.MODIFY_DATE)
   private Instant modifyDate;



   /*******************************************************************************
    ** Getter for id
    *******************************************************************************/
   public Long getId()
   {
      return (this.id);
   }



   /*******************************************************************************
    ** Fluent setter for id
    *******************************************************************************/
   public Workflow withId(Long id)
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
    ** Fluent setter for masterProjectId
    *******************************************************************************/
   public Workflow withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for name
    *******************************************************************************/
   public String getName()
   {
      return (this.name);
   }



   /*******************************************************************************
    ** Fluent setter for name
    *******************************************************************************/
   public Workflow withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workflowType
    *******************************************************************************/
   public String getWorkflowType()
   {
      return (this.workflowType);
   }



   /*******************************************************************************
    ** Fluent setter for workflowType
    *******************************************************************************/
   public Workflow withWorkflowType(String workflowType)
   {
      this.workflowType = workflowType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for currentState
    *******************************************************************************/
   public String getCurrentState()
   {
      return (this.currentState);
   }



   /*******************************************************************************
    ** Fluent setter for currentState
    *******************************************************************************/
   public Workflow withCurrentState(String currentState)
   {
      this.currentState = currentState;
      return (this);
   }



   /*******************************************************************************
    ** Getter for waitingOnCondition
    *******************************************************************************/
   public String getWaitingOnCondition()
   {
      return (this.waitingOnCondition);
   }



   /*******************************************************************************
    ** Fluent setter for waitingOnCondition
    *******************************************************************************/
   public Workflow withWaitingOnCondition(String waitingOnCondition)
   {
      this.waitingOnCondition = waitingOnCondition;
      return (this);
   }



   /*******************************************************************************
    ** Getter for contextJson
    *******************************************************************************/
   public String getContextJson()
   {
      return (this.contextJson);
   }



   /*******************************************************************************
    ** Fluent setter for contextJson
    *******************************************************************************/
   public Workflow withContextJson(String contextJson)
   {
      this.contextJson = contextJson;
      return (this);
   }



   /*******************************************************************************
    ** Getter for startedAt
    *******************************************************************************/
   public Instant getStartedAt()
   {
      return (this.startedAt);
   }



   /*******************************************************************************
    ** Fluent setter for startedAt
    *******************************************************************************/
   public Workflow withStartedAt(Instant startedAt)
   {
      this.startedAt = startedAt;
      return (this);
   }



   /*******************************************************************************
    ** Getter for completedAt
    *******************************************************************************/
   public Instant getCompletedAt()
   {
      return (this.completedAt);
   }



   /*******************************************************************************
    ** Fluent setter for completedAt
    *******************************************************************************/
   public Workflow withCompletedAt(Instant completedAt)
   {
      this.completedAt = completedAt;
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
    ** Fluent setter for createDate
    *******************************************************************************/
   public Workflow withCreateDate(Instant createDate)
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
    ** Fluent setter for modifyDate
    *******************************************************************************/
   public Workflow withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
