# EPS Web Testing Project

Selenium WebDriver + TestNG + Maven project for automated web testing.

Test edit!
    
## Project Structure

```tree
src/test/java/
├── upo/eps/
│   ├── base/                    # Base classes
│   │   ├── BaseTest.java        # Base test class (setup/teardown)
│   │   └── BasePage.java        # Base page object class
│   ├── pages/                   # Page Object Model classes
│   │   └── EpsHomePage.java     # Example: EPS home page
│   ├── tests/                   # Test classes
│   │   ├── EpsHomePageTest.java # Example: Home page tests
│   │   ├── LogoTestRefactored.java
│   │   └── LogoTest.java        # Original test (kept for reference)
│   └── utils/                   # Utilities (future: DriverFactory, etc.)
│
src/test/resources/
├── testng.xml                   # Main test suite
└── smoke-suite.xml              # Smoke test suite
```

## Design Patterns

### 1. BaseTest Pattern

All test classes extend `BaseTest` which provides:

- WebDriver setup/teardown
- Browser configuration (Chrome/Firefox)
- Headless mode toggle
- Base URL management
- Common navigation methods

### 2. BasePage Pattern (Page Object Model)

All page objects extend `BasePage` which provides:

- Explicit wait methods
- Common element interactions
- Element presence/visibility checks
- Reusable helper methods

### 3. Page Object Model

Each web page has a corresponding Java class:

- Encapsulates page elements (locators)
- Provides methods for page interactions
- Hides implementation details from tests

## Running Tests

### Run all tests

```bash
mvn test
```

### Run specific test suite

```bash
mvn test -DsuiteXmlFile=src/test/resources/smoke-suite.xml
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Run specific test class

```bash
mvn -Dtest=EpsHomePageTest test
mvn -Dtest=LogoTestRefactored test
```

### Run specific test method

```bash
mvn -Dtest=EpsHomePageTest#testLogoIsPresent test
```

### Run tests by group

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
mvn test -Dgroups=homepage
```

### Run with custom browser

```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
```

### Run with visible browser (non-headless)

```bash
mvn test -Dheadless=false
```

### Run with custom URL

```bash
mvn test -DbaseUrl=https://www.wikipedia.org
```

### Combine parameters

```bash
mvn test -Dbrowser=chrome -Dheadless=false -Dgroups=smoke
mvn -Dtest=EpsHomePageTest -Dbrowser=chrome -Dheadless=false test
```

## Test Groups

Tests are organized by groups:

- **smoke**: Critical tests that should always pass
- **regression**: Full test coverage
- **homepage**: Home page specific tests
- **logo**: Logo verification tests

## Adding New Tests

### 1. Create a new test class

```java
package upo.eps.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import upo.eps.base.BaseTest;

public class MyNewTest extends BaseTest {
    
    @Test(groups = {"smoke"})
    public void testSomething() {
        navigateToBaseUrl();
        // Your test logic here
        Assert.assertTrue(true);
    }
}
```

### 2. Create a new page object

```java
package upo.eps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import upo.eps.base.BasePage;

public class MyPage extends BasePage {
    
    private final By elementLocator = By.id("my-element");
    
    public MyPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isElementDisplayed() {
        return isElementDisplayed(elementLocator);
    }
}
```

### 3. Use page object in test

```java
@Test
public void testMyPage() {
    navigateToBaseUrl();
    MyPage myPage = new MyPage(driver);
    Assert.assertTrue(myPage.isElementDisplayed());
}
```

## Configuration

### TestNG Suite (testng.xml)

Configure default parameters:

```xml
<parameter name="browser" value="firefox"/>
<parameter name="headless" value="true"/>
<parameter name="baseUrl" value="https://example.com"/>
```

### System Properties (command line)

Override parameters at runtime:

```bash
-Dbrowser=chrome
-Dheadless=false
-DbaseUrl=https://example.com
```

## Best Practices

1. **Extend BaseTest**: All test classes should extend `BaseTest`
2. **Extend BasePage**: All page objects should extend `BasePage`
3. **Use explicit waits**: Provided by `BasePage` methods
4. **Independent tests**: Each test should run independently
5. **Use groups**: Organize tests by feature/priority
6. **Page Object Model**: Keep page logic separate from test logic
7. **Meaningful assertions**: Include descriptive messages

## Dependencies

- Selenium WebDriver 4.38.0
- TestNG 7.8.0
- WebDriverManager 5.4.1
- Java 17

## Notes

- Original `LogoTest.java` is kept for reference
- `LogoTestRefactored.java` shows migration to BaseTest pattern
- `EpsHomePageTest.java` demonstrates full POM implementation

TEST
