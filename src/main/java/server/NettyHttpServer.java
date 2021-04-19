package server;


import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;


import static server.Command.process;

public class NettyHttpServer {
    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String action = req.getDecodedPath().substring(1);
                    rx.Observable<String> responseMessage;
                    try {
                        responseMessage = process(action, req.getQueryParameters());
                    } catch (RuntimeException e) {
                        responseMessage = Observable.just(e.getMessage());
                        resp.setStatus(HttpResponseStatus.BAD_REQUEST);
                    }

                    return resp.writeString(responseMessage);
                })
                .awaitShutdown();
    }
}
