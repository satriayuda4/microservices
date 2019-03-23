FROM openjdk:8-jdk-alpine
ADD ./target/customer-service-0.0.1-SNAPSHOT.jar customer-service.jar
ADD ./customer-service.sh customer-service.sh
RUN ["chmod", "+x", "/customer-service.sh"]
ENTRYPOINT ["/customer-service.sh"]