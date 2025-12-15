# Common Page Tests

This document explains how to use the `CommonPageTest` class to implement reusable tests across all pages in your Selenium test suite.

## Overview

The `CommonPageTest` class provides a set of common tests that apply to all pages in your application, such as:

- Logo presence and visibility
- Page load verification
- Title verification
- Footer presence
- URL validation

## Architecture

```diagram
BaseTest (WebDriver setup/teardown)
    ↓
CommonPageTest (Common tests for all pages)
    ↓
YourPageTest (Page-specific tests)
```

## How to Use

### 1. Create a Test Class

To create a test class that includes common tests, extend `CommonPageTest`:

```java
package upo.eps.tests;

import org.testng.annotations.BeforeMethod;
import upo.eps.base.BasePage;
import upo.eps.base.CommonPageTest;
import upo.eps.pages.YourPage;

public class YourPageTest extends CommonPageTest {
    
    private YourPage page;
    
    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setup")
    public void setupPage() {
        navigateToPage();
        page = new YourPage(driver);
    }
    
    @Override
    protected BasePage createPageObject() {
        if (page == null) {
            page = new YourPage(driver);
        }
        return page;
    }
    
    @Override
    protected void navigateToPage() {
        navigateToBaseUrl(); // or navigateTo("/your-path")
    }
    
    // Add your page-specific tests here
}
```

### 2. Common Tests Included

When you extend `CommonPageTest`, your test class automatically inherits these tests:

| Test Method | Group | Priority | Description |
|------------|-------|----------|-------------|
| `testLogoIsPresent()` | smoke, common | 1 | Verifies logo is present on the page |
| `testLogoIsDisplayed()` | smoke, common | 2 | Verifies logo is visible |
| `testPageLoadsSuccessfully()` | smoke, common | 3 | Verifies page loads with valid title |
| `testPageHasTitle()` | regression, common | 4 | Verifies page has non-empty title |
| `testFooterIsPresent()` | regression, common | 5 | Verifies footer is present |
| `testPageUrlIsCorrect()` | regression, common | 6 | Verifies URL is valid |

### 3. Customizing Locators

You can customize the logo or footer locators by overriding these methods:

```java
@Override
protected By getLogoLocator() {
    return By.cssSelector("img.your-custom-logo-class");
}

@Override
protected By getFooterLocator() {
    return By.id("custom-footer-id");
}
```

### 4. Running Tests

Run all common tests across all pages:

```bash
mvn test -Dgroups=common
```

Run smoke tests (includes common smoke tests):

```bash
mvn test -Dgroups=smoke
```

Run tests for a specific page:

```bash
mvn test -Dtest=EpsHomePageTest
```

## Example: EpsHomePageTest

```java
public class EpsHomePageTest extends CommonPageTest {
    
    private EpsHomePage homePage;
    
    @BeforeMethod(alwaysRun = true, dependsOnMethods = "setup")
    public void setupPage() {
        navigateToPage();
        homePage = new EpsHomePage(driver);
    }
    
    @Override
    protected BasePage createPageObject() {
        if (homePage == null) {
            homePage = new EpsHomePage(driver);
        }
        return homePage;
    }
    
    @Override
    protected void navigateToPage() {
        navigateToBaseUrl();
    }
    
    // This class now has all common tests (logo, footer, etc.)
    // Plus any page-specific tests you add below
    
    @Test(groups = {"regression", "homepage"}, priority = 10)
    public void testMainMenuIsDisplayed() {
        Assert.assertTrue(homePage.isMainMenuDisplayed(), 
            "Main menu should be displayed");
    }
}
```

## Benefits

1. **DRY Principle**: Write common tests once, use everywhere
2. **Consistency**: All pages are tested with the same standards
3. **Maintainability**: Update common tests in one place
4. **Extensibility**: Easy to add new common tests
5. **Flexibility**: Override methods to customize behavior per page

## Best Practices

1. Keep common tests truly common - don't add page-specific logic
2. Use appropriate test groups (smoke, regression, common)
3. Set logical priority values (common tests: 1-9, page-specific: 10+)
4. Override `navigateToPage()` for pages that aren't at base URL
5. Create page-specific tests with priority 10+ to run after common tests

## Adding New Common Tests

To add a new common test to all pages:

1. Open `CommonPageTest.java`
2. Add your test method with `@Test` annotation
3. Use appropriate groups and priority
4. All test classes extending `CommonPageTest` will inherit it automatically

```java
@Test(groups = {"regression", "common"}, priority = 7)
public void testYourNewCommonTest() {
    BasePage page = createPageObject();
    // Your test logic here
}
```
