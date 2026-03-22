package com.kof22.concilium.model.project;


import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.kingsrook.qqq.backend.core.model.data.QField;
import com.kingsrook.qqq.backend.core.model.data.QRecordEntity;
import com.kingsrook.qqq.backend.core.model.metadata.fields.DynamicDefaultValueBehavior;
import com.kingsrook.qqq.backend.core.model.metadata.producers.annotations.QMetaDataProducingEntity;


/*******************************************************************************
 ** QRecordEntity for the repository table.
 **
 ** Represents a git repository attached to a Master Project. Each repository
 ** has a role (backend, frontend, infra) and branch strategy configuration.
 *******************************************************************************/
@Entity
@Table(name = Repository.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class Repository extends QRecordEntity
{
   public static final String TABLE_NAME = "repository";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = MasterProject.TABLE_NAME)
   private Long masterProjectId;

   @Column(name = "name", nullable = false)
   @QField(isRequired = true, maxLength = 200)
   private String name;

   @Column(name = "git_url", nullable = false)
   @QField(isRequired = true, maxLength = 500)
   private String gitUrl;

   @Column(name = "role")
   @QField(maxLength = 100)
   private String role;

   @Column(name = "branch_strategy")
   @QField(maxLength = 100)
   private String branchStrategy;

   @Column(name = "clone_path")
   @QField(maxLength = 500)
   private String clonePath;

   @Column(name = "status", nullable = false)
   @QField(isRequired = true, maxLength = 50, defaultValue = "active")
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
   public Repository()
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
   public Repository withId(Long id)
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
   public Repository withMasterProjectId(Long masterProjectId)
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
    ** Setter for name
    *******************************************************************************/
   public void setName(String name)
   {
      this.name = name;
   }



   /*******************************************************************************
    ** Fluent setter for name
    *******************************************************************************/
   public Repository withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for gitUrl
    *******************************************************************************/
   public String getGitUrl()
   {
      return (this.gitUrl);
   }



   /*******************************************************************************
    ** Setter for gitUrl
    *******************************************************************************/
   public void setGitUrl(String gitUrl)
   {
      this.gitUrl = gitUrl;
   }



   /*******************************************************************************
    ** Fluent setter for gitUrl
    *******************************************************************************/
   public Repository withGitUrl(String gitUrl)
   {
      this.gitUrl = gitUrl;
      return (this);
   }



   /*******************************************************************************
    ** Getter for role
    *******************************************************************************/
   public String getRole()
   {
      return (this.role);
   }



   /*******************************************************************************
    ** Setter for role
    *******************************************************************************/
   public void setRole(String role)
   {
      this.role = role;
   }



   /*******************************************************************************
    ** Fluent setter for role
    *******************************************************************************/
   public Repository withRole(String role)
   {
      this.role = role;
      return (this);
   }



   /*******************************************************************************
    ** Getter for branchStrategy
    *******************************************************************************/
   public String getBranchStrategy()
   {
      return (this.branchStrategy);
   }



   /*******************************************************************************
    ** Setter for branchStrategy
    *******************************************************************************/
   public void setBranchStrategy(String branchStrategy)
   {
      this.branchStrategy = branchStrategy;
   }



   /*******************************************************************************
    ** Fluent setter for branchStrategy
    *******************************************************************************/
   public Repository withBranchStrategy(String branchStrategy)
   {
      this.branchStrategy = branchStrategy;
      return (this);
   }



   /*******************************************************************************
    ** Getter for clonePath
    *******************************************************************************/
   public String getClonePath()
   {
      return (this.clonePath);
   }



   /*******************************************************************************
    ** Setter for clonePath
    *******************************************************************************/
   public void setClonePath(String clonePath)
   {
      this.clonePath = clonePath;
   }



   /*******************************************************************************
    ** Fluent setter for clonePath
    *******************************************************************************/
   public Repository withClonePath(String clonePath)
   {
      this.clonePath = clonePath;
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
   public Repository withStatus(String status)
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
   public Repository withCreateDate(Instant createDate)
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
   public Repository withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
