package com.example.simulator.controller;

import com.example.simulator.SimulatorRegistry;
import com.example.simulator.model.SimulatorEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/simulator")
public class SimulatorConfigController {

    private final SimulatorRegistry registry;

    public SimulatorConfigController(SimulatorRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable String id) {
        SimulatorEntity entity = registry.get(id);
        if (entity == null) return ResponseEntity.notFound().build();
        entity.enabled = true;
        return ResponseEntity.ok("Enabled " + id);
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable String id) {
        SimulatorEntity entity = registry.get(id);
        if (entity == null) return ResponseEntity.notFound().build();
        entity.enabled = false;
        return ResponseEntity.ok("Disabled " + id);
    }

    @GetMapping("/configs")
    public Collection<SimulatorEntity> getAll() {
        return registry.getAll();
    }
}
