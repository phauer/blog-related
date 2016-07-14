```bash
# install docker, docker-compose and httpie up front
docker-compose up #starts mysql
mvn package #builds the service
java -jar target/uuid-mysql-hibernate-1.jar & #start the service
http POST localhost:8080/products name=paul #creates a product. Hibernate generate a UUID
http GET localhost:8080/products #get all products to see the UUIDs
```