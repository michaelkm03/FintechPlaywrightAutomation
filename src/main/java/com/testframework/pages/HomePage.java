package com.testframework.pages;

import com.microsoft.playwright.Page;
import com.testframework.base.BasePage;

public class HomePage extends BasePage {
    
    // Locators
    private final String headingLocator = "h1";
    private final String linkLocator = "a[href*='more']";
    private final String paragraphLocator = "p";

    public HomePage(Page page) {
        super(page);
    }

    // Page actions
    public String getHeading() {
        waitForSelector(headingLocator);
        return getText(headingLocator);
    }

    public boolean isHeadingDisplayed() {
        return isVisible(headingLocator);
    }

    public void clickMoreInformationLink() {
        click(linkLocator);
    }

    public String getParagraphText() {
        return getText(paragraphLocator);
    }

    public String getPageTitle() {
        return super.getPageTitle();
    }
}
