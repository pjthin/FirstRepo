package fr.pjthin.root.vertx.web;

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

public class WebApp extends AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebApp.class);

	/**
	 * Generic failed handler for {@link Route}
	 * 
	 * @param routingContext
	 */
	public void genericFailed(RoutingContext routingContext) {
		// just to be sure
		if (routingContext.failed()) {
			LOGGER.error("WebApp.genericFailed: Failed routing", routingContext.failure());
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
			LOGGER.info("WebApp.start: enter path '/hello'");
			h.response().end("Hello World !");
		}).failureHandler(this::genericFailed);

		// EventBus url
		SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
		sockJSHandler.bridge(createBridgeAuthorization(), this::handleEventBus);
		router.route("/eventbus/*").handler(sockJSHandler).failureHandler(this::genericFailed);

		router.route("/*").handler(h -> {
			LOGGER.info(String.format("WebApp.start: enter path '%s'", h.normalisedPath()));
			h.response().setStatusCode(404).end("Page not found!");
		}).failureHandler(this::genericFailed);

		// Creating server
		vertx.createHttpServer(new HttpServerOptions().setHost("127.0.0.1").setPort(8080))
				.requestHandler(router::accept).listen(lh -> {
					if (lh.succeeded()) {
						startFuture.complete();
					}
					else {
						startFuture.fail(lh.cause());
					}
				});
	}

	public void handleEventBus(BridgeEvent event) {
		// TODO
	}

	private BridgeOptions createBridgeAuthorization() {
		BridgeOptions options = new BridgeOptions();
		options.addInboundPermitted(
		// authorized input from "fr.pjthin.ev.in" with json like
		// {fromPage:'index.html',...}
				new PermittedOptions().setAddress("fr.pjthin.ev.in").setMatch(
						new JsonObject().put("fromPage", "index.html")))
		// authorized output all fr.pjthin.ev.out.*
				.addOutboundPermitted(new PermittedOptions().setAddressRegex("fr\\.pjthin\\.ev\\.out\\..*"));
		return options;
	}

}