package com.kof22.concilium.metadata;


import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.instances.AbstractQQQApplication;
import com.kingsrook.qqq.backend.core.model.metadata.MetaDataProducerHelper;
import com.kingsrook.qqq.backend.core.model.metadata.QAuthenticationType;
import com.kingsrook.qqq.backend.core.model.metadata.QBackendMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.backend.core.model.metadata.authentication.QAuthenticationMetaData;
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

      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.model");
      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.metadata");

      /////////////////////////////////////////////////////////////////////////
      // Set the default backend on all auto-discovered tables that do not   //
      // already have one. The @QMetaDataProducingEntity annotation does not //
      // set a backend, so we assign memory for now (Postgres in Phase 3).   //
      /////////////////////////////////////////////////////////////////////////
      for(QTableMetaData table : qInstance.getTables().values())
      {
         if(table.getBackendName() == null)
         {
            table.setBackendName(MEMORY_BACKEND_NAME);
         }
      }

      return qInstance;
   }



   /*******************************************************************************
    ** Define the in-memory backend (for dev/test, replaced by Postgres later).
    *******************************************************************************/
   private QBackendMetaData defineMemoryBackend()
   {
      return new QBackendMetaData()
         .withName(MEMORY_BACKEND_NAME)
         .withBackendType(MemoryBackendModule.class);
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
