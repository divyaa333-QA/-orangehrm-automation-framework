package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

/**
 * Page Object for the "Add Employee" page reached from PIM > Add.
 */
public class AddEmployeePage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;

    // Locators
    private final By addEmployeeHeader = By.xpath("//h6[text()='Add Employee']");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    // See PIMPage.addButton for why normalize-space(.) + contains() is used here.
    private final By saveButton = By.xpath("//button[contains(normalize-space(.), 'Save')]");
    private final By employeeIdField = By.xpath("//label[text()='Employee Id']/following::input[1]");
    private final By successToast = By.className("oxd-toast-content--success");

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isAddEmployeePageDisplayed() {
        try {
            return waitUtils.waitForVisibility(addEmployeeHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterFirstName(String firstName) {
        WebElement field = waitUtils.waitForVisibility(firstNameField);
        field.clear();
        field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement field = waitUtils.waitForVisibility(lastNameField);
        field.clear();
        field.sendKeys(lastName);
    }

    // Convenience method that fills the mandatory fields and saves the employee.
    public void addEmployee(String firstName, String lastName) {
        enterFirstName(firstName);
        enterLastName(lastName);
        clickSave();
    }

    public void clickSave() {
        waitUtils.waitForClickability(saveButton).click();
    }

    // After saving, OrangeHRM redirects to the Employee's Personal Details page
    // and auto-generates an Employee Id - its presence confirms creation succeeded.
    public boolean isEmployeeCreated() {
        try {
            return waitUtils.waitForVisibility(employeeIdField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getGeneratedEmployeeId() {
        return waitUtils.waitForVisibility(employeeIdField).getAttribute("value");
    }
}
