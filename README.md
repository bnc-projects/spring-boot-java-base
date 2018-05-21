[![Build Status](https://travis-ci.com/bnc-projects/spring-boot-java-base.svg?branch=master)](https://travis-ci.com/bnc-projects/spring-boot-java-base)
[![codecov](https://codecov.io/gh/bnc-projects/spring-boot-java-base/branch/master/graph/badge.svg)](https://codecov.io/gh/bnc-projects/spring-boot-java-base)

# Spring Boot Java Base

## How tos

### How to update the base image

See [docker README](config/docker/README.md).

### Build and Test
For IDEs run one of the following commands before importing into your IDE.
#### IntelliJ
```
./gradlew idea
```

#### Eclipse
```
./gradlew eclipse
```

#### Build & Test
```
./gradlew clean assemble check
```

### How to: Build and run locally on Docker
1. `./gradlew clean assemble check docker`
2. `docker run -p 8080:8080 -i -t "spring-boot-java-base"`

#### Debug / Profiling
To debug the container locally, add the following line in to the ENTRYPOINT line after `java` and before `-jar`.
```
"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```
Once this has been added build and run the docker container locally.

### How to: Build production equivalent container
1. `./gradlew clean assemble check docker dockerTag -PTAG=$(git rev-parse --verify HEAD --short) -PBRANCH=$(git rev-parse --abbrev-ref HEAD) -PREPOSITORY_URI=${DOCKER_REPO}${IMAGE_NAME}`


## For more tasks run
```
./gradlew tasks
```
