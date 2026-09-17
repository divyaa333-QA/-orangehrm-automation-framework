package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

/**
 * Page Object for the OrangeHRM Login page.
 * Holds only login-page locators and login-page actions.
 * Test classes call these methods instead of writing raw Selenium code.
 */
public class LoginPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    // Using contains(@class, ...) instead of an exact @class= match, since OrangeHRM
    // renders this element with several classes (e.g. "oxd-text oxd-text--p oxd-alert-content-text"),
    // and an exact match would never find it.
    private final By errorMessage = By.xpath("//p[contains(@class,'oxd-alert-content-text') or contains(@class,'oxd-input-field-error-message')]");
    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void enterUsername(String username) {
        WebElement field = waitUtils.waitForVisibility(usernameField);
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = waitUtils.waitForVisibility(passwordField);
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        waitUtils.waitForClickability(loginButton).click();
    }

    // Convenience method that performs the full login flow in one call.
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getLoginErrorMessage() {
        return waitUtils.waitForVisibility(errorMessage).getText();
    }

    public boolean isDashboardDisplayed() {
        try {
            return waitUtils.waitForVisibility(dashboardHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
