FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_NAME
COPY ./build/libs/${JAR_NAME} /app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]