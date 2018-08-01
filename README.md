[![Build Status](https://travis-ci.com/bnc-projects/spring-boot-java-base.svg?branch=master)](https://travis-ci.com/bnc-projects/spring-boot-java-base)
[![codecov](https://codecov.io/gh/bnc-projects/spring-boot-java-base/branch/master/graph/badge.svg)](https://codecov.io/gh/bnc-projects/spring-boot-java-base)
[![Known Vulnerabilities](https://snyk.io/test/github/bnc-projects/spring-boot-java-base/badge.svg)](https://snyk.io/test/github/bnc-projects/spring-boot-java-base)
# Spring Boot Java Base

## How tos

### Build and Test
For IDEs run one of the following commands before importing into your IDE.

#### IntelliJ
```bash
./gradlew idea
```

#### Eclipse
```bash
./gradlew eclipse
```

#### Build & Test
```bash
./gradlew clean assemble check
```

### How to: Build and run locally on Docker
1. `./gradlew clean assemble check docker`
2. `docker run -e SPRING_PROFILES_ACTIVE=localhost -p 8080:8080 -i -t spring-boot-java-base`

#### Debug / Profiling
To debug the container locally, the `JAVA_OPTS` environment variable can be provided when running the container.
```bash
docker run -p 8080:8080 -i -t -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" spring-boot-java-base
```

### Enabling New Relic
New relic can be enabled by providing the following `JAVA_OPTS` environment variable. Ensure to provide the correct `newrelic.environment` system property. To configure New Relic add/update the Application Environments section in `newrelic.yml`, more information can be found [here](https://docs.newrelic.com/docs/agents/java-agent/configuration/java-agent-configuration-config-file). 
```base
docker run -p 8080:8080 -i -t -e JAVA_OPTS="-javaagent:newrelic/newrelic.jar -Dnewrelic.environment=${environment} -Dnewrelic.config.file=newrelic/newrelic.yml" spring-boot-java-base
```

### How to: Build production equivalent container
```bash
./gradlew clean assemble check docker dockerTag -PTAG=$(git rev-parse --verify HEAD --short) -PREPOSITORY_URI=${DOCKER_REPO}${IMAGE_NAME}
```

#### Recommended JAVA_OPTS
It is strongly recommended that the following Java Options are set when running the service in production.

**-XX:MaxRAMFraction=n** should only be set to 1 for containers with only 1 processes running. Setting the value will set the heap to 100% of the memory in the container.
```base
JAVA_OPTS="-XX:MaxRAMFraction=2 -XX:+UseG1GC -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -javaagent:newrelic/newrelic.jar -Dnewrelic.environment=${environment} -Dnewrelic.config.file=newrelic/newrelic.yml -Djava.security.egd=file:/dev/./urandom"
```

## For more tasks run
```bash
./gradlew tasks
```
