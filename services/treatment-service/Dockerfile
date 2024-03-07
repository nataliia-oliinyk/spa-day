FROM eclipse-temurin:21-jammy
ENV APP_FILE user-service.jar
ENV APP_HOME /usr/apps
COPY target/*.jar $APP_HOME/$APP_FILE
COPY images/ $APP_HOME/images/
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]