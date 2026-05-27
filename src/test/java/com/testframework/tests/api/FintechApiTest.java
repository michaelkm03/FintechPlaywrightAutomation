package com.testframework.tests.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.testframework.utils.TestConfig;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("WealthTech Pro - API Layer")
@Feature("HTTP Endpoint Validation")
public class FintechApiTest {

    private Playwright playwright;
    private APIRequestContext apiContext;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        apiContext = playwright.request().newContext(
            new APIRequest.NewContextOptions().setBaseURL(TestConfig.getBaseUrl())
        );
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (apiContext != null) apiContext.dispose();
        if (playwright != null) playwright.close();
    }

    @Test(description = "Homepage returns HTTP 200")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Service Health")
    public void testHomepageReturns200() {
        APIResponse response = apiContext.get("/");
        Assert.assertEquals(response.status(), 200, "Homepage should return HTTP 200");
        response.dispose();
    }

    @Test(description = "Account opening page returns HTTP 200")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Service Health")
    public void testAccountsPageReturns200() {
        APIResponse response = apiContext.get("/accounts.html");
        Assert.assertEquals(response.status(), 200, "Accounts page should return HTTP 200");
        response.dispose();
    }

    @Test(description = "Trading platform page returns HTTP 200")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Service Health")
    public void testTradingPageReturns200() {
        APIResponse response = apiContext.get("/trading.html");
        Assert.assertEquals(response.status(), 200, "Trading page should return HTTP 200");
        response.dispose();
    }

    @Test(description = "Homepage response contains expected branding")
    @Severity(SeverityLevel.NORMAL)
    @Story("Content Validation")
    public void testHomepageContainsBranding() {
        APIResponse response = apiContext.get("/");
        String body = response.text();
        Assert.assertTrue(body.contains("WealthTech Pro"),
            "Homepage body should include 'WealthTech Pro' branding");
        response.dispose();
    }

    @Test(description = "Accounts page includes application form markup")
    @Severity(SeverityLevel.NORMAL)
    @Story("Content Validation")
    public void testAccountsPageContainsForm() {
        APIResponse response = apiContext.get("/accounts.html");
        String body = response.text();
        Assert.assertTrue(body.contains("form"),
            "Accounts page should contain an application form element");
        response.dispose();
    }

    @Test(description = "Trading page includes order entry markup")
    @Severity(SeverityLevel.NORMAL)
    @Story("Content Validation")
    public void testTradingPageContainsOrderEntry() {
        APIResponse response = apiContext.get("/trading.html");
        String body = response.text();
        Assert.assertTrue(body.contains("order") || body.contains("trade"),
            "Trading page should contain order entry elements");
        response.dispose();
    }

    @Test(description = "Homepage response has HTML content-type header")
    @Severity(SeverityLevel.NORMAL)
    @Story("Content Validation")
    public void testHomepageHasHtmlContentType() {
        APIResponse response = apiContext.get("/");
        String contentType = response.headers().get("content-type");
        Assert.assertNotNull(contentType, "Response should include a content-type header");
        Assert.assertTrue(contentType.contains("text/html"),
            "Homepage should return text/html content-type, got: " + contentType);
        response.dispose();
    }

    @Test(description = "NEGATIVE: Non-existent page returns a non-200 status")
    @Severity(SeverityLevel.MINOR)
    @Story("Error Handling")
    public void testNonExistentPageReturnsError() {
        APIResponse response = apiContext.get("/nonexistent-page.html");
        Assert.assertNotEquals(response.status(), 200,
            "Non-existent page should not return HTTP 200");
        response.dispose();
    }
}
