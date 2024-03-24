
# Use the official PostgreSQL image from Docker Hub

FROM openjdk:17-oracle
WORKDIR /app
COPY ./build/libs/Assessment-*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]