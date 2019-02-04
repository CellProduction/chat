import io.vertx.core.Vertx;
import services.MessageRouterVerticle;
import services.RestServerVerticle;
import services.WebSocketServerVerticle;

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
