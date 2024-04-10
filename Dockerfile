FROM openjdk:17
LABEL maintainer="community-of-travellersdevs"
WORKDIR /app
COPY target/communityOfTravellers-0.0.1-SNAPSHOT.jar /app/communityOfTravellers.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "communityOfTravellers.jar", "--spring.profiles.active=main"]