package com.kof22.concilium.model.work;


import java.time.Instant;
import com.kingsrook.qqq.backend.core.model.data.QField;
import com.kingsrook.qqq.backend.core.model.data.QRecordEntity;
import com.kingsrook.qqq.backend.core.model.metadata.fields.DynamicDefaultValueBehavior;
import com.kingsrook.qqq.backend.core.model.metadata.fields.ValueTooLongBehavior;
import com.kingsrook.qqq.backend.core.model.metadata.producers.annotations.QMetaDataProducingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = WorkItem.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the work_item table.
 ** Maps to a GitHub Issue or Jira ticket.  Every actionable work item in
 ** Concilium must exist in an external issue tracker -- this entity holds the
 ** local reference, status, and agent assignment for orchestration purposes.
 *******************************************************************************/
public class WorkItem extends QRecordEntity
{
   public static final String TABLE_NAME = "work_item";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = "master_project")
   private Long masterProjectId;

   @Column(name = "repository_id")
   @QField(possibleValueSourceName = "repository")
   private Long repositoryId;

   @Column(name = "plan_id")
   @QField(possibleValueSourceName = Plan.TABLE_NAME)
   private Long planId;

   @Column(name = "assigned_agent_id")
   @QField(possibleValueSourceName = "agent")
   private Long assignedAgentId;

   @Column(name = "tracker_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String trackerType;

   @Column(name = "tracker_project_key", nullable = false, length = 200)
   @QField(isRequired = true, maxLength = 200, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String trackerProjectKey;

   @Column(name = "external_id", nullable = false, length = 100)
   @QField(isRequired = true, maxLength = 100, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String externalId;

   @Column(name = "external_url", length = 500)
   @QField(maxLength = 500, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String externalUrl;

   @Column(name = "item_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String itemType;

   @Column(name = "title", nullable = false, length = 500)
   @QField(isRequired = true, maxLength = 500, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String title;

   @Column(name = "status", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String status;

   @Column(name = "create_date", nullable = false, updatable = false)
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;

   @Column(name = "modify_date", nullable = false)
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.MODIFY_DATE)
   private Instant modifyDate;



   /*******************************************************************************
    ** Getter for id
    ** @see #withId(Long)
    *******************************************************************************/
   public Long getId()
   {
      return (this.id);
   }



   /*******************************************************************************
    ** Setter for id
    ** @see #withId(Long)
    *******************************************************************************/
   public void setId(Long id)
   {
      this.id = id;
   }



   /*******************************************************************************
    ** Fluent setter for id
    ** @param id the unique identifier for this record
    ** @return this
    *******************************************************************************/
   public WorkItem withId(Long id)
   {
      this.id = id;
      return (this);
   }



   /*******************************************************************************
    ** Getter for masterProjectId
    ** @see #withMasterProjectId(Long)
    *******************************************************************************/
   public Long getMasterProjectId()
   {
      return (this.masterProjectId);
   }



   /*******************************************************************************
    ** Setter for masterProjectId
    ** @see #withMasterProjectId(Long)
    *******************************************************************************/
   public void setMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
   }



   /*******************************************************************************
    ** Fluent setter for masterProjectId
    ** @param masterProjectId ID of the associated master project
    ** @return this
    *******************************************************************************/
   public WorkItem withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for repositoryId
    ** @see #withRepositoryId(Long)
    *******************************************************************************/
   public Long getRepositoryId()
   {
      return (this.repositoryId);
   }



   /*******************************************************************************
    ** Setter for repositoryId
    ** @see #withRepositoryId(Long)
    *******************************************************************************/
   public void setRepositoryId(Long repositoryId)
   {
      this.repositoryId = repositoryId;
   }



   /*******************************************************************************
    ** Fluent setter for repositoryId
    ** @param repositoryId ID of the associated repository, if repo-scoped
    ** @return this
    *******************************************************************************/
   public WorkItem withRepositoryId(Long repositoryId)
   {
      this.repositoryId = repositoryId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for planId
    ** @see #withPlanId(Long)
    *******************************************************************************/
   public Long getPlanId()
   {
      return (this.planId);
   }



   /*******************************************************************************
    ** Setter for planId
    ** @see #withPlanId(Long)
    *******************************************************************************/
   public void setPlanId(Long planId)
   {
      this.planId = planId;
   }



   /*******************************************************************************
    ** Fluent setter for planId
    ** @param planId ID of the plan this work item belongs to
    ** @return this
    *******************************************************************************/
   public WorkItem withPlanId(Long planId)
   {
      this.planId = planId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for assignedAgentId
    ** @see #withAssignedAgentId(Long)
    *******************************************************************************/
   public Long getAssignedAgentId()
   {
      return (this.assignedAgentId);
   }



   /*******************************************************************************
    ** Setter for assignedAgentId
    ** @see #withAssignedAgentId(Long)
    *******************************************************************************/
   public void setAssignedAgentId(Long assignedAgentId)
   {
      this.assignedAgentId = assignedAgentId;
   }



   /*******************************************************************************
    ** Fluent setter for assignedAgentId
    ** @param assignedAgentId ID of the agent assigned to this work item
    ** @return this
    *******************************************************************************/
   public WorkItem withAssignedAgentId(Long assignedAgentId)
   {
      this.assignedAgentId = assignedAgentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for trackerType
    ** @see #withTrackerType(String)
    *******************************************************************************/
   public String getTrackerType()
   {
      return (this.trackerType);
   }



   /*******************************************************************************
    ** Setter for trackerType
    ** @see #withTrackerType(String)
    *******************************************************************************/
   public void setTrackerType(String trackerType)
   {
      this.trackerType = trackerType;
   }



   /*******************************************************************************
    ** Fluent setter for trackerType
    ** @param trackerType the issue tracker type: github, jira
    ** @return this
    *******************************************************************************/
   public WorkItem withTrackerType(String trackerType)
   {
      this.trackerType = trackerType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for trackerProjectKey
    ** @see #withTrackerProjectKey(String)
    *******************************************************************************/
   public String getTrackerProjectKey()
   {
      return (this.trackerProjectKey);
   }



   /*******************************************************************************
    ** Setter for trackerProjectKey
    ** @see #withTrackerProjectKey(String)
    *******************************************************************************/
   public void setTrackerProjectKey(String trackerProjectKey)
   {
      this.trackerProjectKey = trackerProjectKey;
   }



   /*******************************************************************************
    ** Fluent setter for trackerProjectKey
    ** @param trackerProjectKey the project key, e.g. "KofTwentyTwo/Concilium" or "MH"
    ** @return this
    *******************************************************************************/
   public WorkItem withTrackerProjectKey(String trackerProjectKey)
   {
      this.trackerProjectKey = trackerProjectKey;
      return (this);
   }



   /*******************************************************************************
    ** Getter for externalId
    ** @see #withExternalId(String)
    *******************************************************************************/
   public String getExternalId()
   {
      return (this.externalId);
   }



   /*******************************************************************************
    ** Setter for externalId
    ** @see #withExternalId(String)
    *******************************************************************************/
   public void setExternalId(String externalId)
   {
      this.externalId = externalId;
   }



   /*******************************************************************************
    ** Fluent setter for externalId
    ** @param externalId the external issue identifier, e.g. "#42" or "MH-123"
    ** @return this
    *******************************************************************************/
   public WorkItem withExternalId(String externalId)
   {
      this.externalId = externalId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for externalUrl
    ** @see #withExternalUrl(String)
    *******************************************************************************/
   public String getExternalUrl()
   {
      return (this.externalUrl);
   }



   /*******************************************************************************
    ** Setter for externalUrl
    ** @see #withExternalUrl(String)
    *******************************************************************************/
   public void setExternalUrl(String externalUrl)
   {
      this.externalUrl = externalUrl;
   }



   /*******************************************************************************
    ** Fluent setter for externalUrl
    ** @param externalUrl URL to the issue in the external tracker
    ** @return this
    *******************************************************************************/
   public WorkItem withExternalUrl(String externalUrl)
   {
      this.externalUrl = externalUrl;
      return (this);
   }



   /*******************************************************************************
    ** Getter for itemType
    ** @see #withItemType(String)
    *******************************************************************************/
   public String getItemType()
   {
      return (this.itemType);
   }



   /*******************************************************************************
    ** Setter for itemType
    ** @see #withItemType(String)
    *******************************************************************************/
   public void setItemType(String itemType)
   {
      this.itemType = itemType;
   }



   /*******************************************************************************
    ** Fluent setter for itemType
    ** @param itemType the work item type: epic, story, task, subtask
    ** @return this
    *******************************************************************************/
   public WorkItem withItemType(String itemType)
   {
      this.itemType = itemType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for title
    ** @see #withTitle(String)
    *******************************************************************************/
   public String getTitle()
   {
      return (this.title);
   }



   /*******************************************************************************
    ** Setter for title
    ** @see #withTitle(String)
    *******************************************************************************/
   public void setTitle(String title)
   {
      this.title = title;
   }



   /*******************************************************************************
    ** Fluent setter for title
    ** @param title the display title of this work item
    ** @return this
    *******************************************************************************/
   public WorkItem withTitle(String title)
   {
      this.title = title;
      return (this);
   }



   /*******************************************************************************
    ** Getter for status
    ** @see #withStatus(String)
    *******************************************************************************/
   public String getStatus()
   {
      return (this.status);
   }



   /*******************************************************************************
    ** Setter for status
    ** @see #withStatus(String)
    *******************************************************************************/
   public void setStatus(String status)
   {
      this.status = status;
   }



   /*******************************************************************************
    ** Fluent setter for status
    ** @param status the work item status: open, in_progress, done, closed
    ** @return this
    *******************************************************************************/
   public WorkItem withStatus(String status)
   {
      this.status = status;
      return (this);
   }



   /*******************************************************************************
    ** Getter for createDate
    ** @see #withCreateDate(Instant)
    *******************************************************************************/
   public Instant getCreateDate()
   {
      return (this.createDate);
   }



   /*******************************************************************************
    ** Setter for createDate
    ** @see #withCreateDate(Instant)
    *******************************************************************************/
   public void setCreateDate(Instant createDate)
   {
      this.createDate = createDate;
   }



   /*******************************************************************************
    ** Fluent setter for createDate
    ** @param createDate timestamp when record was created
    ** @return this
    *******************************************************************************/
   public WorkItem withCreateDate(Instant createDate)
   {
      this.createDate = createDate;
      return (this);
   }



   /*******************************************************************************
    ** Getter for modifyDate
    ** @see #withModifyDate(Instant)
    *******************************************************************************/
   public Instant getModifyDate()
   {
      return (this.modifyDate);
   }



   /*******************************************************************************
    ** Setter for modifyDate
    ** @see #withModifyDate(Instant)
    *******************************************************************************/
   public void setModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
   }



   /*******************************************************************************
    ** Fluent setter for modifyDate
    ** @param modifyDate timestamp when record was last modified
    ** @return this
    *******************************************************************************/
   public WorkItem withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
