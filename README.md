# WealthTech Pro - Test Automation Framework

![CI](https://github.com/michaelkm03/FintechPlaywrightAutomation/actions/workflows/ci.yml/badge.svg)

A comprehensive Playwright + TestNG automation framework for testing the WealthTech Pro financial technology platform. Built following industry best practices and Page Object Model design pattern.

## 🎯 Project Overview

This framework provides end-to-end test automation for WealthTech Pro, a simulated financial services platform similar to Fidelity or E*TRADE. The test suite covers:

- **Homepage**: Landing page, retirement calculator, company statistics
- **Account Opening**: Multi-step account application workflow
- **Trading Platform**: Stock quotes, watchlist, order execution
- **API Layer**: HTTP endpoint validation using Playwright's `APIRequestContext`

## 📋 Prerequisites

- **Java JDK 11+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Python 3.x** (for local web server)
- **IDE** (IntelliJ IDEA, VS Code, or Eclipse recommended)

## 🚀 Quick Start

### 1. Clone/Download the Project

```bash
cd playwright-testng-framework
```

### 2. Install Dependencies

```bash
# Install Maven dependencies
mvn clean install

# Install Playwright browsers
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### 3. Start Local Web Server

**On Windows:**
```bash
start-server.bat
```

**On Mac/Linux:**
```bash
chmod +x start-server.sh
./start-server.sh
```

The WealthTech Pro website will be available at `http://localhost:8080`

### 4. Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=HomePageFunctionalityTest

# Run in different browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true
```

## 📁 Project Structure

```
playwright-testng-framework/
├── fintech-site/                          # WealthTech Pro website files
│   ├── index.html                         # Homepage
│   ├── accounts.html                      # Account opening page
│   └── trading.html                       # Trading platform page
│
├── src/
│   ├── main/java/com/testframework/
│   │   ├── base/
│   │   │   ├── BaseTest.java             # Base test class with browser lifecycle
│   │   │   └── BasePage.java             # Base page with common actions
│   │   │
│   │   ├── pages/wealthtech/             # Page Objects (POM pattern)
│   │   │   ├── WealthTechHomePage.java
│   │   │   ├── AccountOpeningPage.java
│   │   │   └── TradingPlatformPage.java
│   │   │
│   │   └── utils/
│   │       ├── TestConfig.java            # Centralized configuration
│   │       └── TestUtils.java             # Utility methods
│   │
│   └── test/java/com/testframework/tests/
│       ├── wealthtech/
│       │   ├── HomePageFunctionalityTest.java
│       │   ├── AccountOpeningWorkflowTest.java
│       │   └── TradingPlatformFunctionalityTest.java
│       └── api/
│           └── FintechApiTest.java        # HTTP API / endpoint validation tests
│
├── .github/workflows/ci.yml              # GitHub Actions CI/CD pipeline
├── testng.xml                             # TestNG suite configuration
├── pom.xml                                # Maven dependencies
├── start-server.bat                       # Windows server launcher
├── start-server.sh                        # Mac/Linux server launcher
└── README.md
```

## 🧪 Test Scenarios

### Homepage Tests (2 tests)
- ✅ Homepage hero title load verification
- ✅ Account opening CTA navigation

### Account Opening Tests (12 tests)
- ✅ Complete brokerage account opening
- ✅ Traditional IRA account opening
- ✅ Roth IRA account opening
- ✅ Joint account opening
- ✅ SSN auto-formatting validation
- ✅ Phone number auto-formatting
- ✅ Progress indicator updates
- ✅ Continue button conditional enabling
- ✅ Form validation - agreements required
- ✅ Form validation - email format
- ✅ Form validation - required fields
- ✅ Back button navigation

### Trading Platform Tests (14 tests)
- ✅ Trading dashboard load verification
- ✅ Watchlist display and stock selection
- ✅ Market buy orders
- ✅ Limit buy orders
- ✅ Market sell orders
- ✅ Order summary calculations
- ✅ Portfolio positions display
- ✅ Buy/Sell tab switching
- ✅ Market status display
- ✅ Order validation - share quantity
- ✅ Order validation - symbol handling
- ✅ Limit price field conditional display
- ✅ Time-in-force options (DAY and GTC)

### API Endpoint Tests (8 tests)
- ✅ Homepage returns HTTP 200
- ✅ Accounts page returns HTTP 200
- ✅ Trading page returns HTTP 200
- ✅ Homepage response contains expected branding
- ✅ Accounts page includes form markup
- ✅ Trading page includes order entry markup
- ✅ Homepage returns HTML content-type header
- ✅ NEGATIVE: Non-existent page returns error (non-200)

## 🎯 Key Features

### Page Object Model (POM)
- Clean separation between test logic and page interactions
- Reusable page methods with meaningful names
- Centralized element locators

### CI/CD Integration
- **GitHub Actions** pipeline runs on every push and pull request
- Automatically starts the Python web server before tests execute
- Uploads Allure results, Surefire reports, screenshots, and traces as artifacts
- Tests run in headless mode on `ubuntu-latest`
- Failed-test artifacts (screenshots + Playwright traces) uploaded automatically

### Best Practices Implementation
- **Explicit waits** instead of Thread.sleep()
- **Proper exception handling** with try-catch blocks
- **Comprehensive JavaDoc** documentation
- **Descriptive naming conventions** following financial industry standards
- **Allure reporting** integration for detailed test reports
- **Screenshot capture** on test failures
- **Playwright trace files** for debugging
- **API testing** via Playwright's `APIRequestContext` (status codes, headers, body content)

### Test Data Management
- Centralized configuration in `TestConfig.java`
- Environment-specific URLs (local/production)
- Test data constants for account opening and trading
- Unique email generation to avoid conflicts

### Reporting & Debugging
- **Allure Reports**: Rich HTML reports with screenshots and traces
- **TestNG Reports**: Built-in HTML reports
- **Console Logging**: Real-time test execution feedback
- **Playwright Traces**: Detailed execution traces for failed tests

## 🔧 Configuration

### Environment Configuration

Edit `src/main/java/com/testframework/utils/TestConfig.java`:

```java
// Switch between local and production
public static final String LOCAL_BASE_URL = "http://localhost:8080";
public static final String PRODUCTION_BASE_URL = "https://wealthtechpro.com";

// Set default environment
private static final String ENVIRONMENT = System.getProperty("env", "local");
```

### Browser Configuration

```bash
# Run in Chrome (default)
mvn test

# Run in Firefox
mvn test -Dbrowser=firefox

# Run in Safari/WebKit
mvn test -Dbrowser=webkit

# Run in headless mode
mvn test -Dheadless=true
```

### Parallel Execution

Edit `testng.xml` to control parallelization:

```xml
<suite name="Test Suite" parallel="tests" thread-count="2">
```

## 📊 Viewing Test Reports

### Allure Reports (Recommended)

```bash
# Generate report
mvn allure:report

# View report in browser
mvn allure:serve
```

### TestNG Reports

After running tests, open:
```
target/surefire-reports/index.html
```

### Playwright Traces

Failed test traces are saved in `target/traces/`

Upload to https://trace.playwright.dev/ for interactive debugging

## 🎓 Framework Design Principles

### 1. Page Object Model
Each page has a dedicated class with:
- Private locator constants (e.g., `SUBMIT_BUTTON`)
- Public action methods (e.g., `submitApplication()`)
- Public verification methods (e.g., `isApplicationSuccessful()`)

### 2. Test Independence
- Each test can run independently
- Fresh browser context per test method
- No shared state between tests

### 3. Maintainability
- Centralized configuration
- Reusable base classes
- Clear separation of concerns
- Comprehensive documentation

### 4. Financial Industry Standards
- Method names reflect business operations (e.g., `placeBuyMarketOrder`)
- Test names describe user workflows (e.g., `testOpenBrokerageAccountSuccessfully`)
- Validation messages use financial terminology

## 💡 Common Use Cases

### Run Specific Test Suite

```bash
# Run only homepage tests
mvn test -Dtest=HomePageFunctionalityTest

# Run only trading tests
mvn test -Dtest=TradingPlatformFunctionalityTest

# Run multiple test classes
mvn test -Dtest=HomePageFunctionalityTest,AccountOpeningWorkflowTest
```

### Debug a Failing Test

```bash
# Run in headed mode with slowmo
mvn test -Dheadless=false -Dtest=AccountOpeningWorkflowTest

# Check trace file in target/traces/
# Upload to https://trace.playwright.dev/
```

### Test Against Different Environment

```bash
# Test against production (when available)
mvn test -Denv=production
```

## 🐛 Troubleshooting

### Server Won't Start
**Issue**: Port 8080 already in use  
**Solution**: Kill process on port 8080 or change port in start-server script

### Tests Can't Find Website
**Issue**: Connection refused to localhost:8080  
**Solution**: Ensure local server is running (see Quick Start #3)

### Browser Not Launching
**Issue**: Playwright browsers not installed  
**Solution**: Run `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"`

### Maven Not Found
**Issue**: 'mvn' command not recognized  
**Solution**: Install Maven and add to PATH, or use IDE's built-in Maven

## 📚 Additional Resources

- [Playwright Java Docs](https://playwright.dev/java/)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Page Object Model Pattern](https://playwright.dev/java/docs/pom)
- [Maven Documentation](https://maven.apache.org/guides/)

## 🤝 Contributing

When adding new tests:
1. Follow existing naming conventions
2. Add proper JavaDoc comments
3. Use financial industry terminology
4. Include both positive and negative test cases
5. Update README if adding new features

## 📝 Test Naming Conventions

### Page Object Methods
- Use present tense verbs: `fillPersonalInformation()`, `submitApplication()`
- Include business context: `placeBuyMarketOrder()` vs `clickButton()`
- Return types should be clear: `getAccountValue()` returns String

### Test Methods
- Start with "test": `testOpenBrokerageAccountSuccessfully()`
- Describe user workflow: `testRetirementCalculatorWithValidInputs()`
- Negative tests: `testCannotSubmitWithoutAgreements()`

### Locator Constants
- Use UPPER_SNAKE_CASE: `SUBMIT_APPLICATION_BUTTON`
- Describe element clearly: `FIRST_NAME_INPUT` vs `INPUT1`

## 🎉 Success Metrics

Framework provides:
- **28 comprehensive test cases** covering critical user workflows
- **100% Page Object Model** implementation
- **Detailed reporting** with screenshots and traces
- **Production-ready** test automation framework
- **Financial industry standards** compliance

---

**Happy Testing!** 🚀

For questions or issues, please check the troubleshooting section or review the test execution logs.
