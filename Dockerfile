FROM maven:3.5.4-jdk-8-alpine

COPY . /app/

WORKDIR /app/webLibrarySql

CMD ["mvn", "spring-boot:run"]

EXPOSE 8080