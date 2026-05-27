package com.testframework.base;

import com.microsoft.playwright.*;
import com.testframework.utils.TestConfig;
import org.testng.annotations.*;
import io.qameta.allure.Allure;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Base test class for WealthTech Pro automation framework.
 * Handles browser lifecycle management, page context creation, and test cleanup.
 * All test classes should extend this class to inherit common functionality.
 */
public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;

    /**
     * Launches the browser before all tests in the class.
     * Browser type is determined by TestConfig or system property.
     */
    @BeforeClass(alwaysRun = true)
    public void launchBrowser() {
        playwright = Playwright.create();
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(TestConfig.HEADLESS_MODE)
                .setTimeout(TestConfig.DEFAULT_TIMEOUT);
        
        browser = getBrowserType().launch(launchOptions);
        
        System.out.println("Browser launched: " + TestConfig.BROWSER_TYPE);
        System.out.println("Headless mode: " + TestConfig.HEADLESS_MODE);
        System.out.println("Base URL: " + TestConfig.getBaseUrl());
    }

    /**
     * Creates a new browser context and page before each test method.
     * Ensures test isolation by providing a fresh context for each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void createBrowserContextAndPage() {
        // Create isolated browser context with proper viewport
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(TestConfig.VIEWPORT_WIDTH, TestConfig.VIEWPORT_HEIGHT)
                .setLocale("en-US")
                .setTimezoneId("America/New_York"));
        
        // Enable tracing for debugging failed tests
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        
        // Create new page
        page = browserContext.newPage();
        
        // Set default timeout for all operations
        page.setDefaultTimeout(TestConfig.DEFAULT_TIMEOUT);
    }

    /**
     * Closes the browser context after each test method.
     * Captures screenshots and traces for failed tests.
     * 
     * @param result TestNG result object containing test execution details
     */
    @AfterMethod(alwaysRun = true)
    public void closeBrowserContext(org.testng.ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        // Capture screenshot on test failure
        if (!result.isSuccess() && page != null) {
            captureScreenshotOnFailure(testName);
        }
        
        // Save trace for debugging
        saveTrace(testName);
        
        // Close browser context
        if (browserContext != null) {
            browserContext.close();
        }
    }

    /**
     * Closes the browser and Playwright instance after all tests in the class.
     */
    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        System.out.println("Browser closed successfully");
    }

    /**
     * Gets the appropriate browser type based on configuration.
     * 
     * @return BrowserType for the configured browser
     */
    private BrowserType getBrowserType() {
        switch (TestConfig.BROWSER_TYPE.toLowerCase()) {
            case "firefox":
                return playwright.firefox();
            case "webkit":
            case "safari":
                return playwright.webkit();
            case "chromium":
            case "chrome":
            default:
                return playwright.chromium();
        }
    }

    /**
     * Captures and attaches a screenshot to the Allure report on test failure.
     * 
     * @param testName Name of the failed test
     */
    private void captureScreenshotOnFailure(String testName) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true));
            Allure.addAttachment(testName + " - Failure Screenshot", "image/png", 
                new java.io.ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Saves Playwright trace file for test debugging.
     * 
     * @param testName Name of the test
     */
    private void saveTrace(String testName) {
        try {
            Path tracesDir = Paths.get("target/traces");
            if (!Files.exists(tracesDir)) {
                Files.createDirectories(tracesDir);
            }
            
            Path tracePath = tracesDir.resolve(testName + ".zip");
            browserContext.tracing().stop(new Tracing.StopOptions()
                    .setPath(tracePath));
        } catch (Exception e) {
            System.err.println("Failed to save trace: " + e.getMessage());
        }
    }

    /**
     * Navigates to a specific page URL.
     * 
     * @param pageUrl Page URL to navigate to
     */
    protected void navigateToPage(String pageUrl) {
        page.navigate(TestConfig.getPageUrl(pageUrl));
        page.waitForLoadState();
    }

    /**
     * Gets the base URL from configuration.
     * 
     * @return Base URL for the current environment
     */
    protected String getBaseUrl() {
        return TestConfig.getBaseUrl();
    }
}

