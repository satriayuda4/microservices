#!/bin/sh
while ! nc -z registry-service 8761 ; do
    echo "Waiting for the Registry Service"
    sleep 3
done
java -Djava.security.egd=file:/dev/./urandom -jar /account-service.jar
