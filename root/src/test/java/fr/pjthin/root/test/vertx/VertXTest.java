package fr.pjthin.root.test.vertx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.vertx.java.core.Vertx;
import org.vertx.java.test.VertxAware;
import org.vertx.java.test.VertxConfiguration;
import org.vertx.java.test.junit.VertxJUnit4ClassRunner;

@RunWith(VertxJUnit4ClassRunner.class)
@VertxConfiguration(hostname = "127.0.0.1", port = 8080)
public class VertXTest implements VertxAware {

	private Vertx vertx;

	@Test
	public void firstTest() {
		vertx.createHttpClient().get("http://127.0.0.1:8080/hello", h -> {
			h.dataHandler(d -> {
				d.length();
			});
		});
	}

	@Override
	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
	}
}
