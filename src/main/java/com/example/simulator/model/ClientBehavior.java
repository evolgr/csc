package com.example.simulator.model;

import java.util.Map;

public class ClientBehavior {
    public String mode; // interval or oneshot
    public long intervalMs;
    public String method;
    public String url;
    public Map<String, String> headers;
    public String body;
}
