package com.kof22.concilium.model.agent;


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
import com.kof22.concilium.model.project.MasterProject;
import com.kof22.concilium.model.project.Repository;


/*******************************************************************************
 ** QRecordEntity for the agent table.
 **
 ** Represents a persistent agent identity within a Master Project. Agents may
 ** be of type master, repo, or specialist, each with configurable model,
 ** approval policies, and escalation rules.
 *******************************************************************************/
@Entity
@Table(name = Agent.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class Agent extends QRecordEntity
{
   public static final String TABLE_NAME = "agent";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "master_project_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = MasterProject.TABLE_NAME)
   private Long masterProjectId;

   @Column(name = "repository_id")
   @QField(possibleValueSourceName = Repository.TABLE_NAME)
   private Long repositoryId;

   @Column(name = "name", nullable = false)
   @QField(isRequired = true, maxLength = 200)
   private String name;

   @Column(name = "agent_type", nullable = false)
   @QField(isRequired = true, maxLength = 50)
   private String agentType;

   @Column(name = "specialist_type")
   @QField(maxLength = 50)
   private String specialistType;

   @Column(name = "model_id")
   @QField(maxLength = 100, defaultValue = "claude-sonnet-4-6")
   private String modelId;

   @Column(name = "max_turns")
   @QField(defaultValue = "50")
   private Integer maxTurns;

   @Column(name = "tool_permissions")
   @QField(maxLength = 2000)
   private String toolPermissions;

   @Column(name = "system_prompt_template")
   @QField(maxLength = 50000)
   private String systemPromptTemplate;

   @Column(name = "approval_policy", nullable = false)
   @QField(isRequired = true, maxLength = 50, defaultValue = "NONE")
   private String approvalPolicy;

   @Column(name = "auto_approve_scope")
   @QField(maxLength = 10000)
   private String autoApproveScope;

   @Column(name = "escalation_rules")
   @QField(maxLength = 10000)
   private String escalationRules;

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
   public Agent()
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
   public Agent withId(Long id)
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
   public Agent withMasterProjectId(Long masterProjectId)
   {
      this.masterProjectId = masterProjectId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for repositoryId
    *******************************************************************************/
   public Long getRepositoryId()
   {
      return (this.repositoryId);
   }



   /*******************************************************************************
    ** Setter for repositoryId
    *******************************************************************************/
   public void setRepositoryId(Long repositoryId)
   {
      this.repositoryId = repositoryId;
   }



   /*******************************************************************************
    ** Fluent setter for repositoryId
    *******************************************************************************/
   public Agent withRepositoryId(Long repositoryId)
   {
      this.repositoryId = repositoryId;
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
   public Agent withName(String name)
   {
      this.name = name;
      return (this);
   }



   /*******************************************************************************
    ** Getter for agentType
    *******************************************************************************/
   public String getAgentType()
   {
      return (this.agentType);
   }



   /*******************************************************************************
    ** Setter for agentType
    *******************************************************************************/
   public void setAgentType(String agentType)
   {
      this.agentType = agentType;
   }



   /*******************************************************************************
    ** Fluent setter for agentType
    *******************************************************************************/
   public Agent withAgentType(String agentType)
   {
      this.agentType = agentType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for specialistType
    *******************************************************************************/
   public String getSpecialistType()
   {
      return (this.specialistType);
   }



   /*******************************************************************************
    ** Setter for specialistType
    *******************************************************************************/
   public void setSpecialistType(String specialistType)
   {
      this.specialistType = specialistType;
   }



   /*******************************************************************************
    ** Fluent setter for specialistType
    *******************************************************************************/
   public Agent withSpecialistType(String specialistType)
   {
      this.specialistType = specialistType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for modelId
    *******************************************************************************/
   public String getModelId()
   {
      return (this.modelId);
   }



   /*******************************************************************************
    ** Setter for modelId
    *******************************************************************************/
   public void setModelId(String modelId)
   {
      this.modelId = modelId;
   }



   /*******************************************************************************
    ** Fluent setter for modelId
    *******************************************************************************/
   public Agent withModelId(String modelId)
   {
      this.modelId = modelId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for maxTurns
    *******************************************************************************/
   public Integer getMaxTurns()
   {
      return (this.maxTurns);
   }



   /*******************************************************************************
    ** Setter for maxTurns
    *******************************************************************************/
   public void setMaxTurns(Integer maxTurns)
   {
      this.maxTurns = maxTurns;
   }



   /*******************************************************************************
    ** Fluent setter for maxTurns
    *******************************************************************************/
   public Agent withMaxTurns(Integer maxTurns)
   {
      this.maxTurns = maxTurns;
      return (this);
   }



   /*******************************************************************************
    ** Getter for toolPermissions
    *******************************************************************************/
   public String getToolPermissions()
   {
      return (this.toolPermissions);
   }



   /*******************************************************************************
    ** Setter for toolPermissions
    *******************************************************************************/
   public void setToolPermissions(String toolPermissions)
   {
      this.toolPermissions = toolPermissions;
   }



   /*******************************************************************************
    ** Fluent setter for toolPermissions
    *******************************************************************************/
   public Agent withToolPermissions(String toolPermissions)
   {
      this.toolPermissions = toolPermissions;
      return (this);
   }



   /*******************************************************************************
    ** Getter for systemPromptTemplate
    *******************************************************************************/
   public String getSystemPromptTemplate()
   {
      return (this.systemPromptTemplate);
   }



   /*******************************************************************************
    ** Setter for systemPromptTemplate
    *******************************************************************************/
   public void setSystemPromptTemplate(String systemPromptTemplate)
   {
      this.systemPromptTemplate = systemPromptTemplate;
   }



   /*******************************************************************************
    ** Fluent setter for systemPromptTemplate
    *******************************************************************************/
   public Agent withSystemPromptTemplate(String systemPromptTemplate)
   {
      this.systemPromptTemplate = systemPromptTemplate;
      return (this);
   }



   /*******************************************************************************
    ** Getter for approvalPolicy
    *******************************************************************************/
   public String getApprovalPolicy()
   {
      return (this.approvalPolicy);
   }



   /*******************************************************************************
    ** Setter for approvalPolicy
    *******************************************************************************/
   public void setApprovalPolicy(String approvalPolicy)
   {
      this.approvalPolicy = approvalPolicy;
   }



   /*******************************************************************************
    ** Fluent setter for approvalPolicy
    *******************************************************************************/
   public Agent withApprovalPolicy(String approvalPolicy)
   {
      this.approvalPolicy = approvalPolicy;
      return (this);
   }



   /*******************************************************************************
    ** Getter for autoApproveScope
    *******************************************************************************/
   public String getAutoApproveScope()
   {
      return (this.autoApproveScope);
   }



   /*******************************************************************************
    ** Setter for autoApproveScope
    *******************************************************************************/
   public void setAutoApproveScope(String autoApproveScope)
   {
      this.autoApproveScope = autoApproveScope;
   }



   /*******************************************************************************
    ** Fluent setter for autoApproveScope
    *******************************************************************************/
   public Agent withAutoApproveScope(String autoApproveScope)
   {
      this.autoApproveScope = autoApproveScope;
      return (this);
   }



   /*******************************************************************************
    ** Getter for escalationRules
    *******************************************************************************/
   public String getEscalationRules()
   {
      return (this.escalationRules);
   }



   /*******************************************************************************
    ** Setter for escalationRules
    *******************************************************************************/
   public void setEscalationRules(String escalationRules)
   {
      this.escalationRules = escalationRules;
   }



   /*******************************************************************************
    ** Fluent setter for escalationRules
    *******************************************************************************/
   public Agent withEscalationRules(String escalationRules)
   {
      this.escalationRules = escalationRules;
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
   public Agent withStatus(String status)
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
   public Agent withCreateDate(Instant createDate)
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
   public Agent withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
