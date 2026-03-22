package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kof22.concilium.metadata.ConciliumMetaDataProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Smoke test verifying the ConciliumMetaDataProvider can produce a QInstance.
 *******************************************************************************/
class ServerSmokeTest
{


   /*******************************************************************************
    ** Verify QInstance is produced without exceptions.
    *******************************************************************************/
   @Test
   void testMetaDataProviderProducesInstance() throws Exception
   {
      ConciliumMetaDataProvider provider = new ConciliumMetaDataProvider();
      QInstance qInstance = provider.defineValidatedQInstance();
      assertThat(qInstance).isNotNull();
   }
}
