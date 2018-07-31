package com.jiangj.webfluxlambda.client.sse;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jiangj.webfluxlambda.ExecutorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import sun.nio.ch.ThreadPool;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class SSEClient2 {

    public static void main(String[] args) {
        SSEClient2 client2 = new SSEClient2();
        client2.test();
    }


    public void test() {

        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(20, 100,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), nameThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0 ; i < 1000 ; i++){
            pool.execute(() -> {
                WebClient client = WebClient.create("http://101.37.119.210/issp/sse/order?uniCode=66");
                client.get()
                        .uri("")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .flatMapMany(clientResponse -> clientResponse.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
                        })))
                        .filter(sse -> Objects.nonNull(sse.data()))
                        .map(ServerSentEvent::data)
                        .doOnNext(System.out::println)
                        .blockFirst();

            });
        }
    }
}
