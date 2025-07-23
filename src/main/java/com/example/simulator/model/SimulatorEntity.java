package com.example.simulator.model;

public class SimulatorEntity {
    public String id;
    public boolean enabled;
    public String type; // client or server
    public Integer autoDisableAfterSec;
    public ClientBehavior clientBehavior;
    public ServerBehavior serverBehavior;
}
