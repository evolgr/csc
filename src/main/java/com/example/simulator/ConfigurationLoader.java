package com.example.simulator;

import com.example.simulator.model.SimulatorEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.util.Objects;

@Component
public class ConfigurationLoader {

    private final SimulatorRegistry registry;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConfigurationLoader(SimulatorRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    public void loadAllConfigs() {
        loadFromDir("/run/configuration/client");
        loadFromDir("/run/configuration/server");
    }

    private void loadFromDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) return;
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) return;

        for (File file : files) {
            try {
                SimulatorEntity entity = mapper.readValue(file, SimulatorEntity.class);
                registry.register(entity);
            } catch (Exception e) {
                System.err.println("Failed to load: " + file.getName());
            }
        }
    }
}
