FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build
WORKDIR /

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_NAME
COPY --from=build /app/build/libs/${JAR_NAME} /app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]