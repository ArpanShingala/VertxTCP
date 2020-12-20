package example;

import io.vertx.core.Vertx;

public class Application {

    public static Vertx vertx = null;
    private static int CLIENT_COUNT = 10;

    public static void main(String[] args) {
        vertx = Vertx.vertx();
        System.out.println("Deploying verticles");
        vertx.deployVerticle(new VertxTcpServerVerticle(vertx), result -> {
            if (result.succeeded()) {
                // deploy clients to spam messages to server
                for (int i = 0; i < CLIENT_COUNT; i++) {
                    vertx.deployVerticle(new VertxTcpClientVerticle(vertx, "Client " + i));
                }
                System.out.println("Deployed verticles");
            } else if (result.failed()) {
                result.cause().printStackTrace();
            } else {
                System.out.println(result.result());
            }
        });
    }
}
