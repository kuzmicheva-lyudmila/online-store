FROM openjdk:11-slim
COPY ./build/libs/order-service-latest-plain.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "order-service-latest-plain.jar"]
EXPOSE 8080