# rest-api-doc-jaxrs-swagger-asciidoc

Build:

```bash
mvn package
java -jar target/rest-api-doc-jaxrs-swagger-asciidoc-1.0-SNAPSHOT.jar server config.yml
```

Resources:
 
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
