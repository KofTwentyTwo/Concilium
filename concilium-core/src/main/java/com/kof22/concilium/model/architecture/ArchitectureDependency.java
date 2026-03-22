package com.kof22.concilium.model.architecture;


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
@Table(name = ArchitectureDependency.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the architecture_dependency table.
 ** Represents a directed edge between two architecture components -- capturing
 ** relationships such as API calls, shared libraries, data flows, and deploy
 ** dependencies within an architecture model.
 *******************************************************************************/
public class ArchitectureDependency extends QRecordEntity
{
   public static final String TABLE_NAME = "architecture_dependency";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "architecture_model_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = ArchitectureModel.TABLE_NAME)
   private Long architectureModelId;

   @Column(name = "source_component_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = ArchitectureComponent.TABLE_NAME)
   private Long sourceComponentId;

   @Column(name = "target_component_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = ArchitectureComponent.TABLE_NAME)
   private Long targetComponentId;

   @Column(name = "dependency_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String dependencyType;

   @Column(name = "description", length = 1000)
   @QField(maxLength = 1000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String description;

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
   public ArchitectureDependency withId(Long id)
   {
      this.id = id;
      return (this);
   }



   /*******************************************************************************
    ** Getter for architectureModelId
    ** @see #withArchitectureModelId(Long)
    *******************************************************************************/
   public Long getArchitectureModelId()
   {
      return (this.architectureModelId);
   }



   /*******************************************************************************
    ** Setter for architectureModelId
    ** @see #withArchitectureModelId(Long)
    *******************************************************************************/
   public void setArchitectureModelId(Long architectureModelId)
   {
      this.architectureModelId = architectureModelId;
   }



   /*******************************************************************************
    ** Fluent setter for architectureModelId
    ** @param architectureModelId ID of the parent architecture model
    ** @return this
    *******************************************************************************/
   public ArchitectureDependency withArchitectureModelId(Long architectureModelId)
   {
      this.architectureModelId = architectureModelId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for sourceComponentId
    ** @see #withSourceComponentId(Long)
    *******************************************************************************/
   public Long getSourceComponentId()
   {
      return (this.sourceComponentId);
   }



   /*******************************************************************************
    ** Setter for sourceComponentId
    ** @see #withSourceComponentId(Long)
    *******************************************************************************/
   public void setSourceComponentId(Long sourceComponentId)
   {
      this.sourceComponentId = sourceComponentId;
   }



   /*******************************************************************************
    ** Fluent setter for sourceComponentId
    ** @param sourceComponentId ID of the component that depends on the target
    ** @return this
    *******************************************************************************/
   public ArchitectureDependency withSourceComponentId(Long sourceComponentId)
   {
      this.sourceComponentId = sourceComponentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for targetComponentId
    ** @see #withTargetComponentId(Long)
    *******************************************************************************/
   public Long getTargetComponentId()
   {
      return (this.targetComponentId);
   }



   /*******************************************************************************
    ** Setter for targetComponentId
    ** @see #withTargetComponentId(Long)
    *******************************************************************************/
   public void setTargetComponentId(Long targetComponentId)
   {
      this.targetComponentId = targetComponentId;
   }



   /*******************************************************************************
    ** Fluent setter for targetComponentId
    ** @param targetComponentId ID of the component that is depended upon
    ** @return this
    *******************************************************************************/
   public ArchitectureDependency withTargetComponentId(Long targetComponentId)
   {
      this.targetComponentId = targetComponentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for dependencyType
    ** @see #withDependencyType(String)
    *******************************************************************************/
   public String getDependencyType()
   {
      return (this.dependencyType);
   }



   /*******************************************************************************
    ** Setter for dependencyType
    ** @see #withDependencyType(String)
    *******************************************************************************/
   public void setDependencyType(String dependencyType)
   {
      this.dependencyType = dependencyType;
   }



   /*******************************************************************************
    ** Fluent setter for dependencyType
    ** @param dependencyType the type: api_call, shared_lib, data_flow, deploy_dependency
    ** @return this
    *******************************************************************************/
   public ArchitectureDependency withDependencyType(String dependencyType)
   {
      this.dependencyType = dependencyType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for description
    ** @see #withDescription(String)
    *******************************************************************************/
   public String getDescription()
   {
      return (this.description);
   }



   /*******************************************************************************
    ** Setter for description
    ** @see #withDescription(String)
    *******************************************************************************/
   public void setDescription(String description)
   {
      this.description = description;
   }



   /*******************************************************************************
    ** Fluent setter for description
    ** @param description text description of this dependency
    ** @return this
    *******************************************************************************/
   public ArchitectureDependency withDescription(String description)
   {
      this.description = description;
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
   public ArchitectureDependency withCreateDate(Instant createDate)
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
   public ArchitectureDependency withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
