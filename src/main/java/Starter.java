import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import services.MessageRouterVerticle;
import services.RestServerVerticle;
import services.WebSocketServerVerticle;

public class Starter {
    public static void main(String[] args) {
        ConfigStoreOptions store = new ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(new JsonObject()
                .put("path", "application.yml")
            );
        ConfigRetriever retriever = ConfigRetriever.create(Vertx.vertx(), new ConfigRetrieverOptions().addStore(store));
        deploy(Vertx.vertx());
    }

    private static void deploy(Vertx vertx) {
        vertx.deployVerticle(new WebSocketServerVerticle());
        vertx.deployVerticle(new MessageRouterVerticle());
        vertx.deployVerticle(new RestServerVerticle());
    }
}
