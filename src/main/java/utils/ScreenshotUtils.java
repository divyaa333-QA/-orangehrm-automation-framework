package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures a screenshot of the current browser window and saves it to the
 * /screenshots folder with a meaningful, unique name (test name + timestamp).
 * Called automatically by TestListener whenever a test fails.
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "screenshots";

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            // Make sure the screenshots folder exists.
            Path dirPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + File.separator + fileName;

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);

            Files.copy(sourceFile.toPath(), destinationFile.toPath());

            return filePath;
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot for test: " + testName + " - " + e.getMessage());
            return null;
        }
    }
}
