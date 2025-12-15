package upo.eps.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import upo.eps.base.BaseTest;

/**
 * Refactored version of LogoTest using BaseTest pattern.
 * This demonstrates how to migrate existing tests to use the base class structure.
 */
public class LogoTestRefactored extends BaseTest {
    
    @Test(groups = {"smoke", "logo"})
    public void testLogoIsPresent() {
        // Get configurable properties
        String targetUrl = System.getProperty("target.url", baseUrl);
        String logoSelector = System.getProperty("logo.selector", "img[alt*='Logo']");
        
        // Navigate to target URL
        driver.get(targetUrl);
        
        // Create locator and verify
        By logoBy = By.cssSelector(logoSelector);
        
        try {
            // Wait for element presence
            boolean found = !driver.findElements(logoBy).isEmpty();
            Assert.assertTrue(found, "Logo element not found using selector: " + logoSelector + " at " + targetUrl);
            
            // Verify element is displayed
            boolean displayed = driver.findElement(logoBy).isDisplayed();
            Assert.assertTrue(displayed, "Logo element found but not displayed using selector: " + logoSelector + " at " + targetUrl);
            
        } catch (Exception e) {
            Assert.fail("Failed to verify logo presence on " + targetUrl + " using selector '" + logoSelector + "' - " + e.getMessage());
        }
    }
}
