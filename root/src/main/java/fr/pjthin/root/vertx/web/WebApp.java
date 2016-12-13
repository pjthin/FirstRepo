package fr.pjthin.root.vertx.web;

import fr.pjthin.root.vertx.verticle.UserActor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEvent;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.time.LocalTime;

public class WebApp extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);
	private UserActor userActor;

	/**
	 * Generic failed handler for {@link Route}
	 * 
	 * @param routingContext
	 */
	public void genericFailed(RoutingContext routingContext) {
		// just to be sure
		if (routingContext.failed()) {
			LOGGER.error("Failed routing", routingContext.failure());
			routingContext.response().end("An error occurred... Please try again.");
			;
		}
	}

	@Override
	public void start(Future<Void> startFuture) throws Exception {

		// Creating router
		Router router = Router.router(vertx);

		// Static ressources
		router.route("/static/*").handler(StaticHandler.create("webroot")).failureHandler(this::genericFailed);

		// First url
		router.get("/hello").handler(h -> {
			LOGGER.info("Enter path '/hello'");
			h.response().end("Hello World !");
		}).failureHandler(this::genericFailed);

		// EventBus url
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
		sockJSHandler.bridge(createBridgeAuthorization(), this::handleEventBus);
		router.route("/eventbus/*").handler(sockJSHandler).failureHandler(this::genericFailed);

		router.route("/*").handler(h -> {
			LOGGER.info(String.format("Enter path '%s'", h.normalisedPath()));
			h.response().setStatusCode(404).end("Page not found!");
		}).failureHandler(this::genericFailed);

		// Creating server
		vertx.createHttpServer(new HttpServerOptions().setHost("127.0.0.1").setPort(8080))
				.requestHandler(router::accept)
				.listen(lh -> {
					if (lh.succeeded()) {
						vertx.setPeriodic(
								1000,
								t -> {
									JsonObject json = new JsonObject().put("hello", "world").put("time",
											LocalTime.now().toString());
									LOGGER.trace("publish: " + json);
									vertx.eventBus().publish("fr.pjthin.ev.client", json);
								});
						startFuture.complete();
					}
					else {
						startFuture.fail(lh.cause());
					}
				});

	}

	public void handleEventBus(BridgeEvent event) {

		switch (event.type()) {
		case SOCKET_CREATED:
			LOGGER.info("A socket was created");
			break;
		case SOCKET_CLOSED:
			LOGGER.info("A socket was closed");
			break;
		// from clients to server
		case SEND:
		case PUBLISH:
			LOGGER.info("Client send: " + event.rawMessage());
			break;
		// from server to clients
		case RECEIVE:
			LOGGER.info("Server send: " + event.rawMessage());
			handleEventBusReceive(event.rawMessage().getString("address"),event.rawMessage().getJsonObject("body"));
			break;
		default:
			LOGGER.info("An event occurred: " + event.type());
			break;
		}

		// This signals that it's ok to process the event
		event.complete(true);
	}

	private void handleEventBusReceive(String address, JsonObject message) {
		switch (address) {
		case "fr.pjthin.ev.server.login":
			userActor.createUser(message.getString("user"), res -> {
				if (res.failed()) {
					LOGGER.info(String.format("Failed to create user %s. Cause : %s", message.getString("user"), res
							.cause().getMessage()));
				}
				else {
//					vertx.eventBus().se
				}
			});
			break;

		default:
			break;
		}
	}

	private BridgeOptions createBridgeAuthorization() {
		BridgeOptions options = new BridgeOptions();
		options
		// authorized input fr.pjthin.ev.server
		.addInboundPermitted(new PermittedOptions().setAddress("fr.pjthin.ev.server"))
		.addInboundPermitted(new PermittedOptions().setAddress("fr.pjthin.ev.server.login").setMatch(new JsonObject().put("user", "test")))
		// authorized output fr.pjthin.ev.client
		.addOutboundPermitted(new PermittedOptions().setAddress("fr.pjthin.ev.client"));
		return options;
	}

	public void setUserActor(UserActor userActor) {
		this.userActor = userActor;
	}

}
