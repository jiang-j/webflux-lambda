package com.jiangj.webfluxlambda;

import com.jiangj.webfluxlambda.client.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * @author Jiang Jian
 * @since 2018/7/10
 */
public class UserControllerTest {

    WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080/user").build();

    @Test
    public void testCreateUser(){
        User user = new User();
        user.setId("4");
        user.setEmail("1234@qq.com");
        user.setName("jiangj");

        client.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user),User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo("jiangj1");
    }

}
