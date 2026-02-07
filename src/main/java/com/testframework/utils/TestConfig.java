package com.testframework.utils;

/**
 * Central configuration class for WealthTech Pro test automation framework.
 * Contains all environment-specific settings and test data.
 */
public class TestConfig {
    
    // Environment URLs
    public static final String LOCAL_BASE_URL = "http://localhost:8080";
    public static final String PRODUCTION_BASE_URL = "https://wealthtechpro.com";
    
    // Current environment (can be overridden via system property)
    private static final String ENVIRONMENT = System.getProperty("env", "local");
    
    // Page URLs
    public static final String HOME_PAGE = "/index.html";
    public static final String ACCOUNTS_PAGE = "/accounts.html";
    public static final String TRADING_PAGE = "/trading.html";
    
    // Timeouts (in milliseconds)
    public static final int DEFAULT_TIMEOUT = 30000;
    public static final int EXPLICIT_WAIT = 10000;
    public static final int POLLING_INTERVAL = 500;
    
    // Test Data - Account Opening
    public static class AccountData {
        public static final String FIRST_NAME = "John";
        public static final String LAST_NAME = "Doe";
        public static final String EMAIL = "john.doe@wealthtech.test";
        public static final String PHONE = "(555) 123-4567";
        public static final String SSN = "123-45-6789";
        public static final String STREET = "123 Investment Lane";
        public static final String CITY = "New York";
        public static final String STATE = "NY";
        public static final String ZIP = "10001";
        public static final String EMPLOYMENT_STATUS = "employed";
        public static final String INCOME_RANGE = "100k-250k";
        public static final String NET_WORTH_RANGE = "250k-1m";
        public static final String INVESTMENT_EXPERIENCE = "good";
    }
    
    // Test Data - Trading
    public static class TradingData {
        public static final String DEFAULT_SYMBOL = "AAPL";
        public static final int DEFAULT_SHARES = 10;
        public static final String ORDER_TYPE_MARKET = "market";
        public static final String ORDER_TYPE_LIMIT = "limit";
        public static final String TIME_IN_FORCE_DAY = "day";
        public static final String TIME_IN_FORCE_GTC = "gtc";
    }
    
    // Browser Configuration
    public static final String BROWSER_TYPE = System.getProperty("browser", "chromium");
    public static final boolean HEADLESS_MODE = Boolean.parseBoolean(System.getProperty("headless", "false"));
    public static final int VIEWPORT_WIDTH = 1920;
    public static final int VIEWPORT_HEIGHT = 1080;
    
    /**
     * Get the base URL based on the current environment
     * @return Base URL for the current environment
     */
    public static String getBaseUrl() {
        switch (ENVIRONMENT.toLowerCase()) {
            case "prod":
            case "production":
                return PRODUCTION_BASE_URL;
            case "local":
            default:
                return LOCAL_BASE_URL;
        }
    }
    
    /**
     * Get the full URL for a specific page
     * @param page Page path (e.g., HOME_PAGE, ACCOUNTS_PAGE)
     * @return Complete URL
     */
    public static String getPageUrl(String page) {
        return getBaseUrl() + page;
    }
    
    /**
     * Generate a unique email for testing to avoid duplicates
     * @param prefix Email prefix
     * @return Unique email address
     */
    public static String generateUniqueEmail(String prefix) {
        return prefix + "_" + System.currentTimeMillis() + "@wealthtech.test";
    }
}
