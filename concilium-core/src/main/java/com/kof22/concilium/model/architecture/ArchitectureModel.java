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
@Table(name = ArchitectureModel.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the architecture_model table.
 ** Represents a versioned architecture snapshot for a Master Project, stored
 ** as a JSON graph.  The sourceType distinguishes DECLARED (human-defined)
 ** models from OBSERVED (system-inferred) models to support drift detection.
 *******************************************************************************/
public class ArchitectureModel extends QRecordEntity
{
   public static final String TABLE_NAME = "architecture_model";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = "master_project")
   private Long masterProjectId;

   @Column(name = "name", nullable = false, length = 200)
   @QField(isRequired = true, maxLength = 200, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String name;

   @Column(name = "version", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String version;

   @Column(name = "source_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String sourceType;

   @Column(name = "graph_json", length = 500000)
   @QField(maxLength = 500000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String graphJson;

   @Column(name = "description", length = 2000)
   @QField(maxLength = 2000, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
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
   public ArchitectureModel withId(Long id)
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
   public ArchitectureModel withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
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
    ** @param name the display name for this architecture model
    ** @return this
    *******************************************************************************/
   public ArchitectureModel withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for version
    ** @see #withVersion(String)
    *******************************************************************************/
   public String getVersion()
   {
      return (this.version);
   }



   /*******************************************************************************
    ** Setter for version
    ** @see #withVersion(String)
    *******************************************************************************/
   public void setVersion(String version)
   {
      this.version = version;
   }



   /*******************************************************************************
    ** Fluent setter for version
    ** @param version the version identifier for this architecture snapshot
    ** @return this
    *******************************************************************************/
   public ArchitectureModel withVersion(String version)
   {
      this.version = version;
      return (this);
   }



   /*******************************************************************************
    ** Getter for sourceType
    ** @see #withSourceType(String)
    *******************************************************************************/
   public String getSourceType()
   {
      return (this.sourceType);
   }



   /*******************************************************************************
    ** Setter for sourceType
    ** @see #withSourceType(String)
    *******************************************************************************/
   public void setSourceType(String sourceType)
   {
      this.sourceType = sourceType;
   }



   /*******************************************************************************
    ** Fluent setter for sourceType
    ** @param sourceType DECLARED or OBSERVED
    ** @return this
    *******************************************************************************/
   public ArchitectureModel withSourceType(String sourceType)
   {
      this.sourceType = sourceType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for graphJson
    ** @see #withGraphJson(String)
    *******************************************************************************/
   public String getGraphJson()
   {
      return (this.graphJson);
   }



   /*******************************************************************************
    ** Setter for graphJson
    ** @see #withGraphJson(String)
    *******************************************************************************/
   public void setGraphJson(String graphJson)
   {
      this.graphJson = graphJson;
   }



   /*******************************************************************************
    ** Fluent setter for graphJson
    ** @param graphJson the full architecture graph serialized as JSON
    ** @return this
    *******************************************************************************/
   public ArchitectureModel withGraphJson(String graphJson)
   {
      this.graphJson = graphJson;
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
    ** @param description text description of this architecture model
    ** @return this
    *******************************************************************************/
   public ArchitectureModel withDescription(String description)
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
   public ArchitectureModel withCreateDate(Instant createDate)
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
   public ArchitectureModel withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
