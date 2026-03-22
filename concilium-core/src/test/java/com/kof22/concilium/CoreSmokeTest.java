package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Smoke test verifying QQQ core is on the classpath and functional.
 *******************************************************************************/
class CoreSmokeTest
{


   /*******************************************************************************
    ** Verify QInstance can be instantiated from qqq-backend-core.
    *******************************************************************************/
   @Test
   void testQInstanceAvailable()
   {
      QInstance qInstance = new QInstance();
      assertThat(qInstance).isNotNull();
      assertThat(qInstance.getTables()).isEmpty();
   }
}
