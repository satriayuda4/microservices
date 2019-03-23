FROM openjdk:8-jdk-alpine
ADD ./target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ADD ./account-service.sh account-service.sh
RUN ["chmod", "+x", "/account-service.sh"]
ENTRYPOINT ["/account-service.sh"]