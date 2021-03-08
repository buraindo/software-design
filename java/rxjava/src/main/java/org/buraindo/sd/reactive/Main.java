package org.buraindo.sd.reactive;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.buraindo.sd.reactive.db.RxDatabase;
import org.buraindo.sd.reactive.handler.AddItemHandler;
import org.buraindo.sd.reactive.handler.RegisterHandler;
import org.buraindo.sd.reactive.handler.ViewItemsHandler;
import org.buraindo.sd.reactive.model.Item;
import org.buraindo.sd.reactive.model.User;
import org.buraindo.sd.reactive.server.RxServer;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        var database = RxDatabase.newRxDatabase();
        var items = database.getCollection("items", Item.class);
        var users = database.getCollection("users", User.class);
        var handlers = new HashMap<String, RequestHandler<ByteBuf, ByteBuf>>();
        handlers.put("/add", new AddItemHandler(items));
        handlers.put("/view", new ViewItemsHandler(users, items));
        handlers.put("/register", new RegisterHandler(users));
        RxServer.newRxServer(handlers).awaitShutdown();
    }

}
