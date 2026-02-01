FROM gradle:8.5-jdk17

WORKDIR /app

COPY . .

# ✅ ONLY BUILD — NO SELENIUM HERE
RUN gradle clean build -x test --no-daemon
