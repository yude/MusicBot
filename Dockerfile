FROM maven:eclipse-temurin AS builder
ADD . /app
WORKDIR /app
RUN mvn install

FROM openjdk:11-jre-slim AS runner
WORKDIR /app
RUN apt -y update; apt -y upgrade; apt -y install fontconfig libfreetype6 libmpg123-dev
COPY --from=builder /app/target/JMusicBot-Snapshot-All.jar /bin/JMusicBot.jar

CMD ["java", "-Dnogui=true", "-jar", "/bin/JMusicBot.jar"]