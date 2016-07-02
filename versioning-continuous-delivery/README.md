Example configuration to show how to use the git commit hash as the artifact/image version.

Additionally, I prefix the commit hash with the commit timestamp for better human readability ("20160702-152019.7555485").

- `mvn package` to build the jar, wrap it in a docker image and install the image to the local registry.
- `docker-compose up` to start the built image locally. the service runs on localhost:8080
- `mvn deploy` to push the image to the docker registry.