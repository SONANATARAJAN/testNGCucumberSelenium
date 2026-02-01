package runnners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
        features = "src/test/resources/feature",
        glue = "stepDefinition",
        plugin = {
                "pretty",
                "html:reports/htmlCucumber.html",
                "json:reports/htmlCucumber.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Test
    public void runCucumber() {

    }
}
