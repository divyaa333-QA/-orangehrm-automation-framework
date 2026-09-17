package listeners;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtils;

/**
 * TestNG listener that hooks into the lifecycle of every test method.
 *
 * Its main job is to automatically capture a screenshot whenever a test
 * fails, without requiring any screenshot code inside the individual
 * @Test methods. It retrieves the live WebDriver instance from the test
 * class itself (which extends BaseTest), since ITestResult gives us
 * access to the test class instance that was actually running.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("TEST STARTED: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("TEST PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("TEST FAILED: " + result.getName());

        Object currentTestClassInstance = result.getInstance();

        if (currentTestClassInstance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) currentTestClassInstance).getDriver();

            if (driver != null) {
                String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
                System.out.println("Screenshot saved at: " + screenshotPath);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("TEST SKIPPED: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("TEST SUITE STARTED: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("TEST SUITE FINISHED: " + context.getName());
    }
}
