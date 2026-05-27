package com.testframework.pages.wealthtech;

import com.microsoft.playwright.Page;
import com.testframework.base.BasePage;

/**
 * Page Object for WealthTech Pro Homepage.
 * Contains elements and actions for the main landing page.
 */
@SuppressWarnings("unused")
public class WealthTechHomePage extends BasePage {
    
    // Locators - Header & Navigation
    private static final String LOGO = ".logo";
    private static final String NAV_HOME_LINK = "nav a[href='index.html']";
    private static final String NAV_ACCOUNTS_LINK = "nav a[href='accounts.html']";
    private static final String NAV_TRADING_LINK = "nav a[href='trading.html']";
    private static final String LOGIN_BUTTON = ".btn-login";
    
    // Locators - Hero Section
    private static final String HERO_TITLE = ".hero h1";
    private static final String HERO_SUBTITLE = ".hero p";
    private static final String CTA_OPEN_ACCOUNT = ".hero a[href='accounts.html'].btn-primary";
    private static final String CTA_EXPLORE_TRADING = "a[href='trading.html'].btn-secondary";
    
    // Locators - Stats Section
    private static final String STAT_ASSETS = ".stat-item:nth-child(1) h3";
    private static final String STAT_CLIENTS = ".stat-item:nth-child(2) h3";
    private static final String STAT_EXPERIENCE = ".stat-item:nth-child(3) h3";
    private static final String STAT_RATING = ".stat-item:nth-child(4) h3";
    
    // Locators - Retirement Calculator
    private static final String CALC_CURRENT_AGE = "#current-age";
    private static final String CALC_RETIREMENT_AGE = "#retirement-age";
    private static final String CALC_CURRENT_SAVINGS = "#current-savings";
    private static final String CALC_MONTHLY_CONTRIBUTION = "#monthly-contribution";
    private static final String CALC_RETURN_RATE = "#return-rate";
    private static final String CALC_SUBMIT_BUTTON = "#calc-form button[type='submit']";
    private static final String CALC_RESULT = "#calc-result";
    private static final String CALC_RESULT_AMOUNT = "#result-amount";
    private static final String CALC_RESULT_DETAILS = "#result-details";
    
    // Locators - Account Cards
    private static final String BROKERAGE_ACCOUNT_CARD = ".account-card:nth-child(1)";
    private static final String TRADITIONAL_IRA_CARD = ".account-card:nth-child(2)";
    private static final String ROTH_IRA_CARD = ".account-card:nth-child(3)";
    private static final String MANAGED_PORTFOLIO_CARD = ".account-card:nth-child(4)";
    
    public WealthTechHomePage(Page page) {
        super(page);
    }

    /**
     * Navigates to the Accounts page via CTA button.
     */
    public void clickOpenAccountButton() {
        clickElement(CTA_OPEN_ACCOUNT);
        waitForPageLoad();
    }

    /**
     * Navigates to the Trading page via CTA button.
     */
    public void clickExploreTradingButton() {
        clickElement(CTA_EXPLORE_TRADING);
        waitForPageLoad();
    }

    /**
     * Clicks the login button in navigation.
     */
    public void clickLoginButton() {
        clickElement(LOGIN_BUTTON);
    }

    /**
     * Gets the hero section title text.
     * 
     * @return Hero title text
     */
    public String getHeroTitle() {
        return getElementText(HERO_TITLE);
    }

    /**
     * Gets the hero section subtitle text.
     * 
     * @return Hero subtitle text
     */
    public String getHeroSubtitle() {
        return getElementText(HERO_SUBTITLE);
    }

    /**
     * Verifies if the logo is displayed.
     * 
     * @return true if logo is visible
     */
    public boolean isLogoDisplayed() {
        return isElementVisible(LOGO);
    }

    /**
     * Gets the company assets under management stat.
     * 
     * @return Assets stat text (e.g., "$4.2T")
     */
    public String getAssetsUnderManagement() {
        return getElementText(STAT_ASSETS);
    }

    /**
     * Gets the client accounts stat.
     * 
     * @return Client accounts stat text
     */
    public String getClientAccounts() {
        return getElementText(STAT_CLIENTS);
    }

    /**
     * Gets the years of experience stat.
     * 
     * @return Years stat text
     */
    public String getYearsOfExperience() {
        return getElementText(STAT_EXPERIENCE);
    }

    /**
     * Gets the customer rating stat.
     * 
     * @return Rating stat text
     */
    public String getCustomerRating() {
        return getElementText(STAT_RATING);
    }

    /**
     * Fills out the retirement calculator with provided values.
     * 
     * @param currentAge Current age
     * @param retirementAge Retirement age
     * @param currentSavings Current savings amount
     * @param monthlyContribution Monthly contribution amount
     * @param returnRate Expected annual return percentage
     */
    public void fillRetirementCalculator(String currentAge, String retirementAge, 
                                         String currentSavings, String monthlyContribution, 
                                         String returnRate) {
        fillInputField(CALC_CURRENT_AGE, currentAge);
        fillInputField(CALC_RETIREMENT_AGE, retirementAge);
        fillInputField(CALC_CURRENT_SAVINGS, currentSavings);
        fillInputField(CALC_MONTHLY_CONTRIBUTION, monthlyContribution);
        fillInputField(CALC_RETURN_RATE, returnRate);
    }

    /**
     * Submits the retirement calculator form.
     */
    public void submitRetirementCalculator() {
        clickElement(CALC_SUBMIT_BUTTON);
    }

    /**
     * Checks if the calculator result is displayed.
     * 
     * @return true if result is visible
     */
    public boolean isCalculatorResultDisplayed() {
        return isElementVisible(CALC_RESULT);
    }

    /**
     * Gets the calculated retirement savings amount.
     * 
     * @return Calculated amount (e.g., "$1,234,567")
     */
    public String getCalculatedRetirementAmount() {
        waitForElementVisible(CALC_RESULT);
        return getElementText(CALC_RESULT_AMOUNT);
    }

    /**
     * Gets the retirement calculation details text.
     * 
     * @return Calculation details
     */
    public String getCalculationDetails() {
        return getElementText(CALC_RESULT_DETAILS);
    }

    /**
     * Navigates to Accounts page via navigation link.
     */
    public void navigateToAccounts() {
        clickElement(NAV_ACCOUNTS_LINK);
        waitForPageLoad();
    }

    /**
     * Navigates to Trading page via navigation link.
     */
    public void navigateToTrading() {
        clickElement(NAV_TRADING_LINK);
        waitForPageLoad();
    }

    /**
     * Checks if a specific account card is displayed.
     * 
     * @param accountType Type of account (brokerage, traditional-ira, roth-ira, managed)
     * @return true if account card is visible
     */
    public boolean isAccountCardDisplayed(String accountType) {
        String selector;
        switch(accountType.toLowerCase()) {
            case "brokerage":
                selector = BROKERAGE_ACCOUNT_CARD;
                break;
            case "traditional-ira":
                selector = TRADITIONAL_IRA_CARD;
                break;
            case "roth-ira":
                selector = ROTH_IRA_CARD;
                break;
            case "managed":
                selector = MANAGED_PORTFOLIO_CARD;
                break;
            default:
                return false;
        }
        return isElementVisible(selector);
    }
}
