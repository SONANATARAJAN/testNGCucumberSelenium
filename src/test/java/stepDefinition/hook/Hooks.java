package stepDefinition.hook;

import in.co.driverFactory.DriverFactory;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Before;

public class Hooks {

    public WebDriver driver;

    @Before
    public void beforeScenario() throws InterruptedException {
        DriverFactory.initDriver();
        DriverFactory.getDriver().get("https://www.flipkart.com/");
        Thread.sleep(4000);

    }

    @After
    public void afterScenario() {
        DriverFactory.quitDriver();

    }
}

