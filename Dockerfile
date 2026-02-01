FROM gradle:8.6-jdk17

WORKDIR /app

COPY . .

RUN chmod +x gradlew

CMD ["./gradlew", "test", "-Dremote=true"]
