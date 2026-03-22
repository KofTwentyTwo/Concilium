package com.kof22.concilium.metadata.health;


import java.util.List;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.middleware.health.HealthIndicator;
import com.kingsrook.qqq.middleware.health.HealthMetaDataProducer;
import com.kingsrook.qqq.middleware.health.indicators.BasicAliveHealthIndicator;
import com.kingsrook.qqq.middleware.health.indicators.MemoryHealthIndicator;
import com.kingsrook.qqq.middleware.health.model.metadata.HealthCheckMetaData;


/*******************************************************************************
 ** MetaData producer for Concilium health check configuration.
 ** Provides a /api/health endpoint for K8s liveness/readiness probes and CI.
 *******************************************************************************/
public class ConciliumHealthMetaDataProducer extends HealthMetaDataProducer
{
   private static final Integer MEMORY_THRESHOLD_PERCENT = 85;
   private static final Integer OVERALL_TIMEOUT_MS       = 5000;



   /*******************************************************************************
    ** Build health check configuration for Concilium.
    *******************************************************************************/
   @Override
   protected HealthCheckMetaData buildHealthCheckMetaData(QInstance qInstance) throws QException
   {
      List<HealthIndicator> indicators = List.of(
         new BasicAliveHealthIndicator(),
         new MemoryHealthIndicator()
            .withThreshold(MEMORY_THRESHOLD_PERCENT)
      );

      return new HealthCheckMetaData()
         .withEnabled(true)
         .withEndpointPath("/api/health")
         .withIndicators(indicators)
         .withTimeoutMs(OVERALL_TIMEOUT_MS);
   }
}
