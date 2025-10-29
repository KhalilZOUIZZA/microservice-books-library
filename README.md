# Microservice Books Library

A modular microservices-based library management system, built to demonstrate a cloud-ready, resilient, containerised architecture. This system comprises multiple Java Spring Boot microservices (books, reservations, users), a gateway, service discovery, a frontend, and CI/CD pipelines.

## Table of Contents

1. [Overview](#overview)
2. [Key Features](#key-features)
3. [Architecture](#architecture)
4. [Tech Stack](#tech-stack)
5. [Getting Started](#getting-started)
6. [Usage](#usage)
7. [Deployment](#deployment)
8. [Environment Configuration](#environment-configuration)

## Overview

This project implements a **Books Library Management System** using microservices architecture. It enables operations such as:

* Managing books (add/update/delete/list)
* Managing users and authentication/authorization
* Handling reservations (users reserving books)
* Real-time chat notifications (optional)
* Service discovery, API Gateway, and container orchestration for local development and cloud readiness

The aim is to build a production-ready application with high resilience, monitoring, DevSecOps practices, and cloud deployment capability.

## Key Features

* Independent microservices for each bounded context (Books, Reservations, Users)
* API Gateway with routing and aggregated endpoints
* Service Discovery via Eureka (or equivalent)
* Containerised with Docker and orchestrated via `docker-compose.yml` for local dev
* CI/CD pipeline integrated (GitLab CI/CD) with manual deploy stage
* Use of environment-specific configurations (dev, staging, prod)
* Real-time chat server included (optional) to demonstrate WebSocket / push notifications
* Infrastructure as Code ready (Terraform / Ansible) for future cloud deployment

## Architecture

```
[Frontend SPA] <-> [API Gateway] <-> [Books MS]
                               |- [Reservations MS]
                               |- [Users / Auth MS]
                
           [Eureka Discovery]
                
           [Database(s)]
```

Each service has its own module, runs independently, communicates via REST (or async events), and is discoverable via the gateway.

Key modules/folders in the repo:

* `backend-ms1`, `backend-ms2`, `backend-ms3` → Each representing a microservice.
* `gateway` → API Gateway service.
* `eureka-discovery` → Service discovery server.
* `frontend` → Front-end application (TypeScript/HTML).
* `docker-compose.yml` → Local orchestration of all services.
* `pipelines` → CI/CD definitions (GitLab).
* `deploy`, `data` → Deployment scripts, DB seed/data.

## Tech Stack

* Java 17, Spring Boot (microservices)
* Maven 3.9.8 for build and packaging
* Spring Cloud (Gateway, Eureka, Resilience4j)
* Front-end: TypeScript + framework (e.g., React/Angular)
* Containers: Docker, Docker Compose for local dev
* CI/CD: GitLab CI/CD pipelines
* Infrastructure: (future) Terraform & Ansible for cloud deployment
* Monitoring/Logging: (planned) OpenTelemetry, ELK/Prometheus-Grafana
* DB: Specify (e.g., PostgreSQL/MongoDB) per service

## Getting Started

### Prerequisites

* Docker & Docker Compose installed
* Java 17+ and Maven 3.9.8 (if building individually)
* GitLab runner (for CI/CD) if using pipelines
* Optional: Node.js + npm/yarn for frontend local dev

### Local Setup (Docker Compose)

```bash
git clone https://github.com/KhalilZOUIZZA/microservice-books-library.git
cd microservice-books-library
docker-compose up --build
```

This will start all services (discovery, gateway, backend services, database(s), frontend) on preconfigured ports.

### Running the Services Manually (Alternative)

```bash
# Example for backend-ms1
cd backend-ms1
mvn clean package
java -jar target/backend-ms1-0.0.1-SNAPSHOT.jar

# Repeat for other services
```

### Stop/Remove Services

```bash
docker-compose down
```

## Usage

### Accessing APIs

* Discovery server: `http://localhost:8761`
* Gateway: `http://localhost:8080`
* Sample endpoints:

  * `GET /api/books` – list books
  * `POST /api/reservations` – create a reservation
* Refer to each service’s README.md or Swagger UI for detailed endpoints

### Frontend

* Access frontend at `http://localhost:3000`
* Default credentials:

  * Username: `admin`
  * Password: `123`

> Change these credentials for any non-development environment

### CI/CD Pipeline

* The `pipelines/` folder contains GitLab CI definitions:

  * `build` – compiles and tests each microservice
  * `verify` – code quality and SonarQube analysis
  * `package` – builds container images
  * `deploy` (manual) – optional deploy step

## Deployment

For production or cloud deployment:

* Move from Docker Compose to orchestration platform (e.g., Kubernetes/EKS)
* Use Terraform to provision cloud resources (VPC, clusters, DB, etc.)
* Use Ansible (or other config management) for environment setup
* Implement service mesh (mTLS), blue-green/canary deployment, monitoring, and alerting
* Secure all endpoints and rotate credentials

## Environment Configuration

All microservices use environment variables or `application-{profile}.properties`.

Example:

```
SPRING_PROFILES_ACTIVE=dev
DATABASE_URL=jdbc:postgresql://db:5432/books
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka
JWT_SECRET=YourSecretKey
```

