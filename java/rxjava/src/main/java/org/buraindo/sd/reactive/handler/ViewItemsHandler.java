package org.buraindo.sd.reactive.handler;

import com.mongodb.rx.client.MongoCollection;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.buraindo.sd.reactive.model.Item;
import org.buraindo.sd.reactive.model.User;
import org.buraindo.sd.reactive.util.CurrencyConverter;
import org.buraindo.sd.reactive.util.ParameterUtils;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;

public class ViewItemsHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final MongoCollection<User> users;
    private final MongoCollection<Item> items;

    public ViewItemsHandler(MongoCollection<User> users, MongoCollection<Item> items) {
        this.users = users;
        this.items = items;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        var params = req.getQueryParameters();
        var login = ParameterUtils.getParameter(params, "login", "ViewItems: missing 'login' parameter");
        var result = users
                .find(eq("login", login))
                .toObservable()
                .map(User::getCurrency)
                .flatMap(currency ->
                        items
                                .find()
                                .toObservable()
                                .map(i -> new Item(i.getName(), CurrencyConverter.convert(currency, i.getPrice()))
                                        .toString(currency))
                );
        return resp.writeString(result);
    }

}
