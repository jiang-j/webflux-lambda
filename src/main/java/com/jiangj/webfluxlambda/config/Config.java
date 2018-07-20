package com.jiangj.webfluxlambda.config;

import com.jiangj.webfluxlambda.handler.CalculatorHandler;
import com.jiangj.webfluxlambda.handler.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Jiang Jian
 * @since 2018/7/9
 */
@Configuration
public class Config {

    @Autowired
    private TimeHandler timeHandler;

    @Bean
    @Autowired
    public RouterFunction<ServerResponse>routerFunction(final CalculatorHandler calculatorHandler) {
        return route(POST("/calculator"),
                request -> request.queryParam("operator")
                        .map(operator -> Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))
                                        .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))
                                        .switchIfEmpty(ServerResponse.badRequest().build())
                                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()))
                        .orElse(ServerResponse.badRequest().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> timeRouter(){
        return route(GET("/time"),req -> timeHandler.getTime(req))
                .andRoute(GET("/date"),timeHandler::getDate);
    }


}
