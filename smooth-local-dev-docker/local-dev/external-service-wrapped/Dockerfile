FROM openjdk:8u151-jre-alpine3.7

RUN apk add --no-cache curl

# default build argument. overwritten in docker-compose.yml
ARG SERVICE_VERSION=2.13.13
RUN curl --user nexusUser:nexusPassword --output external-service.jar https://my-nexus-repo.com/repository/maven-public/de/philipphauer/blog/external-service/$SERVICE_VERSION/external-service-$SERVICE_VERSION.jar

COPY config.yaml /
CMD java -jar external-service.jar --spring.config.location config.yaml
