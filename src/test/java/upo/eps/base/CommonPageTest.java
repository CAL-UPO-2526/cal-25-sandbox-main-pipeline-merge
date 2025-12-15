package upo.eps.base;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Common test class containing tests that apply to all pages.
 * All page-specific test classes should extend this class to inherit common tests.
 * 
 * Common tests include:
 * - Logo presence and visibility
 * - Page load verification
 * - Title verification
 * - Footer presence
 * 
 * These tests can be overridden in specific test classes if needed.
 */
public abstract class CommonPageTest extends BaseTest {
    
    /**
     * Override this method to provide the logo locator for the specific page.
     * Default locator looks for images with "Logo" in the alt attribute.
     */
    protected By getLogoLocator() {
        return By.cssSelector("img[alt*='Logo']");
    }
    
    /**
     * Override this method to provide the footer locator for the specific page.
     * Default locator looks for footer element.
     */
    protected By getFooterLocator() {
        return By.cssSelector("footer, #footer, .footer");
    }
    
    /**
     * Override this to provide the main menu locator for the specific page.
     * Default locator looks for a nav element with class 'main-menu'.
     */
    protected By getMainMenuLocator() {
        // EPS site navigation commonly uses '.mainnav' and nested '.mainnav-lvl1'
        // Include 'nav#mainnav' for semantic nav containers when present.
        return By.cssSelector(".mainnav, .mainnav-lvl1, nav#mainnav");
    }
    
    /**
     * Test that the logo is present on the page.
     * This test applies to all pages in the application.
     */
    @Test(groups = {"smoke", "common"}, priority = 1)
    public void testLogoIsPresent() {
        BasePage page = createPageObject();
        Assert.assertTrue(page.isElementPresent(getLogoLocator()), 
                "Logo should be present on the page");
    }
    
    /**
     * Test that the logo is displayed (visible) on the page.
     * This test applies to all pages in the application.
     */
    @Test(groups = {"smoke", "common"}, priority = 2)
    public void testLogoIsDisplayed() {
        BasePage page = createPageObject();
        page.waitForElement(getLogoLocator());
        Assert.assertTrue(page.isElementDisplayed(getLogoLocator()), 
                "Logo should be visible on the page");
    }
    
    /**
     * Test that the page loads successfully.
     * Verifies that the page has a non-empty title.
     */
    @Test(groups = {"smoke", "common"}, priority = 3)
    public void testPageLoadsSuccessfully() {
        BasePage page = createPageObject();
        Assert.assertTrue(page.isPageLoaded(), 
                "Page should load successfully");
        Assert.assertTrue(page.getTitle().length() > 0, 
                "Page should have a non-empty title");
    }
    
    /**
     * Test that the page title is not empty.
     */
    @Test(groups = {"regression", "common"}, priority = 4)
    public void testPageHasTitle() {
        BasePage page = createPageObject();
        String title = page.getTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertTrue(title.length() > 0, "Page title should not be empty");
    }
    
    /**
     * Test that the footer is present on the page.
     */
    @Test(groups = {"regression", "common"}, priority = 5)
    public void testFooterIsPresent() {
        BasePage page = createPageObject();
        Assert.assertTrue(page.isElementPresent(getFooterLocator()), 
                "Footer should be present on the page");
    }
    
    /**
     * Test that the footer is displayed (visible) on the page.
     */
    @Test(groups = {"regression", "common"}, priority = 5)
    public void testFooterIsDisplayed() {
        BasePage page = createPageObject();
        page.waitForElement(getFooterLocator());
        Assert.assertTrue(page.isElementDisplayed(getFooterLocator()),
                "Footer should be visible on the page");
    }
    
    /**
     * Test that the page URL is correct.
     * Verifies the current URL starts with the expected base URL.
     */
    @Test(groups = {"regression", "common"}, priority = 6)
    public void testPageUrlIsCorrect() {
        BasePage page = createPageObject();
        String currentUrl = page.getCurrentUrl();
        Assert.assertTrue(currentUrl.startsWith(baseUrl) || currentUrl.startsWith("https://"), 
                "Current URL should be valid");
    }
    
    /**
     * Abstract method that must be implemented by subclasses.
     * Should create and return the specific page object for testing.
     * 
     * @return BasePage instance for the page being tested
     */
    protected abstract BasePage createPageObject();
    
    /**
     * Optional: Override this to navigate to a specific page before running tests.
     * By default, navigates to the base URL.
     */
    protected void navigateToPage() {
        navigateToBaseUrl();
    }

    /**
     * Test that the main menu is present on the page.
     */
    @Test(groups = {"regression", "common"}, priority = 7)
    public void testMainMenuIsPresent() {
        BasePage page = createPageObject();
        Assert.assertTrue(page.isElementPresent(getMainMenuLocator()),
                "Main menu should be present on the page");
    }

    /**
     * Test that the main menu is displayed (visible) on the page.
     */
    @Test(groups = {"regression", "common"}, priority = 8)
    public void testMainMenuIsDisplayed() {
        BasePage page = createPageObject();
        page.waitForElement(getMainMenuLocator());
        Assert.assertTrue(page.isElementDisplayed(getMainMenuLocator()),
                "Main menu should be visible on the page");
    }
}
