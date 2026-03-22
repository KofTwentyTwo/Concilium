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
@Table(name = ArchitectureComponent.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the architecture_component table.
 ** Represents a node in an architecture graph -- a service, library, API,
 ** infrastructure component, or database that is part of the system.
 *******************************************************************************/
public class ArchitectureComponent extends QRecordEntity
{
   public static final String TABLE_NAME = "architecture_component";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "architecture_model_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = ArchitectureModel.TABLE_NAME)
   private Long architectureModelId;

   @Column(name = "repository_id")
   @QField(possibleValueSourceName = "repository")
   private Long repositoryId;

   @Column(name = "name", nullable = false, length = 200)
   @QField(isRequired = true, maxLength = 200, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String name;

   @Column(name = "component_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String componentType;

   @Column(name = "description", length = 2000)
   @QField(maxLength = 2000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String description;

   @Column(name = "metadata_json", length = 10000)
   @QField(maxLength = 10000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String metadataJson;

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
   public ArchitectureComponent withId(Long id)
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
   public ArchitectureComponent withArchitectureModelId(Long architectureModelId)
   {
      this.architectureModelId = architectureModelId;
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
    ** @param repositoryId ID of the associated repository, if any
    ** @return this
    *******************************************************************************/
   public ArchitectureComponent withRepositoryId(Long repositoryId)
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
    ** @param name the display name for this component
    ** @return this
    *******************************************************************************/
   public ArchitectureComponent withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for componentType
    ** @see #withComponentType(String)
    *******************************************************************************/
   public String getComponentType()
   {
      return (this.componentType);
   }



   /*******************************************************************************
    ** Setter for componentType
    ** @see #withComponentType(String)
    *******************************************************************************/
   public void setComponentType(String componentType)
   {
      this.componentType = componentType;
   }



   /*******************************************************************************
    ** Fluent setter for componentType
    ** @param componentType the type of component: service, library, api, infrastructure, database
    ** @return this
    *******************************************************************************/
   public ArchitectureComponent withComponentType(String componentType)
   {
      this.componentType = componentType;
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
    ** @param description text description of this component
    ** @return this
    *******************************************************************************/
   public ArchitectureComponent withDescription(String description)
   {
      this.description = description;
      return (this);
   }



   /*******************************************************************************
    ** Getter for metadataJson
    ** @see #withMetadataJson(String)
    *******************************************************************************/
   public String getMetadataJson()
   {
      return (this.metadataJson);
   }



   /*******************************************************************************
    ** Setter for metadataJson
    ** @see #withMetadataJson(String)
    *******************************************************************************/
   public void setMetadataJson(String metadataJson)
   {
      this.metadataJson = metadataJson;
   }



   /*******************************************************************************
    ** Fluent setter for metadataJson
    ** @param metadataJson extra component metadata serialized as JSON
    ** @return this
    *******************************************************************************/
   public ArchitectureComponent withMetadataJson(String metadataJson)
   {
      this.metadataJson = metadataJson;
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
   public ArchitectureComponent withCreateDate(Instant createDate)
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
   public ArchitectureComponent withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
