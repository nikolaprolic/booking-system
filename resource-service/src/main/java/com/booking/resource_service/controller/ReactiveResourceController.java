package com.booking.resource_service.controller;

import com.booking.resource_service.model.Resource;
import com.booking.resource_service.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/resources/reactive")
@RequiredArgsConstructor
public class ReactiveResourceController {

    private final ResourceService resourceService;

    @GetMapping
    public Flux<Resource> getAllResourcesReactive() {
        return Flux.fromIterable(resourceService.getAllResources());
    }

    @GetMapping("/{id}")
    public Mono<Resource> getResourceByIdReactive(@PathVariable Long id) {
        return Mono.just(resourceService.getResourceById(id));
    }

    @GetMapping("/available")
    public Flux<Resource> getAvailableResourcesReactive() {
        return Flux.fromIterable(resourceService.getAvailableResources());
    }
}