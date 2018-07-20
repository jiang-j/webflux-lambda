package com.jiangj.webfluxlambda.client.http;

import com.jiangj.webfluxlambda.client.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class RESTClient {

    public static void main(String[] args) {
         User user = new User();
         user.setId("3");
         user.setEmail("11111@qq.com");
         user.setName("jiang jian");

        WebClient webClient = WebClient.create("http://127.0.0.1:8080/user");
        Mono<User> createdUser = webClient.post()
                .uri("").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user),User.class)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(User.class));
        System.out.println(createdUser.block());
    }
}
