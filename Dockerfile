
FROM openjdk:17

EXPOSE 8080:8080


WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY target/Lab5Java.jar app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]