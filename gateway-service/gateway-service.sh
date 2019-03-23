while ! nc -z registry-service 8761 ; do
    echo "Waiting for the Registry Service"
    sleep 3
done
while ! nc -z customer-service 8080 ; do
    echo "Waiting for the Customer Service"
    sleep 3
done
while ! nc -z account-service 8082 ; do
    echo "Waiting for the Account Service"
    sleep 3
done
java -Djava.security.egd=file:/dev/./urandom -jar /gateway-service.jar
