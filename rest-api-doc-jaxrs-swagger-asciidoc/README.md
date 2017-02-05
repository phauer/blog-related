# rest-api-doc-jaxrs-swagger-asciidoc

## Build and Start

```bash
mvn package
java -jar target/rest-api-doc-jaxrs-swagger-asciidoc-1.0-SNAPSHOT.jar server config.yml
```

## Resources
 
```
# API:
GET http://localhost:8080/bands
GET http://localhost:8080/bands/<uuid>
POST http://localhost:8080/bands

# Swagger Specification:
GET http://localhost:8080/swagger.json
GET http://localhost:8080/swagger.yaml

# API HTML Documentation:
GET http://localhost:8080/application-doc.html
```

## Optional: Swagger-UI

```bash
# start application
cd swagger-ui
docker-compose up
# open in browser: http://localhost:8090/?url=http://localhost:8080/swagger.json
```