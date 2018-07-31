package com.jiangj.webfluxlambda.client.http;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jiangj.webfluxlambda.client.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.*;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class RESTClient2 {

    public static void main(String[] args) {

        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), nameThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Integer j = Integer.valueOf(i);
            pool.execute(()->{
                WebClient webClient = WebClient.create("http://127.0.0.1:8080/queryById?id="+j);
                Mono<String> result = webClient.post()
                        .uri("").accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
                System.out.println(result.block());
            });

        }

    }
}
