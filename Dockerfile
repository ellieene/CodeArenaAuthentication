# Dockerfile

FROM eclipse-temurin:21

ARG JAR_FILE=target/CodeArenaAuthentification-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} CodeArenaAuthentification-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/CodeArenaAuthentification-0.0.1-SNAPSHOT.jar"]