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
@Table(name = Plan.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the plan table.
 ** Represents a project-level or repository-level plan that captures a goal,
 ** approach, decomposed steps, and rollout notes for coordinated delivery.
 *******************************************************************************/
public class Plan extends QRecordEntity
{
   public static final String TABLE_NAME = "plan";

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

   @Column(name = "name", nullable = false, length = 200)
   @QField(isRequired = true, maxLength = 200, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String name;

   @Column(name = "goal", length = 2000)
   @QField(maxLength = 2000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String goal;

   @Column(name = "approach", length = 5000)
   @QField(maxLength = 5000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String approach;

   @Column(name = "steps_json", length = 50000)
   @QField(maxLength = 50000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String stepsJson;

   @Column(name = "rollout_notes", length = 5000)
   @QField(maxLength = 5000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String rolloutNotes;

   @Column(name = "status", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, defaultValue = "draft", valueTooLongBehavior = ValueTooLongBehavior.ERROR)
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
   public Plan withId(Long id)
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
   public Plan withMasterProjectId(Long masterProjectId)
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
    ** @param repositoryId ID of the associated repository for repo-level plans
    ** @return this
    *******************************************************************************/
   public Plan withRepositoryId(Long repositoryId)
   {
      this.repositoryId = repositoryId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for name
    ** @see #withName(String)
    *******************************************************************************/
   public String getName()
   {
      return (this.name);
   }



   /*******************************************************************************
    ** Setter for name
    ** @see #withName(String)
    *******************************************************************************/
   public void setName(String name)
   {
      this.name = name;
   }



   /*******************************************************************************
    ** Fluent setter for name
    ** @param name the display name for this plan
    ** @return this
    *******************************************************************************/
   public Plan withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for goal
    ** @see #withGoal(String)
    *******************************************************************************/
   public String getGoal()
   {
      return (this.goal);
   }



   /*******************************************************************************
    ** Setter for goal
    ** @see #withGoal(String)
    *******************************************************************************/
   public void setGoal(String goal)
   {
      this.goal = goal;
   }



   /*******************************************************************************
    ** Fluent setter for goal
    ** @param goal what this plan aims to achieve
    ** @return this
    *******************************************************************************/
   public Plan withGoal(String goal)
   {
      this.goal = goal;
      return (this);
   }



   /*******************************************************************************
    ** Getter for approach
    ** @see #withApproach(String)
    *******************************************************************************/
   public String getApproach()
   {
      return (this.approach);
   }



   /*******************************************************************************
    ** Setter for approach
    ** @see #withApproach(String)
    *******************************************************************************/
   public void setApproach(String approach)
   {
      this.approach = approach;
   }



   /*******************************************************************************
    ** Fluent setter for approach
    ** @param approach how this plan will be executed
    ** @return this
    *******************************************************************************/
   public Plan withApproach(String approach)
   {
      this.approach = approach;
      return (this);
   }



   /*******************************************************************************
    ** Getter for stepsJson
    ** @see #withStepsJson(String)
    *******************************************************************************/
   public String getStepsJson()
   {
      return (this.stepsJson);
   }



   /*******************************************************************************
    ** Setter for stepsJson
    ** @see #withStepsJson(String)
    *******************************************************************************/
   public void setStepsJson(String stepsJson)
   {
      this.stepsJson = stepsJson;
   }



   /*******************************************************************************
    ** Fluent setter for stepsJson
    ** @param stepsJson decomposed plan steps serialized as JSON
    ** @return this
    *******************************************************************************/
   public Plan withStepsJson(String stepsJson)
   {
      this.stepsJson = stepsJson;
      return (this);
   }



   /*******************************************************************************
    ** Getter for rolloutNotes
    ** @see #withRolloutNotes(String)
    *******************************************************************************/
   public String getRolloutNotes()
   {
      return (this.rolloutNotes);
   }



   /*******************************************************************************
    ** Setter for rolloutNotes
    ** @see #withRolloutNotes(String)
    *******************************************************************************/
   public void setRolloutNotes(String rolloutNotes)
   {
      this.rolloutNotes = rolloutNotes;
   }



   /*******************************************************************************
    ** Fluent setter for rolloutNotes
    ** @param rolloutNotes notes on rollout and rollback strategy
    ** @return this
    *******************************************************************************/
   public Plan withRolloutNotes(String rolloutNotes)
   {
      this.rolloutNotes = rolloutNotes;
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
    ** @param status the plan status: draft, active, completed, cancelled
    ** @return this
    *******************************************************************************/
   public Plan withStatus(String status)
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
   public Plan withCreateDate(Instant createDate)
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
   public Plan withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
