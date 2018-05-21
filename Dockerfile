FROM alphafoobar/open:8u151-jre-alpine3.7

ARG JAR_FILE
ARG JAVA_OPTS
COPY ${JAR_FILE} app.jar

EXPOSE 8080

CMD ["ntpd", "-s"]
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap","-jar","/app.jar"]

HEALTHCHECK --start-period=90s \
  CMD wget --quiet --tries=1 --spider --timeout=30 http://localhost/1/ping || exit 1
