FROM adoptopenjdk:13-jre-hotspot
EXPOSE 8080

ENTRYPOINT exec java -XX:MaxRAMPercentage=80 -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar app.jar

HEALTHCHECK --start-period=30s \
  CMD curl --silent --fail --max-time 30 http://localhost:8080/actuator/health || exit 1

COPY *.jar app.jar
