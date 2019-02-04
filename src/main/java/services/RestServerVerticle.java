package services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import static services.EventBusAddresses.ROUTING_MESSAGE;

public class RestServerVerticle extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(RestServerVerticle.class);

  @Override
  public void start() {
        HttpServer httpServer = vertx.createHttpServer();
        Router httpRouter = Router.router(vertx);
        httpRouter.route().handler(BodyHandler.create());
        httpRouter.post("/message")
                .handler(request -> {
                    String jsonMessage = request.getBodyAsString();
                    LOG.debug("Message received: " + jsonMessage);
                    vertx.eventBus().send(ROUTING_MESSAGE, jsonMessage);
                    request.response().end("Ok");
                });
        httpServer.requestHandler(httpRouter::accept);
        httpServer.listen(8081);
    }
}