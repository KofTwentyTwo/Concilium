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
@Table(name = WorkItemLink.TABLE_NAME)
@QMetaDataProducingEntity(produceTableMetaData = true, producePossibleValueSource = true)
/*******************************************************************************
 ** Record entity for the work_item_link table.
 ** Represents a directed relationship between two work items -- capturing
 ** dependencies such as blocks, depends_on, parent_child, and related_to.
 *******************************************************************************/
public class WorkItemLink extends QRecordEntity
{
   public static final String TABLE_NAME = "work_item_link";

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   @QField(isEditable = false, isPrimaryKey = true)
   private Long id;

   @Column(name = "source_work_item_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = WorkItem.TABLE_NAME)
   private Long sourceWorkItemId;

   @Column(name = "target_work_item_id", nullable = false)
   @QField(isRequired = true, possibleValueSourceName = WorkItem.TABLE_NAME)
   private Long targetWorkItemId;

   @Column(name = "link_type", nullable = false, length = 50)
   @QField(isRequired = true, maxLength = 50, valueTooLongBehavior = ValueTooLongBehavior.ERROR)
   private String linkType;

   @Column(name = "create_date", nullable = false, updatable = false)
   @QField(dynamicDefaultValueBehavior = DynamicDefaultValueBehavior.CREATE_DATE)
   private Instant createDate;



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
   public WorkItemLink withId(Long id)
   {
      this.id = id;
      return (this);
   }



   /*******************************************************************************
    ** Getter for sourceWorkItemId
    ** @see #withSourceWorkItemId(Long)
    *******************************************************************************/
   public Long getSourceWorkItemId()
   {
      return (this.sourceWorkItemId);
   }



   /*******************************************************************************
    ** Setter for sourceWorkItemId
    ** @see #withSourceWorkItemId(Long)
    *******************************************************************************/
   public void setSourceWorkItemId(Long sourceWorkItemId)
   {
      this.sourceWorkItemId = sourceWorkItemId;
   }



   /*******************************************************************************
    ** Fluent setter for sourceWorkItemId
    ** @param sourceWorkItemId ID of the source work item in the relationship
    ** @return this
    *******************************************************************************/
   public WorkItemLink withSourceWorkItemId(Long sourceWorkItemId)
   {
      this.sourceWorkItemId = sourceWorkItemId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for targetWorkItemId
    ** @see #withTargetWorkItemId(Long)
    *******************************************************************************/
   public Long getTargetWorkItemId()
   {
      return (this.targetWorkItemId);
   }



   /*******************************************************************************
    ** Setter for targetWorkItemId
    ** @see #withTargetWorkItemId(Long)
    *******************************************************************************/
   public void setTargetWorkItemId(Long targetWorkItemId)
   {
      this.targetWorkItemId = targetWorkItemId;
   }



   /*******************************************************************************
    ** Fluent setter for targetWorkItemId
    ** @param targetWorkItemId ID of the target work item in the relationship
    ** @return this
    *******************************************************************************/
   public WorkItemLink withTargetWorkItemId(Long targetWorkItemId)
   {
      this.targetWorkItemId = targetWorkItemId;
      return (this);
   }



   /*******************************************************************************
    ** Getter for linkType
    ** @see #withLinkType(String)
    *******************************************************************************/
   public String getLinkType()
   {
      return (this.linkType);
   }



   /*******************************************************************************
    ** Setter for linkType
    ** @see #withLinkType(String)
    *******************************************************************************/
   public void setLinkType(String linkType)
   {
      this.linkType = linkType;
   }



   /*******************************************************************************
    ** Fluent setter for linkType
    ** @param linkType the relationship type: blocks, depends_on, parent_child, related_to
    ** @return this
    *******************************************************************************/
   public WorkItemLink withLinkType(String linkType)
   {
      this.linkType = linkType;
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
    ** @param createDate timestamp when this link was created
    ** @return this
    *******************************************************************************/
   public WorkItemLink withCreateDate(Instant createDate)
   {
      this.createDate = createDate;
      return (this);
   }
}
