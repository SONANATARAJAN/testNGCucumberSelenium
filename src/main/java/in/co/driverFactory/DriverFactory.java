package in.co.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // 🔥 Create driver
    public static void initDriver() {

        if (driver.get() != null) {
            return; // already created
        }

        String remote = System.getProperty("remote", "false");

        ChromeOptions options = new ChromeOptions();
      options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );

      
        try {
            if (remote.equalsIgnoreCase("true")) {
                URL gridUrl = new URL("http://selenium:4444/wd/hub");
                driver.set(new RemoteWebDriver(gridUrl, options));
            } else {
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(options));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL", e);
        }
    }

    // 🔥 Get driver
    public static WebDriver getDriver() {
        return driver.get();
    }

    // 🔥 Quit driver
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
