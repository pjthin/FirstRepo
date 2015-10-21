package fr.pjthin.root.vertx;

import fr.pjthin.root.vertx.web.WebApp;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

public class StartMain {

    static Verticle webapp = (Verticle) new WebApp();

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(webapp, h -> {
            if (h.succeeded()) {
                System.out.println("Web App deployed!");
            } else {
                System.err.println("Failed: " + h.cause().getMessage());
            }
        });
    }
}
