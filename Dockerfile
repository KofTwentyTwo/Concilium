FROM gcr.io/distroless/java21
WORKDIR /app
COPY concilium-server/build/libs/concilium.jar ./concilium.jar
EXPOSE 8000
USER nonroot
ENTRYPOINT ["java", "-jar", \
  "-Dqqq.javalin.enableStaticFilesFromJar=true", \
  "-Dlog4j2.ignoreExceptions=true", \
  "-Djava.io.tmpdir=/tmp", \
  "/app/concilium.jar"]
