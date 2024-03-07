FROM eclipse-temurin:21-jammy
ENV APP_FILE appointment-service.jar
ENV APP_HOME /usr/apps
COPY target/*.jar $APP_HOME/$APP_FILE
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]