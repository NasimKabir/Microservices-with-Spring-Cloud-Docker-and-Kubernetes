package com.kabirit.apigatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author nasimkabir
 * @date 6/8/25
 */
@RestController
public class FallBackController {
    @GetMapping("/contact-support")
    public Mono<String> fallbackMethod() {
        return Mono.just("An error occurred while processing the request. Please try again later");
    }
}
