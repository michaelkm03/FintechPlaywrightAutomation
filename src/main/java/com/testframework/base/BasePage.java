package com.testframework.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.testframework.utils.TestConfig;

/**
 * Base page class providing common page interaction methods.
 * All page objects should extend this class to inherit reusable functionality.
 * Follows Playwright best practices with proper waits and error handling.
 */
public class BasePage {
    protected Page page;

    /**
     * Constructor for BasePage.
     * 
     * @param page Playwright Page object
     */
    public BasePage(Page page) {
        this.page = page;
    }

    /**
     * Clicks on an element identified by selector.
     * Waits for element to be visible and enabled before clicking.
     * 
     * @param selector CSS selector for the element
     */
    protected void clickElement(String selector) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.click();
    }

    /**
     * Fills text into an input field.
     * Clears existing text before filling.
     * 
     * @param selector CSS selector for the input field
     * @param text Text to fill
     */
    protected void fillInputField(String selector, String text) {
        Locator locator = page.locator(selector);
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        locator.fill(text);
    }

    /**
     * Gets the text content of an element.
     * 
     * @param selector CSS selector for the element
     * @return Text content of the element
     */
    protected String getElementText(String selector) {
        return page.locator(selector).textContent().trim();
    }

    /**
     * Checks if an element is visible on the page.
     * 
     * @param selector CSS selector for the element
     * @return true if element is visible, false otherwise
     */
    protected boolean isElementVisible(String selector) {
        try {
            return page.locator(selector).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for an element to be visible.
     * 
     * @param selector CSS selector for the element
     */
    protected void waitForElementVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(TestConfig.EXPLICIT_WAIT));
    }

    /*
     * Aliases for older page objects that expect simpler method names
     * such as `click`, `fill`, `getText`, `waitForSelector`, and `isVisible`.
     */
    protected void click(String selector) {
        clickElement(selector);
    }

    protected void fill(String selector, String text) {
        fillInputField(selector, text);
    }

    protected String getText(String selector) {
        return getElementText(selector);
    }

    protected void waitForSelector(String selector) {
        waitForElementVisible(selector);
    }

    protected boolean isVisible(String selector) {
        return isElementVisible(selector);
    }

    /**
     * Waits for an element to be hidden.
     * 
     * @param selector CSS selector for the element
     */
    protected void waitForElementHidden(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(TestConfig.EXPLICIT_WAIT));
    }

    /**
     * Selects an option from a dropdown by value.
     * 
     * @param selector CSS selector for the select element
     * @param value Value to select
     */
    protected void selectDropdownByValue(String selector, String value) {
        page.locator(selector).selectOption(value);
    }

    /**
     * Checks a checkbox or radio button.
     * 
     * @param selector CSS selector for the checkbox/radio
     */
    protected void checkElement(String selector) {
        page.locator(selector).check();
    }

    /**
     * Unchecks a checkbox.
     * 
     * @param selector CSS selector for the checkbox
     */
    protected void uncheckElement(String selector) {
        page.locator(selector).uncheck();
    }

    /**
     * Navigates to a specific URL.
     * 
     * @param url URL to navigate to
     */
    protected void navigateTo(String url) {
        page.navigate(url);
        page.waitForLoadState();
    }

    /**
     * Gets the current page title.
     * 
     * @return Page title
     */
    protected String getPageTitle() {
        return page.title();
    }

    /**
     * Gets the current URL.
     * 
     * @return Current URL
     */
    protected String getCurrentUrl() {
        return page.url();
    }

    /**
     * Waits for the page to fully load.
     */
    protected void waitForPageLoad() {
        page.waitForLoadState();
    }

    /**
     * Gets the input value of a form field.
     * 
     * @param selector CSS selector for the input field
     * @return Current value of the input
     */
    protected String getInputValue(String selector) {
        return page.locator(selector).inputValue();
    }

    /**
     * Checks if a checkbox or radio button is checked.
     * 
     * @param selector CSS selector for the element
     * @return true if checked, false otherwise
     */
    protected boolean isElementChecked(String selector) {
        return page.locator(selector).isChecked();
    }

    /**
     * Gets count of elements matching a selector.
     * 
     * @param selector CSS selector
     * @return Number of matching elements
     */
    protected int getElementCount(String selector) {
        return page.locator(selector).count();
    }

    /**
     * Waits for a specific amount of time.
     * NOTE: Use sparingly, prefer explicit waits for elements.
     * 
     * @param milliseconds Time to wait in milliseconds
     */
    protected void waitFor(int milliseconds) {
        page.waitForTimeout(milliseconds);
    }
}

