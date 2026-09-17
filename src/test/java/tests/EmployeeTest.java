package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import pages.PIMPage;
import utils.ConfigReader;

/**
 * Covers scenarios 9-16 from the test plan:
 * 9.  Navigate to PIM
 * 10. Verify PIM page
 * 11. Add a new employee
 * 12. Verify employee creation
 * 13. Search for the created employee
 * 14. Verify employee appears in search results
 * 15. Open employee details
 * 16. Verify employee details
 */
@Listeners(TestListener.class)
public class EmployeeTest extends BaseTest {

    // Realistic test data - a unique last name (with timestamp) avoids clashing
    // with employees created by previous test runs on the shared demo site.
    private final String firstName = "Divya";
    private final String lastName = "Varshini" + System.currentTimeMillis() % 100000;

    @Test(priority = 1, description = "Verify navigation to the PIM page and that it is displayed")
    public void testNavigateToPIM() {
        loginAsAdmin();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPIMMenu();

        PIMPage pimPage = new PIMPage(driver);
        Assert.assertTrue(pimPage.isPIMPageDisplayed(), "PIM page was not displayed after navigation");
    }

    @Test(priority = 2, description = "Add a new employee and verify the employee was created")
    public void testAddNewEmployee() {
        loginAsAdmin();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPIMMenu();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.clickAddEmployee();

        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        Assert.assertTrue(addEmployeePage.isAddEmployeePageDisplayed(), "Add Employee page was not displayed");

        addEmployeePage.addEmployee(firstName, lastName);

        Assert.assertTrue(addEmployeePage.isEmployeeCreated(),
                "Employee was not created - Employee Id field not shown after save");
    }

    @Test(priority = 3, description = "Search for the created employee and verify it appears in the results")
    public void testSearchCreatedEmployee() {
        loginAsAdmin();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPIMMenu();

        PIMPage pimPage = new PIMPage(driver);

        // Create the employee first so there is something to search for in this test run.
        pimPage.clickAddEmployee();
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        addEmployeePage.addEmployee(firstName, lastName);
        Assert.assertTrue(addEmployeePage.isEmployeeCreated(), "Precondition failed: employee was not created");

        // Navigate back to PIM to search the full employee list.
        DashboardPage dashboardPageAgain = new DashboardPage(driver);
        dashboardPageAgain.clickPIMMenu();

        pimPage.searchEmployeeByName(firstName + " " + lastName);

        Assert.assertTrue(pimPage.isEmployeeInSearchResults(firstName),
                "Created employee did not appear in search results");
    }

    @Test(priority = 4, description = "Open the created employee's details and verify they are correct")
    public void testOpenAndVerifyEmployeeDetails() {
        loginAsAdmin();

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickPIMMenu();

        PIMPage pimPage = new PIMPage(driver);
        pimPage.clickAddEmployee();

        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        addEmployeePage.addEmployee(firstName, lastName);
        Assert.assertTrue(addEmployeePage.isEmployeeCreated(), "Precondition failed: employee was not created");

        // Go back to PIM list and search for the employee we just created.
        DashboardPage dashboardPageAgain = new DashboardPage(driver);
        dashboardPageAgain.clickPIMMenu();
        pimPage.searchEmployeeByName(firstName + " " + lastName);

        EmployeeListPage employeeListPage = new EmployeeListPage(driver);
        employeeListPage.openEmployeeByName(firstName);

        Assert.assertTrue(employeeListPage.isPersonalDetailsPageDisplayed(),
                "Personal Details page was not displayed after opening the employee");

        Assert.assertTrue(employeeListPage.verifyEmployeeDetails(firstName, lastName),
                "Employee details on the Personal Details page did not match the created employee");
    }

    // Shared login step used by every test in this class.
    private void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Precondition failed: login was not successful");
    }
}
