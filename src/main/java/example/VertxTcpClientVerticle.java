package example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.time.LocalDateTime;

public class VertxTcpClientVerticle extends AbstractVerticle {

    private Vertx vertx;
    private String name;

    public VertxTcpClientVerticle(Vertx vertx, String name) {
        this.vertx = vertx;
        this.name = name;
    }

    @Override
    public void start() {
        NetClient tcpClient = vertx.createNetClient();
        tcpClient.connect(1234, "localhost",
                result -> {
                    if (result.succeeded()) {
                        NetSocket socket = result.result();

                        // message sent for every 3000 ms
                        vertx.setPeriodic(3000, aLong -> socket.write("Hello World from " + name + " at " + LocalDateTime.now()));

                        // prints the message received from server
                        socket.handler(buffer -> System.out.println("Echo back message - " + buffer));
                    } else {
                        result.cause().printStackTrace();
                    }
                });
    }
}
