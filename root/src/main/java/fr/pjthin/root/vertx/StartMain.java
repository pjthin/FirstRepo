package fr.pjthin.root.vertx;

import io.vertx.core.Vertx;
import fr.pjthin.root.vertx.verticle.UserActorVerticle;
import fr.pjthin.root.vertx.web.WebApp;

public class StartMain {

    static WebApp webapp = new WebApp();
    static UserActorVerticle userActor = new UserActorVerticle();

    public static void main(String[] args) {
    	webapp.setUserActor(userActor);
    	Vertx v = Vertx.vertx();
        v.deployVerticle(webapp, h -> {
            if (h.succeeded()) {
                System.out.println("Web App deployed!");
            } else {
                System.err.println("Failed: " + h.cause().getMessage());
            }
        });
        v.deployVerticle(userActor);
    }
}
