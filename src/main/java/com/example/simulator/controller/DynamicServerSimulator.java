package com.example.simulator.controller;

import com.example.simulator.SimulatorRegistry;
import com.example.simulator.model.ServerBehavior;
import com.example.simulator.model.SimulatorEntity;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock-server")
public class DynamicServerSimulator {

    private final SimulatorRegistry registry;

    public DynamicServerSimulator(SimulatorRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> handle(@PathVariable String id, @RequestBody String body) {
        SimulatorEntity entity = registry.get(id);
        if (entity == null || !entity.enabled || !"server".equalsIgnoreCase(entity.type)) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Server not available");
        }

        ServerBehavior b = entity.serverBehavior;
        if (b.redirect != null && b.redirect.enabled) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(java.net.URI.create(b.redirect.url));
            return new ResponseEntity<>(headers, HttpStatus.valueOf(b.redirect.status));
        }

        HttpHeaders headers = new HttpHeaders();
        if (b.response.headers != null) {
            b.response.headers.forEach(headers::add);
        }

        return ResponseEntity.status(b.response.status).headers(headers).body(b.response.body);
    }
}
