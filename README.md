# DUP_JOINT

Simple WebSocket chat built with Spring Boot and Kotlin.

## Getting started

### Requirements
- Java 21+
- Gradle 8+ (or the Gradle wrapper if you generate it locally)

### Run locally
```bash
gradle bootRun
```
Then open http://localhost:8080 in multiple tabs to chat in real time.

### Build
```bash
gradle build
```

## How it works
- `WebSocketConfig` registers a `/chat` endpoint backed by `ChatWebSocketHandler`.
- The handler broadcasts JSON messages from one client to all connected peers.
- Static assets in `src/main/resources/static` provide a lightweight UI that connects with the native WebSocket API.
