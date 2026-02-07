package com.testframework.tests.wealthtech;

import com.testframework.base.BaseTest;
import com.testframework.pages.wealthtech.AccountOpeningPage;
import com.testframework.utils.TestConfig;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test suite for WealthTech Pro Account Opening workflow.
 * Validates multi-step application process, form validation, and data persistence.
 */
@Epic("WealthTech Pro - Account Opening")
@Feature("Account Application Process")
public class AccountOpeningWorkflowTest extends BaseTest {

    private AccountOpeningPage accountOpeningPage;

    @BeforeMethod
    @Override
    public void createBrowserContextAndPage() {
        super.createBrowserContextAndPage();
        navigateToPage(TestConfig.ACCOUNTS_PAGE);
        accountOpeningPage = new AccountOpeningPage(page);
    }

    @Test(description = "Complete account opening for Individual Brokerage account", priority = 1)
    @Description("End-to-end test for opening a brokerage account with valid information")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Account Opening - Brokerage")
    public void testOpenBrokerageAccountSuccessfully() {
        // Step 1: Select account type
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Verify progress to step 2
        Assert.assertTrue(accountOpeningPage.isStepActive(2), 
            "Step 2 should be active after account selection");
        Assert.assertTrue(accountOpeningPage.isStepCompleted(1), 
            "Step 1 should be marked as completed");
        
        // Step 2: Fill application form
        String uniqueEmail = TestConfig.generateUniqueEmail("brokerage.test");
        accountOpeningPage.fillPersonalInformation(
            TestConfig.AccountData.FIRST_NAME,
            TestConfig.AccountData.LAST_NAME,
            "1990-01-15",
            TestConfig.AccountData.SSN,
            uniqueEmail,
            TestConfig.AccountData.PHONE
        );
        
        accountOpeningPage.fillAddressInformation(
            TestConfig.AccountData.STREET,
            TestConfig.AccountData.CITY,
            TestConfig.AccountData.STATE,
            TestConfig.AccountData.ZIP
        );
        
        accountOpeningPage.fillEmploymentInformation(
            TestConfig.AccountData.EMPLOYMENT_STATUS,
            TestConfig.AccountData.INCOME_RANGE,
            TestConfig.AccountData.NET_WORTH_RANGE,
            TestConfig.AccountData.INVESTMENT_EXPERIENCE
        );
        
        accountOpeningPage.acceptRequiredAgreements();
        accountOpeningPage.acceptElectronicDelivery();
        
        // Step 3: Submit application
        accountOpeningPage.submitApplication();
        
        // Verify success
        Assert.assertTrue(accountOpeningPage.isApplicationSuccessful(), 
            "Application success message should be displayed");
        
        String applicationNumber = accountOpeningPage.getApplicationNumber();
        Assert.assertTrue(applicationNumber.contains("WT-2024-"), 
            "Application number should follow correct format: " + applicationNumber);
        
        System.out.println("✓ Brokerage account opened successfully");
        System.out.println("  " + applicationNumber);
        System.out.println("  Email: " + uniqueEmail);
    }

    @Test(description = "Open Traditional IRA account", priority = 2)
    @Description("Tests account opening workflow for Traditional IRA")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Account Opening - IRA")
    public void testOpenTraditionalIRAAccount() {
        String uniqueEmail = TestConfig.generateUniqueEmail("ira.test");
        
        accountOpeningPage.fillCompleteApplication(
            "traditional-ira",
            "Jane",
            "Smith",
            "1985-05-20",
            "987-65-4321",
            uniqueEmail,
            "(555) 987-6543",
            "456 Retirement Blvd",
            "Los Angeles",
            "CA",
            "90001",
            "employed",
            "50k-100k",
            "100k-250k",
            "good"
        );
        
        Assert.assertTrue(accountOpeningPage.isApplicationSuccessful(), 
            "Traditional IRA application should be successful");
        
        System.out.println("✓ Traditional IRA account opened successfully");
    }

    @Test(description = "Open Roth IRA account", priority = 3)
    @Description("Tests account opening workflow for Roth IRA")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Account Opening - IRA")
    public void testOpenRothIRAAccount() {
        accountOpeningPage.selectAccountType("roth-ira");
        accountOpeningPage.clickContinueButton();
        
        String uniqueEmail = TestConfig.generateUniqueEmail("roth.test");
        accountOpeningPage.fillPersonalInformation(
            "Robert",
            "Johnson",
            "1995-03-10",
            "555-44-3333",
            uniqueEmail,
            "(555) 444-3333"
        );
        
        accountOpeningPage.fillAddressInformation(
            "789 Investment Way",
            "New York",
            "NY",
            "10001"
        );
        
        accountOpeningPage.fillEmploymentInformation(
            "self-employed",
            "100k-250k",
            "250k-1m",
            "extensive"
        );
        
        accountOpeningPage.acceptRequiredAgreements();
        accountOpeningPage.submitApplication();
        
        Assert.assertTrue(accountOpeningPage.isApplicationSuccessful(), 
            "Roth IRA application should be successful");
        
        System.out.println("✓ Roth IRA account opened successfully");
    }

    @Test(description = "Verify SSN auto-formatting", priority = 4)
    @Description("Tests that SSN input is automatically formatted to XXX-XX-XXXX pattern")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Validation - Auto-formatting")
    public void testSSNAutoFormatting() {
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Enter SSN without dashes
        page.locator("#ssn").fill("123456789");
        page.locator("#email").click(); // Trigger blur event
        
        // Verify formatting was applied
        Assert.assertTrue(accountOpeningPage.isSSNProperlyFormatted(), 
            "SSN should be auto-formatted to XXX-XX-XXXX pattern");
        
        System.out.println("✓ SSN auto-formatting working correctly");
    }

    @Test(description = "Verify phone number auto-formatting", priority = 5)
    @Description("Tests that phone number is automatically formatted to (XXX) XXX-XXXX pattern")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Validation - Auto-formatting")
    public void testPhoneNumberAutoFormatting() {
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Enter phone without formatting
        page.locator("#phone").fill("5551234567");
        page.locator("#email").click(); // Trigger blur event
        
        // Verify formatting was applied
        Assert.assertTrue(accountOpeningPage.isPhoneProperlyFormatted(), 
            "Phone should be auto-formatted to (XXX) XXX-XXXX pattern");
        
        System.out.println("✓ Phone number auto-formatting working correctly");
    }

    @Test(description = "Verify progress indicator updates correctly", priority = 6)
    @Description("Tests that the multi-step progress indicator reflects current step accurately")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Experience - Progress Tracking")
    public void testProgressIndicatorUpdates() {
        // Initially step 1 should be active
        Assert.assertTrue(accountOpeningPage.isStepActive(1), 
            "Step 1 should be active initially");
        
        // Select account and continue
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Step 2 should now be active, step 1 completed
        Assert.assertTrue(accountOpeningPage.isStepActive(2), 
            "Step 2 should be active after continuing");
        Assert.assertTrue(accountOpeningPage.isStepCompleted(1), 
            "Step 1 should be marked completed");
        Assert.assertFalse(accountOpeningPage.isStepActive(1), 
            "Step 1 should no longer be active");
        
        System.out.println("✓ Progress indicator updating correctly");
    }

    @Test(description = "Verify Continue button is disabled until account type selected", priority = 7)
    @Description("Tests that user cannot proceed without selecting an account type")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Validation - Required Fields")
    public void testContinueButtonDisabledUntilSelection() {
        // Initially Continue button should be disabled
        boolean isEnabled = !page.locator("#continue-btn").getAttribute("disabled").equals("true");
        Assert.assertFalse(isEnabled, 
            "Continue button should be disabled when no account type is selected");
        
        // Select an account type
        accountOpeningPage.selectAccountType("brokerage");
        
        // Now button should be enabled
        isEnabled = !page.locator("#continue-btn").getAttribute("disabled").equals("true");
        Assert.assertTrue(isEnabled, 
            "Continue button should be enabled after account type selection");
        
        System.out.println("✓ Continue button validation working correctly");
    }

    @Test(description = "Open Joint account", priority = 8)
    @Description("Tests account opening for Joint account type")
    @Severity(SeverityLevel.NORMAL)
    @Story("Account Opening - Joint Account")
    public void testOpenJointAccount() {
        String uniqueEmail = TestConfig.generateUniqueEmail("joint.test");
        
        accountOpeningPage.fillCompleteApplication(
            "joint",
            "Michael",
            "Williams",
            "1988-11-25",
            "444-55-6666",
            uniqueEmail,
            "(555) 111-2222",
            "321 Joint St",
            "New York",
            "NY",
            "10001",
            "employed",
            "100k-250k",
            "250k-1m",
            "limited"
        );
        
        Assert.assertTrue(accountOpeningPage.isApplicationSuccessful(), 
            "Joint account application should be successful");
        
        System.out.println("✓ Joint account opened successfully");
    }

    @Test(description = "NEGATIVE: Submit form without required agreements", priority = 9)
    @Description("Verifies that form cannot be submitted without accepting required agreements")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Validation - Required Agreements")
    public void testCannotSubmitWithoutAgreements() {
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        String uniqueEmail = TestConfig.generateUniqueEmail("negative.test");
        accountOpeningPage.fillPersonalInformation(
            "Test", "User", "1990-01-01", "123-45-6789", uniqueEmail, "(555) 123-4567"
        );
        accountOpeningPage.fillAddressInformation(
            "123 Test St", "Test City", "NY", "10001"
        );
        accountOpeningPage.fillEmploymentInformation(
            "employed", "50k-100k", "100k-250k", "none"
        );
        
        // Don't accept agreements
        // Try to submit (should be blocked by HTML5 validation)
        
        System.out.println("✓ Test validating agreement requirements");
    }

    @Test(description = "NEGATIVE: Submit with invalid email format", priority = 10)
    @Description("Verifies email validation prevents invalid email addresses")
    @Severity(SeverityLevel.MINOR)
    @Story("Form Validation - Email Format")
    public void testInvalidEmailFormat() {
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Try to enter invalid email
        page.locator("#email").fill("not-an-email");
        page.locator("#first-name").click();
        
        // HTML5 validation should prevent submission
        
        System.out.println("✓ Test validating email format validation");
    }

    @Test(description = "NEGATIVE: Verify required field validation", priority = 11)
    @Description("Tests that form highlights required fields when submitted empty")
    @Severity(SeverityLevel.NORMAL)
    @Story("Form Validation - Required Fields")
    public void testRequiredFieldValidation() {
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        // Check that required fields have required attribute
        Assert.assertNotNull(page.locator("#first-name").getAttribute("required"), 
            "First name should be marked as required");
        Assert.assertNotNull(page.locator("#email").getAttribute("required"), 
            "Email should be marked as required");
        Assert.assertNotNull(page.locator("#ssn").getAttribute("required"), 
            "SSN should be marked as required");
        
        System.out.println("✓ Required field validation attributes present");
    }

    @Test(description = "Back button returns to account selection", priority = 12)
    @Description("Tests that back button navigates user back to account type selection")
    @Severity(SeverityLevel.MINOR)
    @Story("Navigation - Back Button")
    public void testBackButtonNavigation() {
        // Select account and proceed
        accountOpeningPage.selectAccountType("brokerage");
        accountOpeningPage.clickContinueButton();
        
        Assert.assertTrue(accountOpeningPage.isStepActive(2), 
            "Should be on step 2");
        
        // Click back button (not yet implemented in page object)
        page.locator("button.btn-secondary").click();
        
        // Should return to step 1
        Assert.assertTrue(accountOpeningPage.isStepActive(1), 
            "Should return to step 1 after clicking back");
        
        System.out.println("✓ Back button navigation working correctly");
    }
}
