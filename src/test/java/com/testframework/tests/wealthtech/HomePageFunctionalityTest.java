package com.testframework.tests.wealthtech;

import com.testframework.base.BaseTest;
import com.testframework.pages.wealthtech.WealthTechHomePage;
import com.testframework.utils.TestConfig;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("WealthTech Pro - Homepage")
@Feature("Homepage Functionality")
public class HomePageFunctionalityTest extends BaseTest {

    private WealthTechHomePage homePage;

    @BeforeMethod
    @Override
    public void createBrowserContextAndPage() {
        super.createBrowserContextAndPage();
        navigateToPage(TestConfig.HOME_PAGE);
        homePage = new WealthTechHomePage(page);
    }

    @Test(description = "Homepage hero and title load correctly")
    @Description("Verify the homepage hero title is present and non-empty")
    @Severity(SeverityLevel.CRITICAL)
    public void testHomepageHeroTitleIsDisplayed() {
        String title = homePage.getHeroTitle();
        Assert.assertNotNull(title, "Hero title should not be null");
        Assert.assertFalse(title.trim().isEmpty(), "Hero title should not be empty");
    }

    @Test(description = "Open Accounts CTA navigates to Accounts page")
    @Description("Clicking the Open Account CTA should navigate to the accounts page")
    @Severity(SeverityLevel.CRITICAL)
    public void testOpenAccountCTANavigates() {
        homePage.clickOpenAccountButton();
        // Wait a short moment for navigation to complete
        page.waitForLoadState();
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains(TestConfig.ACCOUNTS_PAGE),
            "After clicking Open Account CTA the URL should contain accounts page path: " + currentUrl);
    }
}
