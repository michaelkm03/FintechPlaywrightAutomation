package com.testframework.pages;

import com.microsoft.playwright.Page;
import com.testframework.base.BasePage;

@SuppressWarnings("unused")
public class PracticePage extends BasePage {
    
    // Form locators
    private final String usernameInput = "#username";
    private final String emailInput = "#email";
    private final String passwordInput = "#password";
    private final String ageInput = "#age";
    private final String birthdateInput = "#birthdate";
    private final String countrySelect = "#country";
    private final String commentsTextarea = "#comments";
    private final String termsCheckbox = "#terms";
    private final String submitButton = "#submit-btn";
    private final String successAlert = "#success-alert";
    private final String errorAlert = "#error-alert";
    
    // Radio buttons
    private final String maleRadio = "#male";
    private final String femaleRadio = "#female";
    private final String otherRadio = "#other";
    
    // Checkboxes for interests
    private final String sportsCheckbox = "#sports";
    private final String musicCheckbox = "#music";
    private final String readingCheckbox = "#reading";
    private final String travelCheckbox = "#travel";
    
    // Buttons
    private final String clickMeButton = "#click-me-btn";
    private final String clickResult = "#click-result";
    private final String modalButton = "#modal-btn";
    private final String modalDialog = "#modal";
    private final String closeModalButton = "#close-modal";
    private final String modalOkButton = "#modal-ok";
    
    // Dropdown
    private final String dropdownButton = "#dropdown-btn";
    private final String dropdownContent = "#dropdown-content";
    private final String dropdownResult = "#dropdown-result";
    
    // File upload
    private final String fileInput = "#file-input";
    private final String fileName = "#file-name";
    
    // Progress bar
    private final String startProgressButton = "#start-progress";
    private final String progressFill = "#progress-fill";
    
    // Table
    private final String dataTable = "#data-table";
    
    public PracticePage(Page page) {
        super(page);
    }

    // Form actions
    public void fillUsername(String username) {
        fill(usernameInput, username);
    }

    public void fillEmail(String email) {
        fill(emailInput, email);
    }

    public void fillPassword(String password) {
        fill(passwordInput, password);
    }

    public void fillAge(String age) {
        fill(ageInput, age);
    }

    public void selectCountry(String country) {
        page.locator(countrySelect).selectOption(country);
    }

    public void fillComments(String comments) {
        fill(commentsTextarea, comments);
    }

    public void checkTerms() {
        page.locator(termsCheckbox).check();
    }

    public void selectGender(String gender) {
        switch(gender.toLowerCase()) {
            case "male":
                click(maleRadio);
                break;
            case "female":
                click(femaleRadio);
                break;
            case "other":
                click(otherRadio);
                break;
        }
    }

    public void selectInterest(String interest) {
        switch(interest.toLowerCase()) {
            case "sports":
                page.locator(sportsCheckbox).check();
                break;
            case "music":
                page.locator(musicCheckbox).check();
                break;
            case "reading":
                page.locator(readingCheckbox).check();
                break;
            case "travel":
                page.locator(travelCheckbox).check();
                break;
        }
    }

    public void submitForm() {
        click(submitButton);
    }

    public boolean isSuccessAlertVisible() {
        return page.locator(successAlert).isVisible();
    }

    public boolean isErrorAlertVisible() {
        return page.locator(errorAlert).isVisible();
    }

    // Button actions
    public void clickMeButton() {
        click(clickMeButton);
    }

    public String getClickResult() {
        return getText(clickResult);
    }

    // Modal actions
    public void openModal() {
        click(modalButton);
    }

    public boolean isModalVisible() {
        return page.locator(modalDialog).isVisible();
    }

    public void closeModal() {
        click(closeModalButton);
    }

    public void clickModalOk() {
        click(modalOkButton);
    }

    // Dropdown actions
    public void clickDropdown() {
        click(dropdownButton);
    }

    public void selectDropdownOption(String option) {
        page.locator(dropdownContent + " a[data-action='" + option + "']").click();
    }

    public String getDropdownResult() {
        return getText(dropdownResult);
    }

    // File upload
    public void uploadFile(String filePath) {
        page.locator(fileInput).setInputFiles(java.nio.file.Paths.get(filePath));
    }

    public String getUploadedFileName() {
        return getText(fileName);
    }

    // Progress bar
    public void startProgress() {
        click(startProgressButton);
    }

    public String getProgressValue() {
        return getText(progressFill);
    }

    // Table actions
    public int getTableRowCount() {
        return page.locator(dataTable + " tbody tr").count();
    }

    public String getTableCellText(int row, int column) {
        return page.locator(dataTable + " tbody tr").nth(row).locator("td").nth(column).textContent();
    }

    // Complete form fill helper
    public void fillCompleteForm(String username, String email, String password, 
                                  String age, String country, String gender, 
                                  String comments) {
        fillUsername(username);
        fillEmail(email);
        fillPassword(password);
        fillAge(age);
        selectCountry(country);
        selectGender(gender);
        fillComments(comments);
        checkTerms();
    }
}
