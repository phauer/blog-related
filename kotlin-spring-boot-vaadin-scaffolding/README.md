# Scaffolding for Kotlin, Spring Boot, Vaadin, Spring Data MongoDB

## Development
### Database
Start/Stop MongoDB and MySQL
```
$ docker-compose up # -d flag for background
$ docker-compose down
```
Access MongoDB
```
$ mongo
```
Access MySQL: 
1. a) Use a MySQL client and connect to localhost:3306 with root/root.
2. b) use mysql cli: 
```
$ mysql -h localhost -P 3306 --protocol=tcp -D dbname -u root -p dbname
enter "root"
```

### Application
Start MyApplication.kt out of your IDE. But don't forget to pass the yml (program arguments): 
```
--spring.config.location=myapp.yaml
```
