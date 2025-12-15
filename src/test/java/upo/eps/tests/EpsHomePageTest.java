package upo.eps.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import upo.eps.base.BasePage;
import upo.eps.base.CommonPageTest;
import upo.eps.pages.EpsHomePage;

/**
 * Test class for EPS home page functionality.
 * Extends CommonPageTest to inherit common tests (logo, page load, etc.)
 * and WebDriver setup/teardown from BaseTest.
 */
public class EpsHomePageTest extends CommonPageTest {
    
    private EpsHomePage homePage;
    
    /**
     * Setup method that runs before each test.
     * Navigates to the home page and creates the page object.
     */
    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setup")
    public void setupPage() {
        navigateToPage();
        homePage = new EpsHomePage(driver);
    }
    
    /**
     * Implementation of abstract method from CommonPageTest.
     * Returns the page object for common tests.
     */
    @Override
    protected BasePage createPageObject() {
        if (homePage == null) {
            homePage = new EpsHomePage(driver);
        }
        return homePage;
    }
    
    /**
     * Navigate to the EPS home page.
     */
    @Override
    protected void navigateToPage() {
        navigateToBaseUrl();
    }
    
    // Page-specific tests below
    // Note: Common tests (logo, page load, title, footer, main menu) are inherited from CommonPageTest
    // Remove duplicates here to rely on inheritance.
}
