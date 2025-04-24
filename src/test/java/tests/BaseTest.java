package tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverManager;
import utils.ReportManager;

import java.io.File;
import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite() {
        // Create directories for screenshots and reports
        new File("screenshots").mkdirs();
        new File("reports").mkdirs();
    }

    @BeforeMethod
    public void setup(Method method) {
        // Initialize driver
        driver = DriverManager.getDriver();

        // Setup reporting
        ExtentTest test = ReportManager.getExtentReports().createTest(method.getName(),
                method.getAnnotation(org.testng.annotations.Test.class).description());
        ReportManager.setTest(test);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Update report based on test result
        ExtentTest test = ReportManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ReportManager.captureScreenshot(driver, result.getName());
            test.log(Status.FAIL, result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed successfully");
        } else {
            test.log(Status.SKIP, "Test skipped");
        }

        // Quit driver
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        // Flush reports
        ReportManager.getExtentReports().flush();
    }
}
