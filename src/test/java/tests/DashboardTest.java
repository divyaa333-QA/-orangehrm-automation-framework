package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

/**
 * Covers scenarios 7-8 from the test plan:
 * 7. Verify dashboard page is displayed
 * 8. Verify important dashboard elements
 */
@Listeners(TestListener.class)
public class DashboardTest extends BaseTest {

    @Test(priority = 1, description = "Verify the dashboard page is displayed after login")
    public void testDashboardPageIsDisplayed() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(dashboardPage.isDashboardPageDisplayed(), "Dashboard page was not displayed");
    }

    @Test(priority = 2, description = "Verify key dashboard elements (header, side menu, user dropdown) are visible")
    public void testDashboardElementsAreDisplayed() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());

        DashboardPage dashboardPage = new DashboardPage(driver);

        Assert.assertTrue(dashboardPage.areDashboardElementsDisplayed(),
                "One or more expected dashboard elements were not visible");
    }
}
