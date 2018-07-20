package com.jiangj.webfluxlambda.client.sse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class SSEClient {

    public static void main(String[] args) {

        WebClient client = WebClient.create("http://localhost:8080/sse/randomNumbers");

        client.get()
                .uri("")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
                })))
                .filter(sse -> Objects.nonNull(sse.data()))
                .map(ServerSentEvent::data)
                .buffer(10)
                .doOnNext(System.out::println)
                .blockFirst();
    }
}
