FROM openjdk:11-slim
COPY ./build/libs/delivery-service-latest-plain.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "delivery-service-latest-plain.jar"]
EXPOSE 8083