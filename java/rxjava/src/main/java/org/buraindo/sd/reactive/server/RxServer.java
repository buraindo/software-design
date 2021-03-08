package org.buraindo.sd.reactive.server;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.RequestHandler;

import java.util.Map;

public class RxServer {

    public static HttpServer<ByteBuf, ByteBuf> newRxServer(Map<String, RequestHandler<ByteBuf, ByteBuf>> handlers) {
        return HttpServer
                .newServer(8080)
                .start((req, resp) -> handlers.get(req.getUri().split("\\?")[0]).handle(req, resp));
    }

}
