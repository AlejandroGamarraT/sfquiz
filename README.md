# SAP SF Quiz

A mobile-focused Progressive Web App (PWA) for testing **SAP SuccessFactors Employee Central** knowledge, built with Spring Boot and Angular.

## Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.2 · Spring Data JPA · Hibernate |
| Frontend | Angular 17 · PWA |
| Database | MySQL 8 |

## Features

- 224 curated questions across 8 Employee Central topics
- Random question selection per topic and quantity
- Instant answer feedback with explanations
- Session scoring and full review after each quiz
- Installable as a PWA on iOS and Android

## Project Structure

```
sfquiz/
├── backend/      # Spring Boot REST API
└── frontend/     # Angular PWA
```

## Prerequisites

- Java 17+
- Node.js 18+ and npm
- MySQL 8

## Getting Started

### 1. Database

Create the database and run the app — Hibernate auto-creates the schema on first boot:

```sql
CREATE DATABASE sapsf_quiz;
```

### 2. Backend

```bash
cd backend
./mvnw spring-boot:run
```

Runs on `http://localhost:8080`.

### 3. Frontend

```bash
cd frontend
npm install
ng serve
```

Runs on `http://localhost:4200`.

## Configuration

The backend is configured via environment variables. Defaults work out of the box for local development:

| Variable | Default | Description |
|---|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/sapsf_quiz` | MySQL connection URL |
| `DB_USERNAME` | `root` | Database username |
| `DB_PASSWORD` | _(empty)_ | Database password |
| `PORT` | `8080` | Server port |
| `ALLOWED_ORIGINS` | `http://localhost:4200` | Comma-separated allowed CORS origins |

## API Endpoints

All responses follow the shape `{ data, error }`.

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/topics` | List all topics with question counts |
| `GET` | `/api/quiz?topic=&limit=` | Get random questions for a topic |
| `POST` | `/api/quiz/session` | Save a completed quiz session |

## Topics

| Topic | Questions |
|---|---|
| Self Service and Workflows | 48 |
| Meta Data Framework (MDF) | 41 |
| Set Up Employees | 36 |
| Storing Corporate Data | 34 |
| Additional Administrative | 19 |
| Introduction | 18 |
| Manage User Permissions | 16 |
| Employee Record Management | 12 |
