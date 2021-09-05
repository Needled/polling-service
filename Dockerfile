FROM gradle:7.2.0-jdk11 as builder
COPY . .
RUN ./gradlew build

FROM openjdk:11.0-jre
WORKDIR /polling-service
COPY --from=builder /home/gradle/build/libs/polling*.jar .
ENTRYPOINT [ "java", "-jar", "/polling-service/polling-service-0.0.1-SNAPSHOT.jar" ]