FROM openjdk:11-jdk-slim as builder
WORKDIR /usr/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle build.gradle
COPY gradle.properties gradle.properties
COPY settings.gradle settings.gradle
COPY iot-producer/src iot-producer/src
COPY iot-producer/build.gradle iot-producer/build.gradle

RUN sed -i -e 's/\r$//' gradlew
RUN ./gradlew build -x test

ARG JAR_FILE=iot-producer/build/libs/iot-producer.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-jre-slim
WORKDIR /usr/app

RUN groupadd admin && useradd -g admin admin
USER admin

RUN mkdir -p /var/log

COPY --from=builder /usr/app/dependencies/ ./
COPY --from=builder /usr/app/spring-boot-loader/ ./
COPY --from=builder /usr/app/snapshot-dependencies/ ./
COPY --from=builder /usr/app/application/ ./

ENTRYPOINT ["java","-noverify", "org.springframework.boot.loader.JarLauncher"]