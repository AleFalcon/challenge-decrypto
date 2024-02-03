FROM adoptopenjdk/openjdk17:alpine-jre

LABEL version="0.0.1-SNAPSHOT"

VOLUME /tmp

COPY build/libs/com.challenge.decrypto-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]