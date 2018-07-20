package com.jiangj.webfluxlambda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebfluxLambdaApplicationTests {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void test(){

    }


}
