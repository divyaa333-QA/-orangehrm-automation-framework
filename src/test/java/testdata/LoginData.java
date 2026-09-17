package testdata;

import org.testng.annotations.DataProvider;

/**
 * Supplies username/password combinations to LoginTest via a TestNG DataProvider.
 * Each row is: { username, password, expectedResult }
 * expectedResult is either "success" or "failure" so the test can decide
 * which assertion to run.
 */
public class LoginData {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        return new Object[][]{
                {"Admin", "admin123", "success"},   // valid username + valid password
                {"WrongUser", "wrongPass123", "failure"}, // invalid username + invalid password
                {"InvalidUser", "admin123", "failure"},   // invalid username + valid password
                {"Admin", "wrongPassword", "failure"}     // valid username + invalid password
        };
    }
}
