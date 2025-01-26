FROM openjdk:21

WORKDIR /app

COPY dist/QuizApplication-0.0.1-SNAPSHOT.jar /app/QuizApplication-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "QuizApplication-0.0.1-SNAPSHOT.jar"]