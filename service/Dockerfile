FROM openjdk:11.0.3-jre-slim

ARG JAR_FILE

RUN  apt-get update && apt-get install -y wget

RUN mkdir newrelic && wget https://download.newrelic.com/newrelic/java-agent/newrelic-agent/4.12.1/newrelic-agent-4.12.1.jar -O newrelic/newrelic.jar
COPY newrelic.yml newrelic/newrelic.yml

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT exec java -XX:MaxRAMPercentage=80 -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar app.jar

HEALTHCHECK --start-period=300s \
  CMD wget --quiet --tries=1 --spider --timeout=30 http://localhost:8080/actuator/health || exit 1
