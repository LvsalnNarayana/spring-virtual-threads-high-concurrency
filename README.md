
# Spring-Virtual-Threads-High-Concurrency

## Overview

This project is a **multi-microservice demonstration** of **Java Virtual Threads** (Project Loom - JEP 444, Java 21+) in **Spring Boot 3.x**, showcasing how virtual threads enable handling **tens of thousands of concurrent requests** with **blocking-style code** while maintaining low memory footprint and high throughput.

It compares traditional platform threads vs virtual threads, proving that virtual threads make blocking I/O scalable again — perfect for modern high-traffic applications.

## Real-World Scenario (Simulated)

In high-traffic professional networks like **LinkedIn**:
- Millions of users load feeds, profiles, and notifications simultaneously.
- Each request may involve blocking operations: database calls, HTTP to downstream services, file I/O.
- Traditional thread-per-request model limits concurrency to ~number of CPU cores × thread stack size (often < 10k).
- Virtual threads allow **10k–100k+ concurrent requests** using carrier threads efficiently.

We simulate a user profile/feed system with simulated blocking delays (DB/external calls) and demonstrate handling 10,000+ concurrent requests using virtual threads vs traditional threads.

## Microservices Involved

| Service                   | Responsibility                                                                 | Port  |
|---------------------------|--------------------------------------------------------------------------------|-------|
| **eureka-server**         | Service discovery (Netflix Eureka)                                             | 8761  |
| **profile-service**       | Serves user profiles with simulated blocking delay                             | 8081  |
| **feed-service**          | Generates user feed by calling profile-service (orchestration)                 | 8082  |
| **notification-service**  | Simulates push notifications with blocking processing                          | 8083  |

All services configurable to run on **platform threads** or **virtual threads**.

## Tech Stack

- Spring Boot 3.2+ (requires Java 21+)
- Java Virtual Threads (`Thread.startVirtualThread()` or `Executors.newVirtualThreadPerTaskExecutor()`)
- Spring Web (Tomcat with virtual thread support)
- Spring Cloud Netflix Eureka
- WebClient (blocking mode for demo)
- Micrometer + Actuator (thread metrics)
- Lombok
- Maven (multi-module)
- Docker & Docker Compose
- JMeter/k6 scripts for load testing

## Docker Containers

```yaml
services:
  eureka-server:
    build: ./eureka-server
    ports:
      - "8761:8761"

  profile-service:
    build: ./profile-service
    depends_on:
      - eureka-server
    ports:
      - "8081:8081"
    environment:
      - THREAD_MODE=virtual  # or 'platform' for comparison

  feed-service:
    build: ./feed-service
    depends_on:
      - eureka-server
    ports:
      - "8082:8082"
    environment:
      - THREAD_MODE=virtual

  notification-service:
    build: ./notification-service
    depends_on:
      - eureka-server
    ports:
      - "8083:8083"
    environment:
      - THREAD_MODE=virtual
```

Run with: `docker-compose up --build`

## Virtual Threads Implementation

| Feature                        | Implementation Details                                                  |
|--------------------------------|-------------------------------------------------------------------------|
| **Virtual Thread Executor**    | `Executors.newVirtualThreadPerTaskExecutor()` in `TaskExecutor` bean   |
| **Tomcat Virtual Threads**     | `server.tomcat.threads.virtual.enabled=true` (Spring Boot 3.2+)        |
| **Blocking Calls**             | Simulated delays (`Thread.sleep`) and blocking WebClient calls         |
| **Configuration Toggle**       | `application.yml` property `thread.mode: virtual|platform`             |
| **Metrics**                    | `jvm.threads.live`, `tomcat.threads.current` via Actuator             |

## Key Features

- Toggle between platform and virtual threads via config
- Simulated blocking operations (DB/external calls)
- High-concurrency load testing scripts (10k+ requests)
- Metrics comparison: memory, throughput, latency
- Clean, readable blocking-style code (no callbacks/Reactive)
- Graceful scaling to 10k+ concurrent connections
- Demonstration of structured concurrency (optional)

## Expected Endpoints

### Feed Service (`http://localhost:8082`)

| Method | Endpoint                  | Description                                      |
|--------|---------------------------|--------------------------------------------------|
| GET    | `/api/feed/{userId}`      | Load user feed → calls profile-service (blocking) |
| GET    | `/api/feed/concurrent`    | Load test endpoint (handles many parallel calls) |

### Profile Service (`http://localhost:8081`)

| Method | Endpoint                        | Description                                      |
|--------|---------------------------------|--------------------------------------------------|
| GET    | `/api/profiles/{id}`            | Get profile with 100-500ms simulated delay       |

### Notification Service (`http://localhost:8083`)

| Method | Endpoint                        | Description                                      |
|--------|---------------------------------|--------------------------------------------------|
| POST   | `/api/notifications`            | Process notification with blocking work          |

### Actuator
- `/actuator/metrics/jvm.threads.live`
- `/actuator/metrics/tomcat.threads.*`

## Architecture Overview

```
Clients (10k+ concurrent)
   ↓
Feed Service → blocking WebClient calls
   ↓
Profile Service → Thread.sleep(200ms)
   ↓
Virtual Threads (mounted on few carrier threads)
   ↓
High throughput, low memory
```

**Concurrency Flow**:
1. Request → Tomcat accepts on virtual thread
2. Blocking call to profile-service → virtual thread parks (no OS thread blocked)
3. Thousands of requests → only ~CPU cores carrier threads used
4. Response when all blocking ops complete

## How to Run

1. Clone repository
2. Ensure Java 21+ installed
3. Start Docker: `docker-compose up --build`
4. Access Eureka: `http://localhost:8761`
5. Load test:
   - Use provided JMeter/k6 script
   - 10,000 concurrent users → virtual threads: success, low memory
   - Switch to `platform` → OOM or thread exhaustion

## Testing High Concurrency

1. Run with `THREAD_MODE=virtual` → 10k concurrent requests succeed
2. Monitor threads: ~200 live threads (carrier pool)
3. Memory stable (~500MB)
4. Switch to `platform` → fails at ~2k-5k threads (1MB stack each)
5. Compare latency/throughput → virtual threads win under high load

## Skills Demonstrated

- Java 21+ Virtual Threads (Project Loom)
- Configuring Spring Boot for virtual threads
- Simplifying concurrency with blocking code
- High-concurrency application design
- Thread pool vs virtual thread comparison
- Performance benchmarking under load
- Modern Java scalability patterns

## Future Extensions

- Structured concurrency with Scoped Values
- Virtual threads + Reactive (hybrid)
- Integration with blocking JDBC
- Virtual threads in Kubernetes (resource limits)
- Comparison with WebFlux reactive stack
- Real database (PostgreSQL blocking driver)

