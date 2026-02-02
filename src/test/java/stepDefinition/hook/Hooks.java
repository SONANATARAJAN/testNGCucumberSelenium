package stepDefinition.hook;

import com.aventstack.extentreports.*;
import in.co.driverFactory.DriverFactory;
import io.cucumber.java.*;
import org.openqa.selenium.*;
import report.ExtentManager;

import java.util.Base64;

public class Hooks {

    static ExtentReports extent = ExtentManager.getExtentReports();
    static ExtentTest scenarioTest;

    @Before
    public void beforeScenario(Scenario scenario) {
        DriverFactory.initDriver(false);
        DriverFactory.getDriver().get("https://www.flipkart.com/");
        scenarioTest = extent.createTest(scenario.getName());
    }

    @AfterStep
    public void afterEachStep(Scenario scenario) {

        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

        String base64 = Base64.getEncoder().encodeToString(screenshot);

        if (scenario.isFailed()) {
            scenarioTest.fail("Step Failed")
                    .addScreenCaptureFromBase64String(base64);
        } else {
            scenarioTest.pass("Step Passed")
                    .addScreenCaptureFromBase64String(base64);
        }
    }

    @After
    public void afterScenario() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
