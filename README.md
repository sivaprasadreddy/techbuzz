# TechBuzz
TechBuzz is a place to share the interesting tech news such as blog posts, videos, release news etc.

[![Build](https://github.com/sivaprasadreddy/techbuzz/actions/workflows/maven.yml/badge.svg)](https://github.com/sivaprasadreddy/techbuzz/actions/workflows/maven.yml)

## Features
* Login with username/password
* Register with username/password
* Account activation by email verification
* View posts by category with pagination
* Create post
* Update post
* Delete post
* Up/Down vote a post

## Tech Stack
* Java, SpringBoot
* Postgres, jOOQ, FlywayDb
* Spring Security
* Thymeleaf, Bootstrap, jQuery
* JUnit 5, Testcontainers

## HOW TO?

### Run Unit / Integration Tests

```shell
$ ./mvnw verify
```

### Run application locally

```shell
$ ./run.sh start_infra
$ ./mvnw spring-boot:run
```

## Run application using docker-compose

```shell
$ ./run.sh start
```
* App: http://localhost:8080/
* MailHog: http://localhost:8025/

### Run Gatling Tests

```shell
$ ./mvnw -pl gatling-tests gatling:test 
```
