package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extentReports;

    public static ExtentReports getExtentReports(){
        if(extentReports==null) {
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter("reports/tetsNGReport.html");

            extentSparkReporter.config().setReportName("Automation");
            extentSparkReporter.config().setDocumentTitle("test case");
            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);

        }
        return extentReports;
    }
}
