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
 * Covers scenarios 17-18 from the test plan:
 * 17. Logout
 * 18. Verify user is returned to login page
 */
@Listeners(TestListener.class)
public class LogoutTest extends BaseTest {

    @Test(description = "Verify a logged-in user can log out and is returned to the login page")
    public void testLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Precondition failed: login was not successful");

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.logout();

        // After logout, OrangeHRM redirects back to the login page - the
        // username field being visible again confirms we are back on it.
        LoginPage loginPageAfterLogout = new LoginPage(driver);
        boolean isBackOnLoginPage = driver.getCurrentUrl().contains("auth/login");

        Assert.assertTrue(isBackOnLoginPage, "User was not redirected back to the login page after logout");
    }
}
