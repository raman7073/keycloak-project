FROM eclipse-temurin:17
LABEL mantainer="raman.mehta@fiftyfivetech.io"
WORKDIR /app
COPY target/auth-server-0.0.1-SNAPSHOT.jar /app/ums.jar
ENTRYPOINT ["java","-jar","ums.jar"]