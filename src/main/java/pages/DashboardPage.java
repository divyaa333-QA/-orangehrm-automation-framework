package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

/**
 * Page Object for the OrangeHRM Dashboard page (the page shown right after login).
 */
public class DashboardPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    // Locators
    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By userDropdown = By.className("oxd-userdropdown-tab");
    private final By sidePanel = By.className("oxd-sidepanel");
    private final By pimMenuItem = By.xpath("//span[text()='PIM']");
    private final By logoutLink = By.xpath("//a[text()='Logout']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isDashboardPageDisplayed() {
        try {
            return waitUtils.waitForVisibility(dashboardHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Verifies the key dashboard elements (header, side menu, user dropdown) are all present.
    public boolean areDashboardElementsDisplayed() {
        try {
            boolean headerVisible = waitUtils.waitForVisibility(dashboardHeader).isDisplayed();
            boolean sidePanelVisible = waitUtils.waitForVisibility(sidePanel).isDisplayed();
            boolean userDropdownVisible = waitUtils.waitForVisibility(userDropdown).isDisplayed();
            return headerVisible && sidePanelVisible && userDropdownVisible;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickPIMMenu() {
        waitUtils.waitForClickability(pimMenuItem).click();
    }

    public void logout() {
        WebElement dropdown = waitUtils.waitForClickability(userDropdown);
        dropdown.click();
        waitUtils.waitForClickability(logoutLink).click();
    }
}
