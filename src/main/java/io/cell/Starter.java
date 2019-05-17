package io.cell;

import io.cell.services.MessageRouterVerticle;
import io.cell.services.RestServerVerticle;
import io.cell.services.WebSocketServerVerticle;
import io.vertx.core.Vertx;

public class Starter {
    public static void main(String[] args) {
        deploy(Vertx.vertx());
    }

    private static void deploy(Vertx vertx) {
        vertx.deployVerticle(new WebSocketServerVerticle());
        vertx.deployVerticle(new MessageRouterVerticle());
        vertx.deployVerticle(new RestServerVerticle());
    }
}
