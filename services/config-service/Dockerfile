FROM eclipse-temurin:21-jammy
ENV APP_FILE config-service.jar
ENV APP_HOME /usr/apps
EXPOSE 8088
COPY target/*.jar $APP_HOME/$APP_FILE
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]