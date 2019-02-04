package services;

import entities.MessageEntity;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import static services.EventBusAddresses.CELL_ADDRESS_PREFIX;
import static services.EventBusAddresses.ROUTING_MESSAGE;

/**
 * Роутер сообщений, распределяет пришедшие сообщения по нужным чат комнатам.
 */
public class MessageRouterVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServerVerticle.class);

    @Override
    public void start() {
        vertx.eventBus().consumer(ROUTING_MESSAGE, this::router);
    }

    private void router(Message<String> message) {
        if (message.body() != null && !message.body().isEmpty()) {
            LOG.info("Router message: " + message.body());
            MessageEntity data = Json.decodeValue(message.body(), MessageEntity.class);
            LOG.info(data);
            vertx.eventBus().send(CELL_ADDRESS_PREFIX + data.getAddress(), message.body());
        }
    }
}
