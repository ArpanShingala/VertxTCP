package example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;

public class VertxTcpServerVerticle extends AbstractVerticle {

    public static NetServer server = null;
    private Vertx vertx;

    public VertxTcpServerVerticle(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        server = vertx.createNetServer();

        // echo back the message
        server.connectHandler(socket -> socket.handler(socket::write));

        server.listen(1234, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Echo server is now listening!");
                startPromise.complete();
            } else {
                startPromise.fail("Failed to bind!");
            }
        });
    }
}
