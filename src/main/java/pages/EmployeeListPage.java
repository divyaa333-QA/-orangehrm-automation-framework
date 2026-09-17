package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

/**
 * Page Object for the Employee List (search results grid under PIM) and the
 * "Personal Details" page that opens when a specific employee row is clicked.
 */
public class EmployeeListPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    // Locators
    private final By employeeListTable = By.className("oxd-table-body");
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");

    public EmployeeListPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isEmployeeListDisplayed() {
        try {
            return waitUtils.waitForVisibility(employeeListTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openEmployeeByName(String employeeName) {
        // Using contains(., ...) instead of contains(text(), ...): text() only reads
        // text nodes that are DIRECT children of the div, but OrangeHRM nests the
        // actual name text inside a child element, so text() sees nothing. "." reads
        // all descendant text, matching what's actually rendered on screen.
        By employeeLink = By.xpath("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-cell') and contains(., '" + employeeName + "')]");
        waitUtils.waitForClickability(employeeLink).click();
    }

    // Confirms we landed on the employee's Personal Details page after opening a row.
    public boolean isPersonalDetailsPageDisplayed() {
        try {
            return waitUtils.waitForVisibility(personalDetailsHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Verifies that the first/last name shown on the Personal Details page match
    // the employee that was created and opened, confirming the details are correct.
    // Waits for the fields' values to be populated first, since they render empty
    // initially and get filled in shortly after by an async API call.
    public boolean verifyEmployeeDetails(String expectedFirstName, String expectedLastName) {
        waitUtils.waitForValueContains(firstNameField, expectedFirstName);
        waitUtils.waitForValueContains(lastNameField, expectedLastName);

        String actualFirstName = waitUtils.waitForVisibility(firstNameField).getAttribute("value");
        String actualLastName = waitUtils.waitForVisibility(lastNameField).getAttribute("value");
        return actualFirstName.equalsIgnoreCase(expectedFirstName) && actualLastName.equalsIgnoreCase(expectedLastName);
    }
}
