package fr.pjthin.root.vertx.verticle;

import io.vertx.core.Future;
import io.vertx.core.Handler;

public interface UserActor {

	public void createUser(String user, Handler<Future<Long>> handler);

}
