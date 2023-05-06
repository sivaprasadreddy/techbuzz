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

## Architecture Decision Records (ADRs)
* [Technology selection for UI](adr/ui-tech-selection.md)
* [Tech selection for database persistence](adr/persistence-library-selection.md)

## HOW TO?

### Run Unit / Integration Tests

```shell
$ ./mvnw verify
```

### Format code

```shell
$ ./mvnw spotless:apply // to formatting code automatically
$ ./mvnw spotless:check // to verify the code formatting
```

### Run application locally

If you want to start the required services (database, mail server) using docker-compose 
and run the application locally:

```shell
$ ./run.sh start_infra
$ ./mvnw spring-boot:run
```

Instead, you can simply run `./mvnw spring-boot:test-run` which will automatically spin up the required services 
as docker containers using Testcontainers and starts the application.

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

## How to contribute?
* If you find this project interesting, fork/clone it, run the application and provide feedback.
* If you find any bugs or have suggestions for improvement, then please file an issue.
* Of course, Pull Requests are most welcome.
