FROM adoptopenjdk/openjdk11
RUN addgroup spring && adduser  --ingroup spring --disabled-password spring
USER 10001
WORKDIR /home/spring/app
ARG JAR_FILE=build/libs/*.jar
ARG TELEMETRY=opentelemetry-javaagent-all.jar
COPY --chown=spring build/libs/tp-rule-engine-1.2.0.jar /home/spring/app/bin/tp-rule-engine-1.2.0.jar
#COPY --chown=spring ${TELEMETRY} /home/spring/app/bin/opentelemetry-javaagent-all.jar "-javaagent:/home/spring/app/bin/opentelemetry-javaagent-all.jar",
ENTRYPOINT ["java", "-Dotel.exporter=jaeger", "-Dotel.exporter.jaeger.endpoint=http://jaeger:14250", "-Dotel.exporter.jaeger.service.name=rule-engine", "-Dapplication.name=tp-rule-engine", "-jar","/home/spring/app/bin/tp-rule-engine-1.2.0.jar"]


EXPOSE 8080
