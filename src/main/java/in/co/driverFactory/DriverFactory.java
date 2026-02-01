package in.co.driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {

        if (driver.get() != null) return;

        String remote = System.getProperty("remote", "false");

        ChromeOptions options = new ChromeOptions();

        // ✅ GRID SAFE OPTIONS
        options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080"
        );

        try {
            if (remote.equalsIgnoreCase("true")) {
                driver.set(
                        new RemoteWebDriver(
                                new URL("http://selenium:4444"),
                                options
                        )
                );
            } else {
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(options));
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Driver initialization failed", e);
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
