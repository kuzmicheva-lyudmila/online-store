FROM openjdk:11-slim
COPY ./build/libs/payment-service-latest-plain.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "payment-service-latest-plain.jar"]
EXPOSE 8082