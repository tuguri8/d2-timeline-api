## D2-Timeline-api

- D2 Campus Mini fest 7th 타임라인 만들기 프로젝트 백엔드
- 라이선스 : MIT
- 프론트엔드 저장소 : <https://github.com/tuguri8/d2-timeline-ui>

## 개발환경

- React
- Spring Boot
- Apache Kafka
- H2 + Apache Cassandra

## Usage

**Zookeeper 실행**

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

**Kafka 실행**

```
bin/kafka-server-start.sh config/server.properties
```

**Cassandra 실행**

```
cassandra
```

## Posting Architecture

![POSTING_ARCHITECTURE](https://i.imgur.com/AjF1IOa.png)

## Used Open Source

- [Spring Boot](https://github.com/spring-projects/spring-boot)
- [Apache Kafka](https://kafka.apache.org/)

- [Apache Cassandra](http://cassandra.apache.org/)
- [H2](http://www.h2database.com/html/license.html)

- [Spring Security](https://spring.io/projects/spring-security)
- [jjwt](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)
