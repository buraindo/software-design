package org.buraindo.sd.reactive.handler;

import com.mongodb.rx.client.MongoCollection;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.buraindo.sd.reactive.model.Item;
import org.buraindo.sd.reactive.util.ParameterUtils;
import rx.Observable;

public class AddItemHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final MongoCollection<Item> items;

    public AddItemHandler(MongoCollection<Item> items) {
        this.items = items;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        var params = req.getQueryParameters();
        var name = ParameterUtils.getParameter(params, "name", "AddItem: missing 'name' parameter");
        var price = Double.parseDouble(ParameterUtils.getParameter(params, "price", "AddItem: missing 'currency' parameter"));
        var item = new Item(name, price);
        var result = items.insertOne(item).map(__ -> item.toString());
        return resp.writeString(result);
    }

}
