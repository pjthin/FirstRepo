package fr.pjthin.root.test.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;

public class VertXTest {

    public static void main(String[] args) {
        Vertx.vertx().createHttpServer(new HttpServerOptions().setHost("localhost").setPort(1234))
                .requestHandler(req -> req.response().end("HELLO WORLD !")).listen(serv -> {
                    if (serv.succeeded()) {
                        System.out.println("Listening !");
                    } else {
                        System.out.println(serv.cause());
                    }
                });
    }
}
