# REST API Pagination Example Application

# Getting Started

```bash
cd continuation-token
./mvnw install
cd ../demo-kotlin
./mvnw package && java -jar target/demo-kotlin*.jar
```

Open `http://localhost:8000/designs?pageSize=3` in your browser an click on the URL in the `nextPage` field in the json payload.

# The Application

It's a lightweight HTTP service written in Kotlin and powered by [HTTP4K](https://www.http4k.org/). It starts within 600 ms. ;-) 
