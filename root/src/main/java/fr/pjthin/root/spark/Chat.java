package fr.pjthin.root.spark;

import static j2html.TagCreator.article;
import static j2html.TagCreator.b;
import static j2html.TagCreator.p;
import static j2html.TagCreator.span;
import static spark.Spark.get;
import static spark.Spark.init;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;
import static spark.Spark.webSocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import io.vertx.core.Vertx;

public class Chat {

    static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
    static int nextUserNumber = 1;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // index.html is served at localhost:4567 (default port)
        staticFiles.location("/public");
        staticFiles.expireTime(600);
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
        get("/hello", (req, res) -> "Hello World");
        get("/hello/:name", (req, res) -> "Hello " + StringUtils.defaultString(req.params(":name"), "World"));
        get("/shutdown", (req, res) -> {
            stop();
            vertx.close();
            return "Ok";
        });
        vertx.setPeriodic(10000, (time) -> {
            broadcastMessage("System", "Time is up");
        });
    }

    // Sends a message from one user to all users, along with a list of current
    // usernames
    public static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(
                        String.valueOf(new JSONObject().put("userMessage", createHtmlMessageFromSender(sender, message))
                                .put("userlist", userUsernameMap.values())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Builds a HTML element with a sender-name, a message, and a timestamp,
    private static String createHtmlMessageFromSender(String sender, String message) {
        return article()
                .with(b(sender + " says:"), p(message),
                        span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date())))
                .render();
    }
}
