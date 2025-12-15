package upo.eps.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;

/**
 * Base test class that all test classes should extend.
 * Provides WebDriver setup, teardown, and common configuration.
 */
public abstract class BaseTest {
    
    protected WebDriver driver;
    protected String baseUrl;
    
    /**
     * Setup method that runs before each test method.
     * Can be configured via TestNG parameters or system properties.
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless", "baseUrl"})
    public void setup(
            @Optional("firefox") String browser,
            @Optional("true") String headless,
            @Optional("https://www.upo.es/escuela-politecnica-superior/es/") String baseUrl) {
        
        // Allow system properties to override TestNG parameters
        browser = System.getProperty("browser", browser);
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", headless));
        this.baseUrl = System.getProperty("baseUrl", baseUrl);
        
        this.driver = createDriver(browser.toLowerCase(), isHeadless);
        configureDriver(driver);
    }
    
    /**
     * Teardown method that runs after each test method.
     * Ensures browser is properly closed.
     */
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    /**
     * Creates a WebDriver instance based on browser type and headless mode.
     */
    protected WebDriver createDriver(String browser, boolean headless) {
        WebDriver webDriver;
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                webDriver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
            default:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
        }
        
        return webDriver;
    }
    
    /**
     * Configure common WebDriver settings.
     */
    protected void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }
    
    /**
     * Navigate to base URL.
     */
    protected void navigateToBaseUrl() {
        driver.get(baseUrl);
    }
    
    /**
     * Navigate to a specific path relative to base URL.
     */
    protected void navigateTo(String path) {
        driver.get(baseUrl + path);
    }
    
    /**
     * Get the current WebDriver instance.
     */
    public WebDriver getDriver() {
        return driver;
    }
}
