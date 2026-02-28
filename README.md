# 🚀 Latest Fixed Setup: Selenium + Gradle + TestNG + Cucumber (2025)

This guide provides a **fully working, up‑to‑date, Docker & Jenkins compatible** setup for:

* Selenium **4.x**
* Gradle **8.x**
* TestNG
* Cucumber (BDD)
* Remote execution using **Selenium Standalone Chrome**

> ✅ Tested pattern for **local + Docker + Jenkins pipelines**

---

## 🧩 Tech Stack Versions (Recommended)

| Tool     | Version        |
| -------- | -------------- |
| Java     | 17             |
| Selenium | 4.17.0         |
| Gradle   | 8.5            |
| TestNG   | 7.9.0          |
| Cucumber | 7.15.0         |
| Chrome   | Docker managed |

---

## 📁 Project Structure

```text
project-root/
│── build.gradle
│── settings.gradle
│── Dockerfile
│── Jenkinsfile
│── src/test/java
│   ├── driver
│   │   └── DriverFactory.java
│   ├── runners
│   │   └── TestRunner.java
│   ├── stepdefs
│   │   └── LoginSteps.java
│   └── hooks
│       └── Hooks.java
│── src/test/resources
│   ├── features
│   │   └── login.feature
│   └── testng.xml
```

---

## ⚙️ build.gradle (LATEST & STABLE)

```gradle
plugins {
    id 'java'
}

group = 'in.co.test'
version = '1.0'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.seleniumhq.selenium:selenium-java:4.17.0'
    testImplementation 'io.cucumber:cucumber-java:7.15.0'
    testImplementation 'io.cucumber:cucumber-testng:7.15.0'
    testImplementation 'org.testng:testng:7.9.0'
}

test {
    useTestNG()
    systemProperty 'remote', System.getProperty('remote', 'false')
}
```

---

## 🧠 DriverFactory.java (FIXED FOR DOCKER)

```java
package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        boolean isRemote = Boolean.parseBoolean(
                System.getProperty("remote", "false")
        );

        try {
            if (isRemote) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");

                driver.set(new RemoteWebDriver(
                        new URL("http://selenium:4444/wd/hub"),
                        options
                ));
            } else {
                driver.set(new ChromeDriver());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
```

---

## 🔁 Hooks.java

```java
package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
```

---

## 🏃 TestRunner.java

```java
package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefs", "hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
```

---

## 🐳 Dockerfile (Gradle + Tests)

```dockerfile
FROM gradle:8.5-jdk17
WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon
CMD ["gradle", "test", "-Dremote=true", "--no-daemon"]
```

---

## 🤖 Jenkinsfile (WORKING)

```groovy
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Create Docker Network') {
            steps {
                sh 'docker network inspect selenium-net || docker network create selenium-net'
            }
        }

        stage('Start Selenium') {
            steps {
                sh '''
                docker rm -f selenium || true
                docker run -d --name selenium \
                  --network selenium-net \
                  --shm-size=2g \
                  selenium/standalone-chrome:4.17.0
                '''
                sh 'sleep 5'
            }
        }

        stage('Build Tests Image') {
            steps {
                sh 'docker build -t cucumber-tests .'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'docker run --rm --network selenium-net cucumber-tests'
            }
        }
    }

    post {
        always {
            sh 'docker rm -f selenium || true'
        }
    }
}
```

---

## ✅ Common FIXES INCLUDED

✔ No `localhost` usage in Docker
✔ Correct Selenium 4 Remote URL
✔ `--shm-size=2g` for Chrome stability
✔ Thread-safe WebDriver
✔ Jenkins + Docker ready

---

## 🧪 Run Commands

### Local:

```bash
gradle test
```

### Docker:

```bash
docker build -t cucumber-tests .
docker run --rm --network selenium-net cucumber-tests
```

---

## 🎯 Result

* Stable execution
* No `SessionNotCreatedException`
* Works in Jenkins CI

---

## 📌 Next Enhancements (Optional)

* Extent Report
* Parallel execution
* Selenium Grid Nodes
* Video recording

---

 
