package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

/**
 * Page Object for the PIM (Personnel Information Management) landing page,
 * which lists all employees and provides the "Add Employee" entry point.
 */
public class PIMPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    // Locators
    private final By pimHeader = By.xpath("//h6[text()='PIM']");
    // normalize-space(.) reads ALL text inside the button (not just a specific
    // child span), so this matches regardless of whether "Add" sits directly on
    // the button, inside a span, or alongside an icon element. contains() (rather
    // than an exact match) tolerates a leading icon character being included in
    // the text content.
    private final By addButton = By.xpath("//button[contains(normalize-space(.), 'Add')]");
    private final By employeeNameSearchField = By.xpath("(//label[text()='Employee Name']/following::input)[1]");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private final By employeeListTable = By.className("oxd-table-body");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isPIMPageDisplayed() {
        try {
            return waitUtils.waitForVisibility(pimHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddEmployee() {
        waitUtils.waitForClickability(addButton).click();
    }

    public void searchEmployeeByName(String employeeName) {
        var field = waitUtils.waitForVisibility(employeeNameSearchField);
        field.clear();
        field.sendKeys(employeeName);
        waitUtils.waitForClickability(searchButton).click();
    }

    public boolean isEmployeeInSearchResults(String employeeName) {
        // See EmployeeListPage.openEmployeeByName for why contains(., ...) is used
        // instead of contains(text(), ...) here.
        By employeeRow = By.xpath("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-cell') and contains(., '" + employeeName + "')]");
        try {
            return waitUtils.waitForVisibility(employeeRow).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openFirstSearchResult() {
        By firstResultLink = By.xpath("(//div[@class='oxd-table-body']//div[@class='oxd-table-row'])[1]");
        waitUtils.waitForClickability(firstResultLink).click();
    }
}
