package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import report.ExtentManager;

public class ExtentTestListener implements ITestListener {
    ExtentReports extern = ExtentManager.getExtentReports();
    ExtentTest test;

    @Override
    public void onTestStart(ITestResult result) {
        test = extern.createTest(result.getMethod().getMethodName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("test pass");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test Fail");
    }
    @Override
    public void onFinish(ITestContext result) {
        extern.flush();
    }
}
