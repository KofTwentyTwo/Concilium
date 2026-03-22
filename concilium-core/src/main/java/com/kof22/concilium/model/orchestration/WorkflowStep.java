/*******************************************************************************
 ** QRecordEntity for the workflow_step table.
 **
 ** Represents a single step within a workflow.  Each step has a type (such as
 ** context assembly, governance gate, execution, or dispatch), tracks its own
 ** status independently, and may optionally require human approval before
 ** proceeding.
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
@Table(name = WorkflowStep.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class WorkflowStep extends QRecordEntity
{
   public static final String TABLE_NAME = "workflow_step";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "workflow_id", nullable = false)
   @QField(possibleValueSourceName = "workflow")
   private Long workflowId;

   @Column(name = "step_order", nullable = false)
   private Integer stepOrder;

   @Column(name = "name", nullable = false, length = 200)
   @QField(maxLength = 200)
   private String name;

   @Column(name = "step_type", nullable = false, length = 50)
   @QField(maxLength = 50)
   private String stepType;

   @Column(name = "status", nullable = false, length = 50)
   @QField(maxLength = 50, defaultValue = "pending")
   private String status;

   @Column(name = "input_json", length = 50000)
   @QField(maxLength = 50000)
   private String inputJson;

   @Column(name = "output_json", length = 50000)
   @QField(maxLength = 50000)
   private String outputJson;

   @Column(name = "approval_required")
   @QField(defaultValue = "false")
   private Boolean approvalRequired;

   @Column(name = "approval_granted")
   private Boolean approvalGranted;

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
   public WorkflowStep withId(Long id)
   {
      this.id = id;
      return (this);
   }



   /*******************************************************************************
    ** Getter for workflowId
    *******************************************************************************/
   public Long getWorkflowId()
   {
      return (this.workflowId);
   }



   /*******************************************************************************
    ** Fluent setter for workflowId
    *******************************************************************************/
   public WorkflowStep withWorkflowId(Long workflowId)
   {
      this.workflowId = workflowId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for stepOrder
    *******************************************************************************/
   public Integer getStepOrder()
   {
      return (this.stepOrder);
   }



   /*******************************************************************************
    ** Fluent setter for stepOrder
    *******************************************************************************/
   public WorkflowStep withStepOrder(Integer stepOrder)
   {
      this.stepOrder = stepOrder;
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
   public WorkflowStep withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for stepType
    *******************************************************************************/
   public String getStepType()
   {
      return (this.stepType);
   }



   /*******************************************************************************
    ** Fluent setter for stepType
    *******************************************************************************/
   public WorkflowStep withStepType(String stepType)
   {
      this.stepType = stepType;
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
    ** Fluent setter for status
    *******************************************************************************/
   public WorkflowStep withStatus(String status)
   {
      this.status = status;
      return (this);
   }



   /*******************************************************************************
    ** Getter for inputJson
    *******************************************************************************/
   public String getInputJson()
   {
      return (this.inputJson);
   }



   /*******************************************************************************
    ** Fluent setter for inputJson
    *******************************************************************************/
   public WorkflowStep withInputJson(String inputJson)
   {
      this.inputJson = inputJson;
      return (this);
   }



   /*******************************************************************************
    ** Getter for outputJson
    *******************************************************************************/
   public String getOutputJson()
   {
      return (this.outputJson);
   }



   /*******************************************************************************
    ** Fluent setter for outputJson
    *******************************************************************************/
   public WorkflowStep withOutputJson(String outputJson)
   {
      this.outputJson = outputJson;
      return (this);
   }



   /*******************************************************************************
    ** Getter for approvalRequired
    *******************************************************************************/
   public Boolean getApprovalRequired()
   {
      return (this.approvalRequired);
   }



   /*******************************************************************************
    ** Fluent setter for approvalRequired
    *******************************************************************************/
   public WorkflowStep withApprovalRequired(Boolean approvalRequired)
   {
      this.approvalRequired = approvalRequired;
      return (this);
   }



   /*******************************************************************************
    ** Getter for approvalGranted
    *******************************************************************************/
   public Boolean getApprovalGranted()
   {
      return (this.approvalGranted);
   }



   /*******************************************************************************
    ** Fluent setter for approvalGranted
    *******************************************************************************/
   public WorkflowStep withApprovalGranted(Boolean approvalGranted)
   {
      this.approvalGranted = approvalGranted;
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
   public WorkflowStep withStartedAt(Instant startedAt)
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
   public WorkflowStep withCompletedAt(Instant completedAt)
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
   public WorkflowStep withCreateDate(Instant createDate)
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
   public WorkflowStep withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
