package fr.pjthin.root.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class UserActorVerticle extends AbstractVerticle implements UserActor {

	static class User {
		Long id;
		String name;
	}

	private ConcurrentHashMap<Long, User> allUser;
	private Random rand;

	@Override
	public void start() throws Exception {
		allUser = new ConcurrentHashMap<>();
		rand = new Random();
	}

	@Override
	public void createUser(String user, Handler<Future<Long>> handler) {
		if (user == null) {
			handler.handle(Future.failedFuture("User can't be null"));
		}
		else if (allUser.contains(user)) {
			handler.handle(Future.failedFuture("User already exists"));
		}
		else {
			handler.handle(Future.succeededFuture(createInternalUser(user)));
		}
	}

	private Long createInternalUser(String user) {
		User u = new User();
		u.name = user;
		u.id = rand.nextLong();
		allUser.put(u.id, u);
		return u.id;
	}

}
