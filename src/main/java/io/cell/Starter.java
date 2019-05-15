package io.cell;

import io.cell.services.MessageRouterVerticle;
import io.cell.services.RestServerVerticle;
import io.cell.services.WebSocketServerVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Starter {
    public static void main(String[] args) {
        ConfigStoreOptions store = new ConfigStoreOptions()
            .setType("file")
            .setFormat("json")
            .setConfig(new JsonObject().put("path", "config.json"));
        ConfigRetriever retriever = ConfigRetriever.create(Vertx.vertx(), new ConfigRetrieverOptions().addStore(store));
        deploy(Vertx.vertx());
    }

    private static void deploy(Vertx vertx) {
        vertx.deployVerticle(new WebSocketServerVerticle());
        vertx.deployVerticle(new MessageRouterVerticle());
        vertx.deployVerticle(new RestServerVerticle());
    }
}
