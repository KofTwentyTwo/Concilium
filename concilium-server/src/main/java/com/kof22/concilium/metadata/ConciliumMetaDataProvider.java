package com.kof22.concilium.metadata;


import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.instances.AbstractQQQApplication;
import com.kingsrook.qqq.backend.core.instances.QInstanceEnricher;
import com.kingsrook.qqq.backend.core.model.metadata.MetaDataProducerHelper;
import com.kingsrook.qqq.backend.core.model.metadata.QAuthenticationType;
import com.kingsrook.qqq.backend.core.model.metadata.QBackendMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.backend.core.instances.QMetaDataVariableInterpreter;
import com.kingsrook.qqq.backend.core.model.metadata.authentication.QAuthenticationMetaData;
import com.kingsrook.qqq.backend.core.model.metadata.tables.QTableMetaData;
import com.kingsrook.qqq.backend.core.modules.backend.implementations.memory.MemoryBackendModule;
import com.kingsrook.qqq.backend.module.postgres.model.metadata.PostgreSQLBackendMetaData;


/*******************************************************************************
 ** MetaData provider for the Concilium application.
 ** Extends AbstractQQQApplication to define the full QInstance.
 *******************************************************************************/
public class ConciliumMetaDataProvider extends AbstractQQQApplication
{
   public static final String MEMORY_BACKEND_NAME   = "memory";
   public static final String POSTGRES_BACKEND_NAME = "postgres";



   /*******************************************************************************
    ** Define the QInstance for Concilium.
    *******************************************************************************/
   @Override
   public QInstance defineQInstance() throws QException
   {
      QInstance qInstance = new QInstance();

      qInstance.addBackend(defineMemoryBackend());
      qInstance.withInstanceDefaultAuthentication(defineAuthentication());

      ///////////////////////////////////////////////////////////////////////////
      // If RDBMS_VENDOR is set to postgresql, add and prefer Postgres backend //
      ///////////////////////////////////////////////////////////////////////////
      boolean usePostgres = isPostgresConfigured();
      String defaultBackendName = MEMORY_BACKEND_NAME;

      if(usePostgres)
      {
         qInstance.addBackend(definePostgresBackend());
         defaultBackendName = POSTGRES_BACKEND_NAME;
      }

      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.model");
      MetaDataProducerHelper.processAllMetaDataProducersInPackage(qInstance, "com.kof22.concilium.metadata");

      /////////////////////////////////////////////////////////////////////////
      // Set the default backend on all auto-discovered tables that do not   //
      // already have one. The @QMetaDataProducingEntity annotation does not //
      // set a backend, so we assign it based on environment configuration.  //
      /////////////////////////////////////////////////////////////////////////
      for(QTableMetaData table : qInstance.getTables().values())
      {
         if(table.getBackendName() == null)
         {
            table.setBackendName(defaultBackendName);
         }

         //////////////////////////////////////////////////////////////////////
         // For Postgres-backed tables, set inferred backend names so that   //
         // camelCase Java field names map to snake_case column names.        //
         //////////////////////////////////////////////////////////////////////
         if(POSTGRES_BACKEND_NAME.equals(table.getBackendName()))
         {
            QInstanceEnricher.setInferredFieldBackendNames(table);
         }
      }

      return qInstance;
   }



   /*******************************************************************************
    ** Check whether Postgres is configured via environment variables.
    *******************************************************************************/
   private boolean isPostgresConfigured()
   {
      QMetaDataVariableInterpreter interpreter = new QMetaDataVariableInterpreter();
      String vendor = interpreter.getStringFromPropertyOrEnvironment("RDBMS_VENDOR", "RDBMS_VENDOR", null);
      return "postgresql".equals(vendor);
   }



   /*******************************************************************************
    ** Define the PostgreSQL backend using environment variables for connection
    ** parameters. Expected env vars:
    **   RDBMS_HOSTNAME (default: localhost)
    **   RDBMS_PORT     (default: 5432)
    **   RDBMS_DATABASE (default: concilium)
    **   RDBMS_USERNAME (default: concilium)
    **   RDBMS_PASSWORD (default: concilium)
    *******************************************************************************/
   private PostgreSQLBackendMetaData definePostgresBackend()
   {
      QMetaDataVariableInterpreter interpreter = new QMetaDataVariableInterpreter();

      String  hostname = interpreter.getStringFromPropertyOrEnvironment("RDBMS_HOSTNAME", "RDBMS_HOSTNAME", "localhost");
      Integer port     = interpreter.getIntegerFromPropertyOrEnvironment("RDBMS_PORT", "RDBMS_PORT", 5432);
      String  database = interpreter.getStringFromPropertyOrEnvironment("RDBMS_DATABASE", "RDBMS_DATABASE", "concilium");
      String  username = interpreter.getStringFromPropertyOrEnvironment("RDBMS_USERNAME", "RDBMS_USERNAME", "concilium");
      String  password = interpreter.getStringFromPropertyOrEnvironment("RDBMS_PASSWORD", "RDBMS_PASSWORD", "concilium");

      return new PostgreSQLBackendMetaData()
         .withName(POSTGRES_BACKEND_NAME)
         .withHostName(hostname)
         .withPort(port)
         .withDatabaseName(database)
         .withUsername(username)
         .withPassword(password);
   }



   /*******************************************************************************
    ** Define the in-memory backend (for dev/test when Postgres is not configured).
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
