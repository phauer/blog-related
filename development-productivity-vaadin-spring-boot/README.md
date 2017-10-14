- example application with some bootstrapping (spring security, JDBC auto configuration, h2 console, actuator endpoints, vaadin view discovery) to keep the application busy during startup. this way, we can better see the impact of our optimizations.

# TODO

- add simple application (like scaffolding example)
    - add some SpringViews to keep app more busy during startup!
- add theme compilation to build
- custom theme to demonstrate time for theme compilation
    - measure
- productionMode flag in application.properties
    - measure
- lazy vs eager servlet loading
    - measure
- analyze dev tools

- measure:
            RUN     DEBUG
plain       TODO
vm args

# Build and Run

```bash
./mvnw clean package && java -jar target/development-productivity-vaadin-spring-boot*.jar
```

# notes

- vaadin on-the-fly sass compilation only works if we put the themes under `webapp` instead of `resources`