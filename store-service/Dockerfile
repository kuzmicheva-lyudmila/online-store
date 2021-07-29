FROM openjdk:11-slim
COPY ./build/libs/store-service-latest-plain.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "store-service-latest-plain.jar"]
EXPOSE 8081