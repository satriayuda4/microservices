FROM openjdk:8-jdk-alpine
ADD ./target/registry-service-0.0.1-SNAPSHOT.jar registry-service.jar
ADD ./registry-service.sh registry-service.sh
RUN ["chmod", "+x", "/registry-service.sh"]
ENTRYPOINT ["/registry-service.sh"]