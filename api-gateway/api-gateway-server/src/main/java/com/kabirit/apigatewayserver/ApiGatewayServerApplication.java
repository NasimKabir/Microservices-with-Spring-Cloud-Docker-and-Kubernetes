package com.kabirit.apigatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class ApiGatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayServerApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/easyBank/accounts-service/**")
                        .filters(f -> f.rewritePath("/easyBank/accounts-service/?(?<remaining>.*)", "/${remaining}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS-SERVICE"))

                .route(p -> p
                        .path("/easyBank/loans-service/**")
                        .filters(f -> f.rewritePath("/easyBank/loans-service/?(?<remaining>.*)", "/${remaining}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://LOANS-SERVICE"))

                .route(p -> p
                        .path("/easyBank/cards-service/**")
                        .filters(f -> f.rewritePath("/easyBank/cards-service/?(?<remaining>.*)", "/${remaining}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS-SERVICE"))
                .build();
    }

}
