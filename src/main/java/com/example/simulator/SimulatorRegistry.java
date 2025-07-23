package com.example.simulator;

import com.example.simulator.model.SimulatorEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SimulatorRegistry {

    private final Map<String, SimulatorEntity> entities = new ConcurrentHashMap<>();
    private final Map<String, Instant> expiryTimes = new ConcurrentHashMap<>();

    public void register(SimulatorEntity entity) {
        entities.put(entity.id, entity);
        if (entity.autoDisableAfterSec != null) {
            expiryTimes.put(entity.id, Instant.now().plusSeconds(entity.autoDisableAfterSec));
        }
    }

    public void unregister(String id) {
        entities.remove(id);
        expiryTimes.remove(id);
    }

    public SimulatorEntity get(String id) {
        return entities.get(id);
    }

    public Collection<SimulatorEntity> getAll() {
        return entities.values();
    }

    public void cleanupExpired() {
        Instant now = Instant.now();
        expiryTimes.forEach((id, expiry) -> {
            if (now.isAfter(expiry)) {
                SimulatorEntity e = entities.get(id);
                if (e != null) e.enabled = false;
            }
        });
    }
}
