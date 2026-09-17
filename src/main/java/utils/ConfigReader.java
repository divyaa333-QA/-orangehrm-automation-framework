package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads key/value pairs from config.properties so that the browser type,
 * application URL and test credentials are never hardcoded inside the
 * framework or test classes.
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    // Static block loads the properties file once, the first time this class is used.
    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties from path: " + CONFIG_PATH, e);
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getValidUsername() {
        return properties.getProperty("validUsername");
    }

    public static String getValidPassword() {
        return properties.getProperty("validPassword");
    }

    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(properties.getProperty("explicitWaitSeconds"));
    }
}
