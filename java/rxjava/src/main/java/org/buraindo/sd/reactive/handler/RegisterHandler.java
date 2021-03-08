package org.buraindo.sd.reactive.handler;

import com.mongodb.rx.client.MongoCollection;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import org.buraindo.sd.reactive.model.User;
import org.buraindo.sd.reactive.util.ParameterUtils;
import rx.Observable;

public class RegisterHandler implements RequestHandler<ByteBuf, ByteBuf> {

    private final MongoCollection<User> users;

    public RegisterHandler(MongoCollection<User> users) {
        this.users = users;
    }

    @Override
    public Observable<Void> handle(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        var params = req.getQueryParameters();
        var login = ParameterUtils.getParameter(params, "login", "Register: missing 'login' parameter");
        var currency = ParameterUtils.getParameter(params, "currency", "Register: missing 'currency' parameter");
        var user = new User(login, currency);
        var result = users.insertOne(user).map(__ -> user.toString());
        return resp.writeString(result);
    }

}
