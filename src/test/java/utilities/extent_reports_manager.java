package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import test_base.base_class;

public class extent_reports_manager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private Map<String, ExtentTest> testMap = new HashMap<>();
    private String repName;

    @Override
    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName;
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Functional Testing Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Orange HRM");
        extent.setSystemInfo("Module", "HR Management");
        extent.setSystemInfo("Tester", "Amal Das");
        extent.setSystemInfo("Browser", "Chrome");
    }

    private ExtentTest getTest(ITestResult result) {
        String key = result.getTestClass().getName() + " :: " + result.getMethod().getMethodName();
        if (!testMap.containsKey(key)) {
            ExtentTest t = extent.createTest(key);
            t.assignCategory(result.getMethod().getGroups());
            testMap.put(key, t);
        }
        return testMap.get(key);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = getTest(result);
        test.log(Status.PASS, result.getName() + " executed successfully ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = getTest(result);
        test.log(Status.FAIL, result.getName() + " failed ❌");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().toString());
        }

        try {
            String imgPath = base_class.captureScreen(result.getName() + "_FAIL_" +
                    new SimpleDateFormat("HH.mm.ss").format(new Date()));
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = getTest(result);
        test.log(Status.SKIP, result.getName() + " was skipped ⚠️");
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().toString());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        // Open report automatically (Windows only)
        try {
            File reportFile = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + repName);
            if (Desktop.isDesktopSupported() && reportFile.exists()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            } else {
                System.out.println("Report generated at: " + reportFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Not used but required by ITestListener
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}
