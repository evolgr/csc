package com.example.simulator.scheduler;

import com.example.simulator.SimulatorRegistry;
import com.example.simulator.model.ClientBehavior;
import com.example.simulator.model.SimulatorEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ClientScheduler {

    private final SimulatorRegistry registry;
    private final RestTemplate restTemplate = new RestTemplate();

    public ClientScheduler(SimulatorRegistry registry) {
        this.registry = registry;
    }

    @Scheduled(fixedDelay = 5000)
    public void tick() {
        registry.cleanupExpired();
        for (SimulatorEntity entity : registry.getAll()) {
            if (!entity.enabled || !"client".equals(entity.type)) continue;
            if (!"interval".equalsIgnoreCase(entity.clientBehavior.mode)) continue;
            send(entity);
        }
    }

    @PostConstruct
    public void triggerOneShots() {
        for (SimulatorEntity entity : registry.getAll()) {
            if (entity.enabled && "client".equals(entity.type) &&
                "oneshot".equalsIgnoreCase(entity.clientBehavior.mode)) {
                send(entity);
                entity.enabled = false;
            }
        }
    }

    private void send(SimulatorEntity entity) {
        try {
            ClientBehavior b = entity.clientBehavior;
            HttpHeaders headers = new HttpHeaders();
            if (b.headers != null) b.headers.forEach(headers::add);
            HttpEntity<String> req = new HttpEntity<>(b.body, headers);
            restTemplate.exchange(b.url, HttpMethod.valueOf(b.method), req, String.class);
        } catch (Exception e) {
            System.err.println("Error for " + entity.id + ": " + e.getMessage());
        }
    }
}
