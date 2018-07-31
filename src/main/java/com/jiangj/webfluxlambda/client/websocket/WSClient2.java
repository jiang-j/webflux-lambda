package com.jiangj.webfluxlambda.client.websocket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.*;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class WSClient2 {

    public void test() {

        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), nameThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            pool.execute(()->{
                WebSocketClient client = new ReactorNettyWebSocketClient();
                client.execute(URI.create("ws://localhost:8085/websocket"), session ->
                        session.send(Flux.just(session.textMessage("Hello")))
                                .thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
                                .doOnNext(System.out::println)
                                .then())
                        .block(Duration.ofMillis(5000));

            });
        }

    }


        public static void main(String[] args) {
            WSClient2 client2 = new WSClient2();
            client2.test();
    }
}
