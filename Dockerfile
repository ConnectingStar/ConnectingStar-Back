FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS="-Dcom.amazonaws.sdk.disableEc2Metadata=true"
ENV TZ Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app.jar"]