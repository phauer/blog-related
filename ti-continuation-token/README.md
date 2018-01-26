# Kotlin Demo

```bash
./mvnw package && java -jar target/demo-kotlin*.jar
```

Open `http://localhost:8000/designs?pageSize=3` in your browser and click on the URL in the `nextPage` field in the json payload. You can also submit a certain start date with the query parameter `modifiedSince`: `http://localhost:8000/designs?pageSize=3&modifiedSince=1512757072`

The demo application is a lightweight HTTP service written in Kotlin and powered by [HTTP4K](https://www.http4k.org/). It starts within 600 ms 🏇
