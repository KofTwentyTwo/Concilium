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


/*******************************************************************************
 ** QRecordEntity for the agent_memory table.
 **
 ** Stores durable memory entries for agents across sessions and machines.
 ** Memory is categorized by type (operational, episodic, knowledge, summary)
 ** and subject for efficient retrieval and relevance scoring.
 *******************************************************************************/
@Entity
@Table(name = AgentMemory.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
public class AgentMemory extends QRecordEntity
{
   public static final String TABLE_NAME = "agent_memory";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "agent_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = Agent.TABLE_NAME)
   private Long agentId;

   @Column(name = "memory_type", nullable = false)
   @QField(isRequired = true, maxLength = 50)
   private String memoryType;

   @Column(name = "category")
   @QField(maxLength = 100)
   private String category;

   @Column(name = "subject")
   @QField(maxLength = 500)
   private String subject;

   @Column(name = "content")
   @QField(maxLength = 100000)
   private String content;

   @Column(name = "relevance_score")
   @QField(defaultValue = "50")
   private Integer relevanceScore;

   @Column(name = "last_accessed_date")
   @QField()
   private Instant lastAccessedDate;

   @Column(name = "create_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;

   @Column(name = "modify_date")
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.MODIFY_DATE)
   private Instant modifyDate;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public AgentMemory()
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
   public AgentMemory withId(Long id)
   {
      this.id = id;
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
   public AgentMemory withAgentId(Long agentId)
   {
      this.agentId = agentId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for memoryType
    *******************************************************************************/
   public String getMemoryType()
   {
      return (this.memoryType);
   }



   /*******************************************************************************
    ** Setter for memoryType
    *******************************************************************************/
   public void setMemoryType(String memoryType)
   {
      this.memoryType = memoryType;
   }



   /*******************************************************************************
    ** Fluent setter for memoryType
    *******************************************************************************/
   public AgentMemory withMemoryType(String memoryType)
   {
      this.memoryType = memoryType;
      return (this);
   }



   /*******************************************************************************
    ** Getter for category
    *******************************************************************************/
   public String getCategory()
   {
      return (this.category);
   }



   /*******************************************************************************
    ** Setter for category
    *******************************************************************************/
   public void setCategory(String category)
   {
      this.category = category;
   }



   /*******************************************************************************
    ** Fluent setter for category
    *******************************************************************************/
   public AgentMemory withCategory(String category)
   {
      this.category = category;
      return (this);
   }



   /*******************************************************************************
    ** Getter for subject
    *******************************************************************************/
   public String getSubject()
   {
      return (this.subject);
   }



   /*******************************************************************************
    ** Setter for subject
    *******************************************************************************/
   public void setSubject(String subject)
   {
      this.subject = subject;
   }



   /*******************************************************************************
    ** Fluent setter for subject
    *******************************************************************************/
   public AgentMemory withSubject(String subject)
   {
      this.subject = subject;
      return (this);
   }



   /*******************************************************************************
    ** Getter for content
    *******************************************************************************/
   public String getContent()
   {
      return (this.content);
   }



   /*******************************************************************************
    ** Setter for content
    *******************************************************************************/
   public void setContent(String content)
   {
      this.content = content;
   }



   /*******************************************************************************
    ** Fluent setter for content
    *******************************************************************************/
   public AgentMemory withContent(String content)
   {
      this.content = content;
      return (this);
   }



   /*******************************************************************************
    ** Getter for relevanceScore
    *******************************************************************************/
   public Integer getRelevanceScore()
   {
      return (this.relevanceScore);
   }



   /*******************************************************************************
    ** Setter for relevanceScore
    *******************************************************************************/
   public void setRelevanceScore(Integer relevanceScore)
   {
      this.relevanceScore = relevanceScore;
   }



   /*******************************************************************************
    ** Fluent setter for relevanceScore
    *******************************************************************************/
   public AgentMemory withRelevanceScore(Integer relevanceScore)
   {
      this.relevanceScore = relevanceScore;
      return (this);
   }



   /*******************************************************************************
    ** Getter for lastAccessedDate
    *******************************************************************************/
   public Instant getLastAccessedDate()
   {
      return (this.lastAccessedDate);
   }



   /*******************************************************************************
    ** Setter for lastAccessedDate
    *******************************************************************************/
   public void setLastAccessedDate(Instant lastAccessedDate)
   {
      this.lastAccessedDate = lastAccessedDate;
   }



   /*******************************************************************************
    ** Fluent setter for lastAccessedDate
    *******************************************************************************/
   public AgentMemory withLastAccessedDate(Instant lastAccessedDate)
   {
      this.lastAccessedDate = lastAccessedDate;
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
   public AgentMemory withCreateDate(Instant createDate)
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
   public AgentMemory withModifyDate(Instant modifyDate)
   {
      this.modifyDate = modifyDate;
      return (this);
   }
}
