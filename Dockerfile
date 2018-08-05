FROM openjdk:8u171-jre-alpine3.8

ARG JAR_FILE

COPY ${JAR_FILE} app.jar
RUN mkdir newrelic && wget https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-agent-4.3.0.jar -O newrelic/newrelic.jar
COPY newrelic.yml newrelic/newrelic.yml

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

HEALTHCHECK --start-period=90s \
  CMD wget --quiet --tries=1 --spider --timeout=30 http://localhost:8080/actuator/health || exit 1
