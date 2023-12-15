# Reproduce example for [Spring Kafka Admin Issue](https://github.com/spring-projects/spring-kafka/issues/2780)

### Steps to reproduce

* Start Kafka. You can use `docker compose up -d`
* Start application via IDE or command line `./gradlew bootRun`
* Send publish request `curl "http://localhost:8080/test/publish?n=1000"`
* Check logs for several Kafka admin initializations `o.a.k.clients.admin.AdminClientConfig    : AdminClientConfig values:`

  With quite a big "n" - the number of records publishing just gets stuck.

### Notice

Not reproducible w/o "org.springframework.boot:spring-boot-starter-actuator"

