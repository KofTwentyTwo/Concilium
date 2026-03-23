package com.kof22.concilium;


import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import com.kingsrook.qqq.middleware.javalin.QApplicationJavalinServer;
import com.kof22.concilium.events.EventBroadcaster;
import com.kof22.concilium.metadata.ConciliumMetaDataProvider;


/*******************************************************************************
 ** Main entry point for the Concilium backend server.
 *******************************************************************************/
public class ConciliumServer
{
   private static final QLogger LOG = QLogger.getLogger(ConciliumServer.class);

   private static final Integer DEFAULT_PORT = 8000;



   /*******************************************************************************
    ** Main method.
    *******************************************************************************/
   public static void main(String[] args)
   {
      new ConciliumServer().start();
   }



   /*******************************************************************************
    ** Start the Javalin server with QQQ.
    *******************************************************************************/
   public void start()
   {
      try
      {
         Integer port = getPort();

         QApplicationJavalinServer javalinServer = new QApplicationJavalinServer(new ConciliumMetaDataProvider())
            .withServeFrontendMaterialDashboard(true)
            .withPort(port);

         ////////////////////////////////////////////////////////////////
         // Register WebSocket endpoint for real-time event streaming //
         // via the Javalin configuration customizer hook.            //
         ////////////////////////////////////////////////////////////////
         javalinServer.withJavalinConfigurationCustomizer(javalin ->
         {
            javalin.ws("/ws/events", ws ->
            {
               ws.onConnect(EventBroadcaster::addClient);
               ws.onClose(EventBroadcaster::removeClient);
               ws.onError(ctx -> EventBroadcaster.removeClient(ctx));
            });
         });

         javalinServer.start();

         LOG.info("Concilium server started", new LogPair("port", port));
      }
      catch(Exception e)
      {
         LOG.error("Failed to start Concilium server", e);
         System.exit(1);
      }
   }



   /*******************************************************************************
    ** Get port from environment or use default.
    *******************************************************************************/
   private Integer getPort()
   {
      String portEnv = System.getenv("SERVER_PORT");
      if(portEnv != null)
      {
         try
         {
            return Integer.valueOf(portEnv);
         }
         catch(NumberFormatException e)
         {
            LOG.warn("Invalid SERVER_PORT value, using default", new LogPair("invalidValue", portEnv), new LogPair("defaultPort", DEFAULT_PORT));
         }
      }
      return DEFAULT_PORT;
   }
}
