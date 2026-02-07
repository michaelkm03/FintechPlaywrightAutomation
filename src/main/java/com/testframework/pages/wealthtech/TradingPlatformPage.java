package com.testframework.pages.wealthtech;

import com.microsoft.playwright.Page;
import com.testframework.base.BasePage;

/**
 * Page Object for WealthTech Pro Trading Platform.
 * Handles stock quotes, watchlist, and order execution.
 */
@SuppressWarnings("unused")
public class TradingPlatformPage extends BasePage {
    
    // Locators - Account Information Bar
    private static final String ACCOUNT_VALUE = "#account-value";
    private static final String BUYING_POWER = ".account-info > div:nth-child(2) .account-value";
    private static final String DAILY_PL = ".account-info > div:nth-child(3) .change";
    private static final String MARKET_STATUS = ".top-bar-content > div:nth-child(2)";
    
    // Locators - Watchlist
    private static final String WATCHLIST_SECTION = ".watchlist";
    private static final String WATCHLIST_STOCKS = ".stock-item";
    private static final String ADD_SYMBOL_BUTTON = ".add-symbol";
    
    // Locators - Chart Section
    private static final String CHART_SYMBOL = "#chart-symbol";
    private static final String CHART_COMPANY_NAME = "#chart-company";
    private static final String CHART_CURRENT_PRICE = "#chart-price";
    private static final String CHART_PRICE_CHANGE = "#chart-change";
    
    // Locators - Order Panel
    private static final String BUY_TAB = ".tab:nth-child(1)";
    private static final String SELL_TAB = ".tab:nth-child(2)";
    private static final String ORDER_SYMBOL_INPUT = "#order-symbol";
    private static final String ORDER_TYPE_DROPDOWN = "#order-type";
    private static final String LIMIT_PRICE_INPUT = "#limit-price";
    private static final String SHARES_INPUT = "#shares";
    private static final String TIME_IN_FORCE_DROPDOWN = "#time-in-force";
    
    // Locators - Order Summary
    private static final String SUMMARY_SHARES = "#summary-shares";
    private static final String SUMMARY_PRICE = "#summary-price";
    private static final String SUMMARY_TOTAL = "#summary-total";
    
    // Locators - Order Button
    private static final String ORDER_SUBMIT_BUTTON = "#order-btn";
    
    // Locators - Order Confirmation Modal
    private static final String ORDER_MODAL = "#order-modal";
    private static final String MODAL_ORDER_ID = "#modal-order-id";
    private static final String MODAL_ORDER_DETAILS = "#modal-order-details";
    private static final String MODAL_CLOSE_BUTTON = ".btn-close";
    
    // Locators - Positions Table
    private static final String POSITIONS_TABLE = "table";
    private static final String POSITIONS_TABLE_ROWS = "table tbody tr";
    
    public TradingPlatformPage(Page page) {
        super(page);
    }

    /**
     * Gets the current account value displayed.
     * 
     * @return Account value (e.g., "$125,487.32")
     */
    public String getAccountValue() {
        return getElementText(ACCOUNT_VALUE);
    }

    /**
     * Gets the buying power amount.
     * 
     * @return Buying power amount
     */
    public String getBuyingPower() {
        return getElementText(BUYING_POWER);
    }

    /**
     * Gets the daily profit/loss.
     * 
     * @return Daily P&L text
     */
    public String getDailyProfitLoss() {
        return getElementText(DAILY_PL);
    }

    /**
     * Checks if the market is currently open.
     * 
     * @return true if market status shows "OPEN"
     */
    public boolean isMarketOpen() {
        String status = getElementText(MARKET_STATUS);
        return status.contains("OPEN");
    }

    /**
     * Selects a stock from the watchlist by symbol.
     * 
     * @param symbol Stock symbol (e.g., "AAPL")
     */
    public void selectStockFromWatchlist(String symbol) {
        String stockSelector = String.format(".stock-item:has(.stock-symbol:text('%s'))", symbol);
        clickElement(stockSelector);
        waitFor(500); // Wait for chart to update
    }

    /**
     * Gets the currently selected stock symbol in the chart.
     * 
     * @return Stock symbol
     */
    public String getSelectedStockSymbol() {
        return getElementText(CHART_SYMBOL);
    }

    /**
     * Gets the company name for the selected stock.
     * 
     * @return Company name
     */
    public String getSelectedStockCompanyName() {
        return getElementText(CHART_COMPANY_NAME);
    }

    /**
     * Gets the current price of the selected stock.
     * 
     * @return Current price (e.g., "$178.45")
     */
    public String getSelectedStockPrice() {
        return getElementText(CHART_CURRENT_PRICE);
    }

    /**
     * Gets the price change for the selected stock.
     * 
     * @return Price change (e.g., "+2.35 (1.34%)")
     */
    public String getSelectedStockPriceChange() {
        return getElementText(CHART_PRICE_CHANGE);
    }

    /**
     * Switches to the Buy tab in the order panel.
     */
    public void switchToBuyTab() {
        clickElement(BUY_TAB);
    }

    /**
     * Switches to the Sell tab in the order panel.
     */
    public void switchToSellTab() {
        clickElement(SELL_TAB);
    }

    /**
     * Enters the stock symbol to trade.
     * 
     * @param symbol Stock symbol
     */
    public void enterOrderSymbol(String symbol) {
        fillInputField(ORDER_SYMBOL_INPUT, symbol);
    }

    /**
     * Selects the order type.
     * 
     * @param orderType Order type (market, limit, stop, stop-limit)
     */
    public void selectOrderType(String orderType) {
        selectDropdownByValue(ORDER_TYPE_DROPDOWN, orderType);
    }

    /**
     * Enters the limit price for limit orders.
     * 
     * @param limitPrice Limit price
     */
    public void enterLimitPrice(String limitPrice) {
        fillInputField(LIMIT_PRICE_INPUT, limitPrice);
    }

    /**
     * Enters the number of shares to trade.
     * 
     * @param shares Number of shares
     */
    public void enterShareQuantity(String shares) {
        fillInputField(SHARES_INPUT, shares);
    }

    /**
     * Selects the time in force for the order.
     * 
     * @param timeInForce Time in force (day, gtc, ioc, fok)
     */
    public void selectTimeInForce(String timeInForce) {
        selectDropdownByValue(TIME_IN_FORCE_DROPDOWN, timeInForce);
    }

    /**
     * Gets the order summary total amount.
     * 
     * @return Estimated total (e.g., "$1,784.50")
     */
    public String getOrderSummaryTotal() {
        return getElementText(SUMMARY_TOTAL);
    }

    /**
     * Gets the shares quantity from the order summary.
     * 
     * @return Number of shares
     */
    public String getOrderSummaryShares() {
        return getElementText(SUMMARY_SHARES);
    }

    /**
     * Gets the price per share from the order summary.
     * 
     * @return Price per share
     */
    public String getOrderSummaryPrice() {
        return getElementText(SUMMARY_PRICE);
    }

    /**
     * Submits the order.
     */
    public void submitOrder() {
        clickElement(ORDER_SUBMIT_BUTTON);
        waitFor(500); // Wait for modal to appear
    }

    /**
     * Checks if the order confirmation modal is displayed.
     * 
     * @return true if modal is visible
     */
    public boolean isOrderConfirmationDisplayed() {
        return isElementVisible(ORDER_MODAL);
    }

    /**
     * Gets the order ID from the confirmation modal.
     * 
     * @return Order ID (e.g., "Order #12345678")
     */
    public String getOrderConfirmationNumber() {
        waitForElementVisible(MODAL_ORDER_ID);
        return getElementText(MODAL_ORDER_ID);
    }

    /**
     * Gets the order details from the confirmation modal.
     * 
     * @return Order details text
     */
    public String getOrderDetails() {
        return getElementText(MODAL_ORDER_DETAILS);
    }

    /**
     * Closes the order confirmation modal.
     */
    public void closeOrderConfirmation() {
        clickElement(MODAL_CLOSE_BUTTON);
        waitForElementHidden(ORDER_MODAL);
    }

    /**
     * Gets the number of positions in the portfolio.
     * 
     * @return Number of positions
     */
    public int getNumberOfPositions() {
        return getElementCount(POSITIONS_TABLE_ROWS);
    }

    /**
     * Places a market buy order for a stock.
     * 
     * @param symbol Stock symbol
     * @param shares Number of shares
     */
    public void placeBuyMarketOrder(String symbol, String shares) {
        switchToBuyTab();
        enterOrderSymbol(symbol);
        selectOrderType("market");
        enterShareQuantity(shares);
        selectTimeInForce("day");
        submitOrder();
    }

    /**
     * Places a limit buy order for a stock.
     * 
     * @param symbol Stock symbol
     * @param shares Number of shares
     * @param limitPrice Limit price
     */
    public void placeBuyLimitOrder(String symbol, String shares, String limitPrice) {
        switchToBuyTab();
        enterOrderSymbol(symbol);
        selectOrderType("limit");
        enterLimitPrice(limitPrice);
        enterShareQuantity(shares);
        selectTimeInForce("gtc");
        submitOrder();
    }

    /**
     * Places a market sell order for a stock.
     * 
     * @param symbol Stock symbol
     * @param shares Number of shares
     */
    public void placeSellMarketOrder(String symbol, String shares) {
        switchToSellTab();
        enterOrderSymbol(symbol);
        selectOrderType("market");
        enterShareQuantity(shares);
        selectTimeInForce("day");
        submitOrder();
    }

    /**
     * Verifies if the watchlist contains a specific stock.
     * 
     * @param symbol Stock symbol
     * @return true if stock is in watchlist
     */
    public boolean isStockInWatchlist(String symbol) {
        String stockSelector = String.format(".stock-item:has(.stock-symbol:text('%s'))", symbol);
        return isElementVisible(stockSelector);
    }

    /**
     * Gets the number of stocks in the watchlist.
     * 
     * @return Number of stocks
     */
    public int getWatchlistStockCount() {
        return getElementCount(WATCHLIST_STOCKS);
    }
}
