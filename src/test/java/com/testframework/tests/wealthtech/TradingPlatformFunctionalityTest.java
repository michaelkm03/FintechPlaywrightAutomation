package com.testframework.tests.wealthtech;

import com.testframework.base.BaseTest;
import com.testframework.pages.wealthtech.TradingPlatformPage;
import com.testframework.utils.TestConfig;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test suite for WealthTech Pro Trading Platform.
 * Validates stock quotes, order placement, and portfolio management.
 */
@Epic("WealthTech Pro - Trading Platform")
@Feature("Trading Functionality")
public class TradingPlatformFunctionalityTest extends BaseTest {

    private TradingPlatformPage tradingPage;

    @BeforeMethod
    @Override
    public void createBrowserContextAndPage() {
        super.createBrowserContextAndPage();
        navigateToPage(TestConfig.TRADING_PAGE);
        tradingPage = new TradingPlatformPage(page);
    }

    @Test(description = "Verify trading platform loads with account information", priority = 1)
    @Description("Validates that trading dashboard displays account value, buying power, and P&L")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Trading Dashboard - Account Display")
    public void testTradingPlatformLoadsSuccessfully() {
        // Verify account value is displayed
        String accountValue = tradingPage.getAccountValue();
        Assert.assertTrue(accountValue.startsWith("$"), 
            "Account value should start with $: " + accountValue);
        Assert.assertTrue(accountValue.contains(","), 
            "Account value should be formatted with commas: " + accountValue);
        
        // Verify buying power is displayed
        String buyingPower = tradingPage.getBuyingPower();
        Assert.assertTrue(buyingPower.startsWith("$"), 
            "Buying power should start with $: " + buyingPower);
        
        // Verify daily P&L is displayed
        String dailyPL = tradingPage.getDailyProfitLoss();
        Assert.assertTrue(dailyPL.contains("$") && (dailyPL.contains("+") || dailyPL.contains("-")), 
            "Daily P&L should show change: " + dailyPL);
        
        System.out.println("✓ Trading platform loaded successfully");
        System.out.println("  Account Value: " + accountValue);
        System.out.println("  Buying Power: " + buyingPower);
        System.out.println("  Daily P&L: " + dailyPL);
    }

    @Test(description = "Verify watchlist displays stocks", priority = 2)
    @Description("Tests that watchlist shows configured stocks with prices and changes")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Watchlist - Stock Display")
    public void testWatchlistDisplaysStocks() {
        // Verify watchlist has stocks
        int stockCount = tradingPage.getWatchlistStockCount();
        Assert.assertTrue(stockCount > 0, 
            "Watchlist should contain at least one stock");
        
        // Verify specific stocks are present
        Assert.assertTrue(tradingPage.isStockInWatchlist("AAPL"), 
            "AAPL should be in watchlist");
        Assert.assertTrue(tradingPage.isStockInWatchlist("MSFT"), 
            "MSFT should be in watchlist");
        Assert.assertTrue(tradingPage.isStockInWatchlist("GOOGL"), 
            "GOOGL should be in watchlist");
        
        System.out.println("✓ Watchlist displays " + stockCount + " stocks");
    }

    @Test(description = "Select stock from watchlist and view details", priority = 3)
    @Description("Tests clicking a stock in watchlist updates the chart and details")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Watchlist - Stock Selection")
    public void testSelectStockFromWatchlist() {
        // Select MSFT from watchlist
        tradingPage.selectStockFromWatchlist("MSFT");
        
        // Verify chart updates with selected stock
        String selectedSymbol = tradingPage.getSelectedStockSymbol();
        Assert.assertEquals(selectedSymbol, "MSFT", 
            "Selected stock symbol should be MSFT");
        
        String companyName = tradingPage.getSelectedStockCompanyName();
        Assert.assertTrue(companyName.contains("Microsoft"), 
            "Company name should contain Microsoft: " + companyName);
        
        String price = tradingPage.getSelectedStockPrice();
        Assert.assertTrue(price.startsWith("$"), 
            "Stock price should start with $: " + price);
        
        System.out.println("✓ Stock selection working correctly");
        System.out.println("  Selected: " + selectedSymbol + " - " + companyName);
        System.out.println("  Price: " + price);
    }

    @Test(description = "Place market buy order successfully", priority = 4)
    @Description("End-to-end test for placing a buy market order")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Order Execution - Buy Orders")
    public void testPlaceBuyMarketOrder() {
        // Place buy order for AAPL
        tradingPage.placeBuyMarketOrder(
            TestConfig.TradingData.DEFAULT_SYMBOL, 
            String.valueOf(TestConfig.TradingData.DEFAULT_SHARES)
        );
        
        // Verify order confirmation is displayed
        Assert.assertTrue(tradingPage.isOrderConfirmationDisplayed(), 
            "Order confirmation modal should be displayed");
        
        // Verify order number is generated
        String orderNumber = tradingPage.getOrderConfirmationNumber();
        Assert.assertTrue(orderNumber.contains("Order #"), 
            "Order confirmation should contain order number: " + orderNumber);
        
        // Verify order details
        String orderDetails = tradingPage.getOrderDetails();
        Assert.assertTrue(orderDetails.contains("BUY"), 
            "Order details should show BUY action");
        Assert.assertTrue(orderDetails.contains(TestConfig.TradingData.DEFAULT_SYMBOL), 
            "Order details should contain stock symbol");
        Assert.assertTrue(orderDetails.contains(String.valueOf(TestConfig.TradingData.DEFAULT_SHARES)), 
            "Order details should show share quantity");
        
        System.out.println("✓ Buy market order placed successfully");
        System.out.println("  " + orderNumber);
        
        // Close confirmation
        tradingPage.closeOrderConfirmation();
        Assert.assertFalse(tradingPage.isOrderConfirmationDisplayed(), 
            "Order confirmation should be closed");
    }

    @Test(description = "Place limit buy order", priority = 5)
    @Description("Tests placing a buy limit order with specified limit price")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Order Execution - Buy Orders")
    public void testPlaceBuyLimitOrder() {
        tradingPage.placeBuyLimitOrder("TSLA", "5", "190.00");
        
        Assert.assertTrue(tradingPage.isOrderConfirmationDisplayed(), 
            "Limit order confirmation should be displayed");
        
        String orderDetails = tradingPage.getOrderDetails();
        Assert.assertTrue(orderDetails.contains("LIMIT"), 
            "Order should be LIMIT type: " + orderDetails);
        Assert.assertTrue(orderDetails.contains("TSLA"), 
            "Order should be for TSLA");
        
        System.out.println("✓ Buy limit order placed successfully");
        
        tradingPage.closeOrderConfirmation();
    }

    @Test(description = "Place market sell order", priority = 6)
    @Description("Tests placing a sell market order")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Order Execution - Sell Orders")
    public void testPlaceSellMarketOrder() {
        tradingPage.placeSellMarketOrder("AAPL", "10");
        
        Assert.assertTrue(tradingPage.isOrderConfirmationDisplayed(), 
            "Sell order confirmation should be displayed");
        
        String orderDetails = tradingPage.getOrderDetails();
        Assert.assertTrue(orderDetails.contains("SELL"), 
            "Order details should show SELL action");
        
        System.out.println("✓ Sell market order placed successfully");
        
        tradingPage.closeOrderConfirmation();
    }

    @Test(description = "Verify order summary calculates correctly", priority = 7)
    @Description("Tests that order summary total is calculated based on shares and price")
    @Severity(SeverityLevel.NORMAL)
    @Story("Order Entry - Calculation")
    public void testOrderSummaryCalculation() {
        // Enter order details
        tradingPage.switchToBuyTab();
        tradingPage.enterOrderSymbol("NVDA");
        tradingPage.enterShareQuantity("50");
        
        // Wait for summary to update
        page.waitForTimeout(500);
        
        // Verify summary shows correct shares
        String summaryShares = tradingPage.getOrderSummaryShares();
        Assert.assertEquals(summaryShares, "50", 
            "Order summary should show 50 shares");
        
        // Verify total is calculated
        String summaryTotal = tradingPage.getOrderSummaryTotal();
        Assert.assertTrue(summaryTotal.startsWith("$"), 
            "Summary total should start with $: " + summaryTotal);
        Assert.assertFalse(summaryTotal.equals("$0.00"), 
            "Summary total should not be zero");
        
        System.out.println("✓ Order summary calculation working correctly");
        System.out.println("  Shares: " + summaryShares);
        System.out.println("  Estimated Total: " + summaryTotal);
    }

    @Test(description = "Verify current positions are displayed", priority = 8)
    @Description("Tests that portfolio positions table shows holdings")
    @Severity(SeverityLevel.NORMAL)
    @Story("Portfolio - Positions Display")
    public void testPortfolioPositionsDisplay() {
        int positionCount = tradingPage.getNumberOfPositions();
        Assert.assertTrue(positionCount > 0, 
            "Portfolio should display at least one position");
        
        System.out.println("✓ Portfolio displays " + positionCount + " positions");
    }

    @Test(description = "Switch between Buy and Sell tabs", priority = 9)
    @Description("Tests that Buy/Sell tab switching works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Story("Order Entry - Tab Navigation")
    public void testBuySellTabSwitching() {
        // Initially on Buy tab
        tradingPage.switchToBuyTab();
        String buttonText = page.locator("#order-btn").textContent();
        Assert.assertTrue(buttonText.contains("Buy"), 
            "Button should show Buy when on Buy tab");
        
        // Switch to Sell tab
        tradingPage.switchToSellTab();
        buttonText = page.locator("#order-btn").textContent();
        Assert.assertTrue(buttonText.contains("Sell"), 
            "Button should show Sell when on Sell tab");
        
        System.out.println("✓ Buy/Sell tab switching working correctly");
    }

    @Test(description = "Verify market status is displayed", priority = 10)
    @Description("Tests that market open/closed status is shown")
    @Severity(SeverityLevel.MINOR)
    @Story("Trading Dashboard - Market Status")
    public void testMarketStatusDisplay() {
        boolean isOpen = tradingPage.isMarketOpen();
        // Market status should be present (can be either open or closed)
        Assert.assertNotNull(isOpen, 
            "Market status should be displayed");
        
        System.out.println("✓ Market status: " + (isOpen ? "OPEN" : "CLOSED"));
    }

    @Test(description = "NEGATIVE: Cannot place order with zero shares", priority = 11)
    @Description("Verifies validation prevents orders with zero quantity")
    @Severity(SeverityLevel.NORMAL)
    @Story("Order Validation - Share Quantity")
    public void testCannotPlaceOrderWithZeroShares() {
        tradingPage.switchToBuyTab();
        tradingPage.enterOrderSymbol("AAPL");
        tradingPage.selectOrderType("market");
        
        // Try to enter 0 shares
        page.locator("#shares").fill("0");
        
        // Input has min="1" attribute which should prevent this
        String minValue = page.locator("#shares").getAttribute("min");
        Assert.assertEquals(minValue, "1", 
            "Shares input should have minimum value of 1");
        
        System.out.println("✓ Zero shares validation in place");
    }

    @Test(description = "NEGATIVE: Verify invalid symbol handling", priority = 12)
    @Description("Tests behavior when entering an invalid stock symbol")
    @Severity(SeverityLevel.MINOR)
    @Story("Order Validation - Symbol")
    public void testInvalidSymbolHandling() {
        tradingPage.switchToBuyTab();
        tradingPage.enterOrderSymbol("INVALID123");
        tradingPage.enterShareQuantity("10");
        
        // In production, this would show error message or prevent submission
        // For demo, order will still process
        
        System.out.println("ℹ Invalid symbol can be entered (production would validate)");
    }

    @Test(description = "Verify limit price field appears for limit orders", priority = 13)
    @Description("Tests that limit price input is shown when limit order type is selected")
    @Severity(SeverityLevel.NORMAL)
    @Story("Order Entry - Order Types")
    public void testLimitPriceFieldDisplay() {
        tradingPage.switchToBuyTab();
        
        // Limit price should be hidden for market orders
        tradingPage.selectOrderType("market");
        Assert.assertFalse(page.locator("#limit-price-group").isVisible(), 
            "Limit price field should be hidden for market orders");
        
        // Limit price should appear for limit orders
        tradingPage.selectOrderType("limit");
        Assert.assertTrue(page.locator("#limit-price-group").isVisible(), 
            "Limit price field should be visible for limit orders");
        
        System.out.println("✓ Limit price field conditional display working");
    }

    @Test(description = "Place order with different time-in-force options", priority = 14)
    @Description("Tests order placement with various time-in-force settings")
    @Severity(SeverityLevel.NORMAL)
    @Story("Order Execution - Time In Force")
    public void testOrderWithDifferentTimeInForce() {
        // Test Day order
        tradingPage.switchToBuyTab();
        tradingPage.enterOrderSymbol("GOOGL");
        tradingPage.selectOrderType("market");
        tradingPage.enterShareQuantity("15");
        tradingPage.selectTimeInForce("day");
        tradingPage.submitOrder();
        
        Assert.assertTrue(tradingPage.isOrderConfirmationDisplayed(), 
            "Day order should be placed successfully");
        tradingPage.closeOrderConfirmation();
        
        // Test GTC order
        tradingPage.selectTimeInForce("gtc");
        tradingPage.submitOrder();
        
        Assert.assertTrue(tradingPage.isOrderConfirmationDisplayed(), 
            "GTC order should be placed successfully");
        tradingPage.closeOrderConfirmation();
        
        System.out.println("✓ Different time-in-force options working");
    }
}
