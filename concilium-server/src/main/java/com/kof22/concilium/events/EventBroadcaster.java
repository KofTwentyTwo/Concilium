package com.kof22.concilium.events;


import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kingsrook.qqq.backend.core.logging.LogPair;
import com.kingsrook.qqq.backend.core.logging.QLogger;
import io.javalin.websocket.WsContext;


/*******************************************************************************
 ** Manages WebSocket client connections and broadcasts {@link ConciliumEvent}
 ** instances to all connected clients.
 **
 ** Clients are tracked by their Javalin session ID in a thread-safe map.
 ** Dead connections are automatically pruned during broadcast when a send fails.
 *******************************************************************************/
public class EventBroadcaster
{
   private static final QLogger LOG = QLogger.getLogger(EventBroadcaster.class);

   private static final ConcurrentHashMap<String, WsContext> clients = new ConcurrentHashMap<>();

   private static final ObjectMapper objectMapper = createObjectMapper();



   /*******************************************************************************
    ** Create and configure the ObjectMapper for event serialization.
    *******************************************************************************/
   private static ObjectMapper createObjectMapper()
   {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      return mapper;
   }



   /*******************************************************************************
    ** Register a new WebSocket client connection.
    *******************************************************************************/
   public static void addClient(WsContext ctx)
   {
      clients.put(ctx.sessionId(), ctx);
      LOG.info("WebSocket client connected", new LogPair("sessionId", ctx.sessionId()), new LogPair("clientCount", clients.size()));
   }



   /*******************************************************************************
    ** Remove a WebSocket client connection.
    *******************************************************************************/
   public static void removeClient(WsContext ctx)
   {
      clients.remove(ctx.sessionId());
      LOG.info("WebSocket client disconnected", new LogPair("sessionId", ctx.sessionId()), new LogPair("clientCount", clients.size()));
   }



   /*******************************************************************************
    ** Return the current number of connected WebSocket clients.
    *******************************************************************************/
   public static Integer getClientCount()
   {
      return clients.size();
   }



   /*******************************************************************************
    ** Broadcast a {@link ConciliumEvent} to all connected WebSocket clients.
    **
    ** Events are serialized to JSON and sent to each client.  If a send fails
    ** (e.g., the connection is dead), the client is automatically removed.
    *******************************************************************************/
   public static void broadcast(ConciliumEvent event)
   {
      try
      {
         String json = objectMapper.writeValueAsString(event);

         clients.values().removeIf(ctx ->
         {
            try
            {
               ctx.send(json);
               return false;
            }
            catch(Exception e)
            {
               LOG.info("Removing dead WebSocket client", new LogPair("sessionId", ctx.sessionId()));
               return true;
            }
         });
      }
      catch(Exception e)
      {
         LOG.error("Failed to serialize event for broadcast", e, new LogPair("eventType", event.getEventType()));
      }
   }



   /*******************************************************************************
    ** Visible for testing -- return the underlying ObjectMapper.
    *******************************************************************************/
   static ObjectMapper getObjectMapper()
   {
      return objectMapper;
   }
}
