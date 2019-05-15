package io.cell.services;

import io.cell.entities.MessageEntity;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import static io.cell.services.EventBusAddresses.CELL_ADDRESS_PREFIX;
import static io.cell.services.EventBusAddresses.ROUTING_MESSAGE;

/**
 * Роутер сообщений, распределяет пришедшие сообщения по нужным чат комнатам.
 */
public class MessageRouterVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(MessageRouterVerticle.class);

    @Override
    public void start() {
        vertx.eventBus().consumer(ROUTING_MESSAGE, this::router);
        LOG.info("Started: router vertical");
    }

    private void router(Message<String> message) {
        if (message.body() != null && !message.body().isEmpty()) {
            LOG.info("Router message: " + message.body());
            MessageEntity data = Json.decodeValue(message.body(), MessageEntity.class);
            vertx.eventBus().send(CELL_ADDRESS_PREFIX + data.getAddress(), message.body());
        }
    }
}
