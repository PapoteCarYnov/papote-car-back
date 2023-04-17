FROM openjdk:17.0

COPY . .

RUN chmod +x ./mvnw

CMD ["./mvnw", "spring-boot:run"]