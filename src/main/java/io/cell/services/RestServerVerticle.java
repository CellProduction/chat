package io.cell.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Optional;

import static io.cell.services.EventBusAddresses.ROUTING_MESSAGE;

/**
 * Rest для приема сообщений
 */
public class RestServerVerticle extends AbstractVerticle {

    public static final Logger LOG = LoggerFactory.getLogger(RestServerVerticle.class);

    @Override
    public void start() {
        HttpServer httpServer = vertx.createHttpServer();
        Router httpRouter = Router.router(vertx);
        Integer defaultPort = 8080;
        String port = Optional.ofNullable(System.getenv("PORT")).orElse(defaultPort.toString());
        httpRouter.route().handler(BodyHandler.create());
        httpRouter.post("/message").handler(this::messageRecivedHandler);
        httpServer.requestHandler(httpRouter::accept);
        httpServer.listen(Integer.valueOf(port));
        LOG.info("Started: rest vertical. Port: " + httpServer.actualPort());
    }

    private void messageRecivedHandler(RoutingContext requestHandler) {
        String jsonMessage = requestHandler.getBodyAsString();
        LOG.debug("Message received: " + jsonMessage);
        vertx.eventBus().send(ROUTING_MESSAGE, jsonMessage);
        requestHandler.response().end("Ok");
    }
}