package upo.eps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import upo.eps.base.BasePage;

/**
 * Page Object for EPS (Escuela Politécnica Superior) home page.
 */
public class EpsHomePage extends BasePage {
    
    // Locators
    private final By logoLocator = By.cssSelector("img[alt*='Logo']");
    // The EPS site uses classes like 'mainnav' and 'mainnav-lvl1' for the menu.
    // Try a broad selector that matches the main navigation container.
    private final By mainMenuLocator = By.cssSelector(".mainnav, .mainnav-lvl1, nav#mainnav");
    private final By searchBoxLocator = By.id("search-input");
    // Footer can be a semantic <footer> or an element with id/class footer
    private final By footerLocator = By.cssSelector("footer, #footer, .footer");
    
    /**
     * Constructor
     */
    public EpsHomePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Check if logo is displayed.
     */
    public boolean isLogoDisplayed() {
        return isElementDisplayed(logoLocator);
    }
    
    /**
     * Check if logo is present (may not be visible).
     */
    public boolean isLogoPresent() {
        return isElementPresent(logoLocator);
    }
    
    /**
     * Wait for logo to be visible.
     */
    public EpsHomePage waitForLogo() {
        waitForElement(logoLocator);
        return this;
    }
    
    /**
     * Check if main menu is displayed.
     */
    public boolean isMainMenuDisplayed() {
        return isElementDisplayed(mainMenuLocator);
    }
    
    /**
     * Check if search box is present.
     */
    public boolean isSearchBoxPresent() {
        return isElementPresent(searchBoxLocator);
    }
    
    /**
     * Check if footer is displayed.
     */
    public boolean isFooterDisplayed() {
        return isElementDisplayed(footerLocator);
    }
    
    /**
     * Get page title.
     */
    public String getTitle() {
        return getPageTitle();
    }
    
    /**
     * Verify page is loaded by checking key elements.
     */
    public boolean isPageLoaded() {
        return isLogoPresent() && getCurrentUrl().contains("upo.es");
    }
}
