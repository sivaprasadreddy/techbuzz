# TechBuzz
TechBuzz is a place to share the interesting tech news such as blog posts, videos, release news etc.

## Features
* Login with username/password
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
* JUnit 5, Testcontainers

## HOW TO?

### Run Unit / Integration Tests

```shell
$ ./mvnw verify
```
### Run Gatling Tests

```shell
$ ./mvnw -pl gatling-tests gatling:test 
```

### Run application locally

```shell
$ ./run.sh start_infra
$ ./mvnw spring-boot:run
```

## Run application using docker-compose

```shell
$ ./run.sh start_app
```

The application should be accessible at http://localhost:8080/

## Monitoring

* Start Prometheus, Grafana, Loki using `$ ./run.sh start_grafana`
* Few Dashboards are already pre-configured to show SpringBoot application Metrics

### Prometheus
* Navigate to http://localhost:9090/ and go to Status -> Targets

### Loki - Log management
* Navigate to http://localhost:3000/ and login with `admin/admin`
* Click on Explore -> Select Loki
* In Label filters, select `label=container` and `value=techbuzz`
* Click on Run Query in the top right corner and select the auto refresh interval

### Tempo - Tracing
In order to enable tracing, first update `docker/.env` file and set `TRACING_ENABLED=true`.
Then restart the application using `./run.sh restart_app`

* Navigate to http://localhost:3000/ and login with `admin/admin`
* Click on Explore -> Select Tempo
* Select the Query type: Search, Service Name: techbuzz
* You should see a list of Traces

## Service URLs
* App: http://localhost:8080/
* MailHog: http://localhost:8025/
* Prometheus: http://localhost:9090
* Grafana: http://localhost:3000
