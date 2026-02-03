package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameField = By.xpath("//input[@id='first-name']");
    private By lastNameField = By.xpath("//input[@id='last-name']");
    private By postalCodeField = By.xpath("//input[@id='postal-code']");
    private By continueButton = By.xpath("//input[@id='continue']");

    private By finishButton = By.xpath("//button[@id='finish']");
    private By subtotalLabel = By.xpath("//*[contains(text(), 'Item total')]");

    private By completeMessage = By.xpath("//h2[contains(text(), 'Thank')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        sendKeys(firstNameField, firstName);
        sendKeys(lastNameField, lastName);
        sendKeys(postalCodeField, postalCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public void clickFinish() {
        click(finishButton);
    }

    public String getCompleteMessage() {
        return getText(completeMessage);
    }

    public String getOrderTotal() {
        return getText(subtotalLabel);
    }

    public boolean isOrderCompleted() {
        return isElementVisible(completeMessage);
    }
}
