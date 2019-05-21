package io.cell.services;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Optional;

import static io.cell.services.EventBusAddresses.ROUTING_MESSAGE;

/**
 * WS сервер для приема сообщений
 */
public class WebSocketServerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServerVerticle.class);

    @Override
    public void start() {
        Integer defaultPort = 8090;
        String port = Optional.ofNullable(System.getenv("WS_PORT")).orElse(defaultPort.toString());
        HttpServer httpServer = vertx.createHttpServer()
                .websocketHandler(this::createWebSocketServer)
                .listen(Integer.valueOf(port));
        LOG.info("Started: WS vertical. Port: " + httpServer.actualPort());
    }

    private void createWebSocketServer(ServerWebSocket wsServer) {
        LOG.info("Create WebSocket: " + wsServer.path());
        wsServer.frameHandler(wsFrame -> {
            LOG.info(wsFrame.textData());
            vertx.eventBus().send(ROUTING_MESSAGE, wsFrame.textData());
        });

        MessageConsumer<String> consumerSendMessage = vertx.eventBus().<String>consumer(wsServer.path(), data -> {
            wsServer.writeFinalTextFrame(data.body());
            data.reply("ok");
        });

        wsServer.closeHandler(aVoid -> {
            LOG.info("Close WebSocket: " + consumerSendMessage.address());
            consumerSendMessage.unregister();
        });
    }
}
