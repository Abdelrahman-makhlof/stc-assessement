
# Use the official PostgreSQL image from Docker Hub

FROM openjdk:21-oracle
WORKDIR /app
COPY ./build/libs/stc-assessment-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]