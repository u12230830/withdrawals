FROM openjdk:17-oracle
EXPOSE 8080
ADD withdrawal-engine-web/target/withdrawal-engine-web-1.0.0.jar withdrawal-engine-web-1.0.0.jar
LABEL authors="Fako Peleha"

ENTRYPOINT ["java", "-jar", "/withdrawal-engine-web-1.0.0.jar"]