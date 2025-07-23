# Client Server Configuration dynamic Simulator

A dynamic Spring Boot-based client-server simulator for mocking external integrations in a flexible and programmable way.

## Features

- Dynamic loading of client/server configurations from JSON files
- Support for interval or one-shot client requests
- Custom server responses with redirect support
- Activation timers with auto-disable functionality
- REST APIs to enable/disable or reload configurations
- Ready for Docker and Kubernetes deployment

## Directory Structure

```
run/
└── configuration/
    ├── client/
    │   └── client-1.json
    └── server/
        └── server-1.json
```

## How to Run

```bash
./mvnw clean package
java -jar target/csc-simulator.jar
```

Or with Docker (see CONTRIBUTING.md).
