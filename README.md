# TechBuzz
TechBuzz is a place to share the interesting tech news such as blog posts, videos, release news etc.

## Features
* Login with username/password
* Login with GitHub
* Register with username/password
* Email verification
* View Posts by Category with pagination
* Create Post
* Update Post
* Delete Post
* Up/Down Vote a Post

## Tech Stack
* Java, SpringBoot
* Postgres, JPA, Spring Data Jpa, FlywayDb
* Spring Security
* Thymeleaf, Bootstrap, jQuery
* JUnit 5, RestAssured, Testcontainers

## Local development

```shell
$ ./run.sh start_infra
$ ./mvnw spring-boot:run
```

* App: http://localhost:8080/
* MailHog: http://localhost:8025/
