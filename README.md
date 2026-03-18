# Redis Task Queue Lab

A simple Redis-based task queue project built with Spring Boot.

## Features

- Redis list-based queue (LPUSH / BRPOP)
- Producer & Consumer model
- Task dispatcher
- Multiple task handlers

## Tech Stack

- Java 17
- Spring Boot
- Redis
- Docker

## How to Run

1. Start Redis with Docker:

```bash
docker run -d -p 6379:6379 redis:alpine
# Redis Task Queue Lab

A simple Redis-based task queue project built with Spring Boot.

## Features

- Redis list-based queue (LPUSH / BRPOP)
- Producer & Consumer model
- Task dispatcher
- Multiple task handlers

## Tech Stack

- Java 17
- Spring Boot
- Redis
- Docker

## How to Run

1. Start Redis with Docker:

```bash
docker run -d -p 6379:6379 redis:alpine

2.Start the application
3.Test API:
http://localhost:9090/job/send?msg=PRINT:hello
http://localhost:9090/job/send?msg=SAVE:world