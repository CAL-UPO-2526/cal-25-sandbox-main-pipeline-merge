package upo.eps.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base page class that all page objects should extend.
 * Provides common web element interaction methods with explicit waits.
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Constructor that initializes driver and wait.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    /**
     * Wait for element to be visible and return it.
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable and return it.
     */
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element presence (not necessarily visible).
     */
    protected WebElement waitForPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Check if element is present on the page.
     */
    public boolean isElementPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is displayed on the page.
     */
    public boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Click on element with explicit wait.
     */
    protected void click(By locator) {
        waitForClickable(locator).click();
    }
    
    /**
     * Type text into element with explicit wait.
     */
    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element with explicit wait.
     */
    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }
    
    /**
     * Get all elements matching locator.
     */
    protected List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }
    
    /**
     * Wait for page title to contain specific text.
     */
    protected void waitForTitle(String titlePart) {
        wait.until(ExpectedConditions.titleContains(titlePart));
    }
    
    /**
     * Get current page URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Get current page title.
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current page title (convenience method).
     */
    public String getTitle() {
        return driver.getTitle();
    }
    
    /**
     * Check if page is loaded by verifying document ready state.
     */
    public boolean isPageLoaded() {
        try {
            return driver.getCurrentUrl() != null && !driver.getCurrentUrl().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
