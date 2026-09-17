package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

/**
 * Every test class extends this class.
 *
 * Responsibilities:
 *  - Create a fresh WebDriver instance before each @Test method
 *  - Select the browser (chrome/edge) based on config.properties, so the
 *    Java code never has to change to switch browsers
 *  - Navigate to the application URL and maximize the window
 *  - Quit the driver after each test, even if the test failed
 *
 * We deliberately do NOT create separate methods like launchChrome() /
 * launchEdge() - a single setUp() method reads the browser name from
 * config and branches once, which keeps this simple enough to explain
 * in an interview.
 */
public class BaseTest {

    // protected so that test classes and TestListener (via reflection/getters) can access it
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getBrowser().trim().toLowerCase();

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser in config.properties: " + browser);
        }

        driver.manage().window().maximize();
        driver.get(ConfigReader.getUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Allows TestListener to retrieve the driver from the currently running test instance.
    public WebDriver getDriver() {
        return driver;
    }
}
