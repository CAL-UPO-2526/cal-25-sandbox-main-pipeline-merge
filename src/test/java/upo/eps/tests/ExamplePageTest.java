package upo.eps.tests;

import org.testng.annotations.BeforeMethod;
import upo.eps.base.BasePage;
import upo.eps.base.CommonPageTest;
import upo.eps.pages.EpsHomePage;

/**
 * Example test class demonstrating how to use CommonPageTest.
 * 
 * This class automatically inherits all common tests:
 * - testLogoIsPresent()
 * - testLogoIsDisplayed()
 * - testPageLoadsSuccessfully()
 * - testPageHasTitle()
 * - testFooterIsPresent()
 * - testPageUrlIsCorrect()
 * 
 * You only need to implement:
 * 1. createPageObject() - to return your page object
 * 2. navigateToPage() - to navigate to your specific page (optional)
 * 3. Any page-specific tests
 */
public class ExamplePageTest extends CommonPageTest {
    
    private EpsHomePage page;
    
    /**
     * Setup method that runs before each test.
     * Navigates to the page and creates the page object.
     */
    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setup")
    public void setupPage() {
        navigateToPage();
        page = new EpsHomePage(driver);
    }
    
    /**
     * Implementation of abstract method from CommonPageTest.
     * Returns the page object for common tests.
     */
    @Override
    protected BasePage createPageObject() {
        if (page == null) {
            page = new EpsHomePage(driver);
        }
        return page;
    }
    
    /**
     * Navigate to your specific page.
     * Override this if your page is not the base URL.
     */
    @Override
    protected void navigateToPage() {
        navigateToBaseUrl();
        // Or navigate to a specific path:
        // navigateTo("/specific-path");
    }
    
    // Add your page-specific tests here
    // The common tests (logo, footer, etc.) are automatically inherited
}
