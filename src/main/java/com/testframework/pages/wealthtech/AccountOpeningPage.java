package com.testframework.pages.wealthtech;

import com.microsoft.playwright.Page;
import com.testframework.base.BasePage;

/**
 * Page Object for WealthTech Pro Account Opening page.
 * Handles the multi-step account application process.
 */
@SuppressWarnings("unused")
public class AccountOpeningPage extends BasePage {
    
    // Locators - Progress Steps
    private static final String STEP_1 = ".progress-steps .step:nth-child(1)";
    private static final String STEP_2 = ".progress-steps .step:nth-child(2)";
    private static final String STEP_3 = ".progress-steps .step:nth-child(3)";
    private static final String STEP_ACTIVE_CLASS = "active";
    private static final String STEP_COMPLETED_CLASS = "completed";
    
    // Locators - Account Type Selection
    private static final String ACCOUNT_TYPE_BROKERAGE = "#brokerage";
    private static final String ACCOUNT_TYPE_TRADITIONAL_IRA = "#traditional-ira";
    private static final String ACCOUNT_TYPE_ROTH_IRA = "#roth-ira";
    private static final String ACCOUNT_TYPE_JOINT = "#joint";
    private static final String CONTINUE_BUTTON = "#continue-btn";
    
    // Locators - Personal Information
    private static final String FIRST_NAME_INPUT = "#first-name";
    private static final String LAST_NAME_INPUT = "#last-name";
    private static final String DATE_OF_BIRTH_INPUT = "#dob";
    private static final String SSN_INPUT = "#ssn";
    private static final String EMAIL_INPUT = "#email";
    private static final String PHONE_INPUT = "#phone";
    
    // Locators - Address Information
    private static final String STREET_ADDRESS_INPUT = "#street";
    private static final String CITY_INPUT = "#city";
    private static final String STATE_DROPDOWN = "#state";
    private static final String ZIP_CODE_INPUT = "#zip";
    
    // Locators - Employment Information
    private static final String EMPLOYMENT_STATUS_DROPDOWN = "#employment";
    private static final String ANNUAL_INCOME_DROPDOWN = "#income";
    private static final String NET_WORTH_DROPDOWN = "#net-worth";
    private static final String INVESTMENT_EXPERIENCE_DROPDOWN = "#experience";
    
    // Locators - Regulatory Checkboxes
    private static final String BROKER_AFFILIATED_CHECKBOX = "#broker-affiliated";
    private static final String POLITICALLY_EXPOSED_CHECKBOX = "#politically-exposed";
    private static final String CONTROL_PERSON_CHECKBOX = "#control-person";
    
    // Locators - Agreements
    private static final String TERMS_CHECKBOX = "#terms";
    private static final String PRIVACY_CHECKBOX = "#privacy";
    private static final String DISCLOSURES_CHECKBOX = "#disclosures";
    private static final String ELECTRONIC_DELIVERY_CHECKBOX = "#electronic-delivery";
    
    // Locators - Form Actions
    private static final String BACK_BUTTON = "button.btn-secondary";
    private static final String SUBMIT_APPLICATION_BUTTON = "button[type='submit'].btn-primary";
    
    // Locators - Success Message
    private static final String SUCCESS_MESSAGE = "#success-message";
    private static final String APPLICATION_NUMBER = "#app-number";
    
    public AccountOpeningPage(Page page) {
        super(page);
    }

    /**
     * Selects an account type.
     * 
     * @param accountType Account type (brokerage, traditional-ira, roth-ira, joint)
     */
    public void selectAccountType(String accountType) {
        String selector;
        switch(accountType.toLowerCase()) {
            case "brokerage":
                selector = ACCOUNT_TYPE_BROKERAGE;
                break;
            case "traditional-ira":
                selector = ACCOUNT_TYPE_TRADITIONAL_IRA;
                break;
            case "roth-ira":
                selector = ACCOUNT_TYPE_ROTH_IRA;
                break;
            case "joint":
                selector = ACCOUNT_TYPE_JOINT;
                break;
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
        clickElement(selector);
    }

    /**
     * Clicks the Continue button after account type selection.
     */
    public void clickContinueButton() {
        clickElement(CONTINUE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Checks if the Continue button is enabled.
     * 
     * @return true if enabled
     */
    public boolean isContinueButtonEnabled() {
        return !page.locator(CONTINUE_BUTTON).getAttribute("disabled").equals("true");
    }

    /**
     * Fills in personal information section.
     * 
     * @param firstName First name
     * @param lastName Last name
     * @param dob Date of birth (format: YYYY-MM-DD)
     * @param ssn Social Security Number
     * @param email Email address
     * @param phone Phone number
     */
    public void fillPersonalInformation(String firstName, String lastName, String dob, 
                                        String ssn, String email, String phone) {
        fillInputField(FIRST_NAME_INPUT, firstName);
        fillInputField(LAST_NAME_INPUT, lastName);
        fillInputField(DATE_OF_BIRTH_INPUT, dob);
        fillInputField(SSN_INPUT, ssn);
        fillInputField(EMAIL_INPUT, email);
        fillInputField(PHONE_INPUT, phone);
    }

    /**
     * Fills in address information section.
     * 
     * @param street Street address
     * @param city City
     * @param state State (two-letter code)
     * @param zip ZIP code
     */
    public void fillAddressInformation(String street, String city, String state, String zip) {
        fillInputField(STREET_ADDRESS_INPUT, street);
        fillInputField(CITY_INPUT, city);
        selectDropdownByValue(STATE_DROPDOWN, state);
        fillInputField(ZIP_CODE_INPUT, zip);
    }

    /**
     * Fills in employment information section.
     * 
     * @param employmentStatus Employment status
     * @param incomeRange Annual income range
     * @param netWorthRange Net worth range
     * @param investmentExperience Investment experience level
     */
    public void fillEmploymentInformation(String employmentStatus, String incomeRange, 
                                          String netWorthRange, String investmentExperience) {
        selectDropdownByValue(EMPLOYMENT_STATUS_DROPDOWN, employmentStatus);
        selectDropdownByValue(ANNUAL_INCOME_DROPDOWN, incomeRange);
        selectDropdownByValue(NET_WORTH_DROPDOWN, netWorthRange);
        selectDropdownByValue(INVESTMENT_EXPERIENCE_DROPDOWN, investmentExperience);
    }

    /**
     * Accepts all required agreements.
     */
    public void acceptRequiredAgreements() {
        checkElement(TERMS_CHECKBOX);
        checkElement(PRIVACY_CHECKBOX);
        checkElement(DISCLOSURES_CHECKBOX);
    }

    /**
     * Opts in for electronic delivery.
     */
    public void acceptElectronicDelivery() {
        checkElement(ELECTRONIC_DELIVERY_CHECKBOX);
    }

    /**
     * Submits the account application.
     */
    public void submitApplication() {
        clickElement(SUBMIT_APPLICATION_BUTTON);
        waitFor(1000); // Wait for submission processing
    }

    /**
     * Checks if the application was submitted successfully.
     * 
     * @return true if success message is displayed
     */
    public boolean isApplicationSuccessful() {
        return isElementVisible(SUCCESS_MESSAGE);
    }

    /**
     * Gets the generated application number.
     * 
     * @return Application number (e.g., "Application #: WT-2024-123456")
     */
    public String getApplicationNumber() {
        waitForElementVisible(APPLICATION_NUMBER);
        return getElementText(APPLICATION_NUMBER);
    }

    /**
     * Verifies if a specific progress step is active.
     * 
     * @param stepNumber Step number (1, 2, or 3)
     * @return true if step is active
     */
    public boolean isStepActive(int stepNumber) {
        String selector = String.format(".progress-steps .step:nth-child(%d)", stepNumber);
        return page.locator(selector).getAttribute("class").contains(STEP_ACTIVE_CLASS);
    }

    /**
     * Verifies if a specific progress step is completed.
     * 
     * @param stepNumber Step number (1, 2, or 3)
     * @return true if step is completed
     */
    public boolean isStepCompleted(int stepNumber) {
        String selector = String.format(".progress-steps .step:nth-child(%d)", stepNumber);
        return page.locator(selector).getAttribute("class").contains(STEP_COMPLETED_CLASS);
    }

    /**
     * Fills the complete account application with all required information.
     * 
     * @param accountType Type of account to open
     * @param firstName First name
     * @param lastName Last name
     * @param dob Date of birth
     * @param ssn SSN
     * @param email Email
     * @param phone Phone
     * @param street Street address
     * @param city City
     * @param state State
     * @param zip ZIP code
     * @param employment Employment status
     * @param income Income range
     * @param netWorth Net worth range
     * @param experience Investment experience
     */
    public void fillCompleteApplication(String accountType, String firstName, String lastName, 
                                        String dob, String ssn, String email, String phone,
                                        String street, String city, String state, String zip,
                                        String employment, String income, String netWorth, 
                                        String experience) {
        // Step 1: Select account type
        selectAccountType(accountType);
        clickContinueButton();
        
        // Step 2: Fill application form
        fillPersonalInformation(firstName, lastName, dob, ssn, email, phone);
        fillAddressInformation(street, city, state, zip);
        fillEmploymentInformation(employment, income, netWorth, experience);
        acceptRequiredAgreements();
        
        // Step 3: Submit
        submitApplication();
    }

    /**
     * Validates that SSN is properly formatted.
     * 
     * @return true if SSN matches XXX-XX-XXXX pattern
     */
    public boolean isSSNProperlyFormatted() {
        String ssnValue = getInputValue(SSN_INPUT);
        return ssnValue.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    /**
     * Validates that phone number is properly formatted.
     * 
     * @return true if phone matches (XXX) XXX-XXXX pattern
     */
    public boolean isPhoneProperlyFormatted() {
        String phoneValue = getInputValue(PHONE_INPUT);
        return phoneValue.matches("\\(\\d{3}\\) \\d{3}-\\d{4}");
    }
}
