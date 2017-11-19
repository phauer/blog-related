# REST API Pagination Example Application

# Getting Started

```bash
./mvnw package && java -jar target/rest-api-pagination*.jar
```

Open `http://localhost:8000/designs?pageSize=5` in your browser an click on the URL in the `nextPage` field in the json payload.

# The Application

It's a lightweight HTTP service written in Kotlin and powered by [HTTP4K](https://www.http4k.org/). It starts within 600 ms. ;-) 
