package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import testdata.LoginData;
import utils.ConfigReader;

/**
 * Covers scenarios 1-6 from the test plan:
 * 1. Valid login
 * 2. Invalid username/password
 * 3. Invalid username
 * 4. Invalid password
 * 5. Verify login error message
 * 6. Verify successful login
 */
@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    // Covers scenarios 1 and 6: valid login redirects to the dashboard.
    @Test(priority = 1, description = "Verify a user can log in with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());

        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Dashboard was not displayed after valid login");
    }

    // Covers scenarios 2, 3, 4 and 5 using a DataProvider so multiple
    // username/password combinations are exercised by a single test method.
    @Test(priority = 2, dataProvider = "loginData", dataProviderClass = LoginData.class,
            description = "Verify login behaviour for valid and invalid username/password combinations")
    public void testLoginWithMultipleCredentials(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        if (expectedResult.equals("success")) {
            Assert.assertTrue(loginPage.isDashboardDisplayed(),
                    "Expected successful login for username: " + username);
        } else {
            String actualErrorMessage = loginPage.getLoginErrorMessage();
            Assert.assertFalse(actualErrorMessage.isEmpty(),
                    "Expected an error message for invalid credentials but none was shown");
        }
    }
}
