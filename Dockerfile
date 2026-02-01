FROM gradle:8.5-jdk17

WORKDIR /app

COPY . .

RUN gradle clean test -Dremote=true --no-daemon
