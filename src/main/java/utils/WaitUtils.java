package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Central place for all explicit wait logic.
 *
 * We use explicit waits (WebDriverWait) instead of Thread.sleep() because:
 *  - Thread.sleep() always waits the full fixed time, making tests slow and flaky.
 *  - WebDriverWait polls the DOM and returns as soon as the condition is true,
 *    which makes tests both faster and more reliable on a page like OrangeHRM
 *    where elements load asynchronously (AJAX calls, React components, etc.)
 */
public class WaitUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWaitSeconds()));
    }

    // Waits until the element is present in the DOM and visible on the page.
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Waits until the element is visible AND enabled, so a click won't be intercepted.
    public WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Waits until the element exists in the DOM (does not require it to be visible).
    public WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Waits until the current URL contains the given fragment - useful after navigation/login.
    public boolean waitForUrlContains(String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }

    // Waits until at least one element matching the locator is visible - useful for search results.
    public boolean waitForAnyVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
    }

    // Waits until the element's "value" attribute contains the given text.
    // Needed for fields like Personal Details' First/Last Name, which render
    // empty first and get filled in a moment later by an async API call -
    // waitForVisibility alone would return before the real value has loaded.
    public boolean waitForValueContains(By locator, String value) {
        return wait.until(ExpectedConditions.attributeContains(locator, "value", value));
    }
}
