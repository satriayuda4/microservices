FROM openjdk:8-jdk-alpine
ADD ./target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ADD ./gateway-service.sh gateway-service.sh
RUN ["chmod", "+x", "/gateway-service.sh"]
ENTRYPOINT ["/gateway-service.sh"]
