<<<<<<< HEAD
# -orangehrm-automation-framework
Selenium Java automation framework for testing the OrangeHRM web application using TestNG.
=======
# OrangeHRM Automation Framework

## 1. Project Title
OrangeHRM Selenium + TestNG Automation Framework

## 2. Project Description
This is a UI test automation framework built for the OrangeHRM demo application. It automates core HR workflows — logging in, viewing the dashboard, adding and searching for an employee, and logging out — using the Page Object Model so the tests stay readable and the locators stay in one place. It's built at a level that reflects about a year of hands-on QA experience: solid fundamentals (explicit waits, data-driven tests, a failure listener) without over-engineering.

## 3. Application Under Test
[OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)

## 4. Technologies Used
- Java 21
- Selenium WebDriver 4.x
- TestNG 7.x
- Maven
- WebDriverManager (automatic driver binary management)
- Page Object Model (POM)

## 5. Framework Structure
```
OrangeHRM-QA-Framework/
│
├── pom.xml
├── testng.xml
├── .gitignore
├── README.md
│
├── src/
│   ├── main/java/
│   │   ├── base/BaseTest.java
│   │   ├── pages/
│   │   │   ├── LoginPage.java
│   │   │   ├── DashboardPage.java
│   │   │   ├── PIMPage.java
│   │   │   ├── EmployeeListPage.java
│   │   │   └── AddEmployeePage.java
│   │   ├── utils/
│   │   │   ├── ConfigReader.java
│   │   │   ├── WaitUtils.java
│   │   │   └── ScreenshotUtils.java
│   │   └── listeners/TestListener.java
│   │
│   └── test/
│       ├── java/
│       │   ├── tests/
│       │   │   ├── LoginTest.java
│       │   │   ├── DashboardTest.java
│       │   │   ├── EmployeeTest.java
│       │   │   └── LogoutTest.java
│       │   └── testdata/LoginData.java
│       └── resources/config.properties
│
└── screenshots/   (auto-created on first failure)
```

## 6. Test Scenarios
**Login**
1. Valid login
2. Login with invalid username/password
3. Login with invalid username
4. Login with invalid password
5. Verify login error message
6. Verify successful login

**Dashboard**
7. Verify dashboard page is displayed
8. Verify key dashboard elements are visible

**PIM / Employee**
9. Navigate to PIM
10. Verify PIM page loads
11. Add a new employee
12. Verify employee creation
13. Search for the created employee
14. Verify employee appears in search results
15. Open employee details
16. Verify employee details are correct

**Logout**
17. Logout
18. Verify user is returned to the login page

## 7. Prerequisites
- Java 21 (JDK) installed and on PATH
- Maven 3.8+ installed
- IntelliJ IDEA (Community or Ultimate)
- Google Chrome and/or Microsoft Edge installed
- Internet access (WebDriverManager downloads the matching driver binary automatically — no need to manually download chromedriver/msedgedriver)

## 8. How to Configure the Project
Open `src/test/resources/config.properties` and set:
```properties
browser=chrome
url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
validUsername=Admin
validPassword=admin123
explicitWaitSeconds=15
```

## 9. How to Run Using IntelliJ
1. Open IntelliJ IDEA → `File > Open` → select the `OrangeHRM-QA-Framework` folder.
2. Let IntelliJ detect the `pom.xml` and reload Maven dependencies (or click the Maven refresh icon).
3. Right-click `testng.xml` in the project root → `Run 'testng.xml'`.
4. Alternatively, right-click any individual test class (e.g. `LoginTest.java`) → `Run`.

## 10. How to Run Using Maven
From the project root, run:
```bash
mvn clean test
```
This compiles the project and executes the suite defined in `testng.xml`.

## 11. How to Change Chrome/Edge
Edit one line in `config.properties`:
```properties
browser=edge
```
No Java code changes are required — `BaseTest.java` reads this value and launches the matching browser.

## 12. Where Reports Are Generated
After running the suite, TestNG generates its default HTML/XML report at:
```
test-output/index.html
```
Open `test-output/index.html` in a browser to see a summary of passed, failed, and skipped tests, grouped by test class.
- **Passed** — the test's assertions all succeeded.
- **Failed** — at least one assertion failed or an exception was thrown.
- **Skipped** — the test did not run, usually because a `@BeforeMethod` (like `setUp()`) failed first.

## 13. Where Screenshots Are Stored
When a test fails, `TestListener.java` automatically captures a screenshot via `ScreenshotUtils.java` and saves it to:
```
screenshots/<testName>_<timestamp>.png
```
No screenshot code is needed inside the test methods themselves.

## 14. Git Commands
```bash
git init
git add .
git commit -m "Initial commit: OrangeHRM Selenium + TestNG automation framework"
git branch -M main
git remote add origin <your-repository-url>
git push -u origin main
```
`.gitignore` excludes `target/`, `.idea/`, `*.iml`, generated screenshots, and TestNG's `test-output/` folder so only source files are pushed.

## 15. Future Improvements
- Add cross-browser execution via TestNG parameters for parallel suite runs
- Extend coverage to Admin/User Management and Leave modules
- Integrate with a CI pipeline (e.g. GitHub Actions) for scheduled runs
- Add an Extent Reports–style HTML report with embedded failure screenshots
- Externalize employee test data into a CSV/Excel file for broader data-driven coverage
>>>>>>> 42aad53 (Initial Selenium Automation Project)
