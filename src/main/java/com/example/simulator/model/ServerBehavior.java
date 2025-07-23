package com.example.simulator.model;

import java.util.Map;

public class ServerBehavior {
    public Matching matching;
    public Response response;
    public Redirect redirect;

    public static class Matching {
        public String method;
        public String path;
        public String contains;
    }

    public static class Response {
        public int status;
        public Map<String, String> headers;
        public String body;
    }

    public static class Redirect {
        public boolean enabled;
        public String url;
        public int status = 302;
    }
}
