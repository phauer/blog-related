An example application with some bootstrapping (spring security, JDBC auto configuration, h2 console, actuator endpoints, vaadin view discovery) to keep the application busy during startup. this way, we can better see the impact of our optimizations.

Build and Run

```bash
./mvnw clean package && java -jar target/development-productivity-vaadin-spring-boot*.jar
```

Search in created jar

```bash
./mvnw clean package
unzip -l target/development-productivity-vaadin-spring-boot*.jar | grep css 
```

Compile Vaadin Theme

```bash
./mvnw vaadin:compile-theme 
```

# Side Notes

Where to put `VAADIN/themes/mytheme`? `src/main/resources` or `src/main/webapp`? Vaadin's on-the-fly compilation works in both cases! (given: there is no `styles.css` (`src` and `target/classes`) and `production-mode=false`)

- `webapp`: sass changes are automatically detected and a recompilation is triggered. `styles.scss.cache` is created. no manual action required. But somehow, there is no `styles.css` in the built jar, although everything works fine.
- `resources`: after a change (in let's say `mytheme.scss`), you have to hit `Ctrl+Shift+F9` in IDEA. Now, the change is detected and recompilation is triggered. There is no `styles.scss.cache` at all.
  - Advantages: it's the [recommend location](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-static-content) of spring boot! Moreover, you can find the generated styles.css in the jar!
  - Drawback: more uncomfortable theme editing.
  
If somebody can explain this behavior, please approach me!
