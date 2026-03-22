package com.kof22.concilium.metadata;


import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.instances.AbstractQQQApplication;
import com.kingsrook.qqq.backend.core.model.metadata.MetaDataProducerHelper;
import com.kingsrook.qqq.backend.core.model.metadata.QAuthenticationType;
import com.kingsrook.qqq.backend.core.model.metadata.QBackendMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.backend.core.model.metadata.authentication.QAuthenticationMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.fields.QFieldMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.fields.QFieldType;
import com.kingsrook.qqq.backend.core.model.metadata.tables.QTableMetaData;
import com.kingsrook.qqq.backend.core.modules.backend.implementations.memory.MemoryBackendModule;


/*******************************************************************************
 ** MetaData provider for the Concilium application.
 ** Extends AbstractQQQApplication to define the full QInstance.
 *******************************************************************************/
public class ConciliumMetaDataProvider extends AbstractQQQApplication
{
   public static final String MEMORY_BACKEND_NAME = "memory";



   /*******************************************************************************
    ** Define the QInstance for Concilium.
    *******************************************************************************/
   @Override
   public QInstance defineQInstance() throws QException
   {
      QInstance qInstance = new QInstance();

      qInstance.addBackend(defineMemoryBackend());
      qInstance.withInstanceDefaultAuthentication(defineAuthentication());
      qInstance.addTable(defineMasterProjectTable());

      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.model");
      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.metadata");

      return qInstance;
   }



   /*******************************************************************************
    ** Define the in-memory backend (for dev/test, replaced by Postgres in Plan 2).
    *******************************************************************************/
   private QBackendMetaData defineMemoryBackend()
   {
      return new QBackendMetaData()
         .withName(MEMORY_BACKEND_NAME)
         .withBackendType(MemoryBackendModule.class);
   }



   /*******************************************************************************
    ** Define the master_project table (bootstrap placeholder -- will be replaced
    ** by entity-driven MetaDataProducers in a later task).
    *******************************************************************************/
   private QTableMetaData defineMasterProjectTable()
   {
      return new QTableMetaData()
         .withName("master_project")
         .withLabel("Master Project")
         .withBackendName(MEMORY_BACKEND_NAME)
         .withPrimaryKeyField("id")
         .withField(new QFieldMetaData("id", QFieldType.INTEGER))
         .withField(new QFieldMetaData("name", QFieldType.STRING))
         .withField(new QFieldMetaData("description", QFieldType.STRING));
   }



   /*******************************************************************************
    ** Define mock authentication (for dev, replaced by OAuth2 in later phase).
    *******************************************************************************/
   private QAuthenticationMetaData defineAuthentication()
   {
      return new QAuthenticationMetaData()
         .withName("mock")
         .withType(QAuthenticationType.MOCK);
   }
}
