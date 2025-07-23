# Contributing & Usage Guide

## Build the Project

```bash
./mvnw clean package
```

This will generate a single executable JAR in `target/csc-simulator.jar`.

## Run with Docker

Build Docker image:

```bash
docker build -t csc-simulator:latest .
```

Run locally:

```bash
docker run --rm -p 8080:8080 -v $(pwd)/run/configuration:/run/configuration csc-simulator:latest
```

Mount `/run/configuration` with your client/server config files.
