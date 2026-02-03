package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By cartItems = By.className("cart_item");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public String getCartItemName() {
        By itemName = By.className("inventory_item_name");
        return getText(itemName);
    }

    public String getCartItemPrice() {
        By itemPrice = By.className("inventory_item_price");
        return getText(itemPrice);
    }

    public void removeItemFromCart(int itemIndex) {
        By removeButton = By.xpath(
                "(//button[contains(text(), 'Remove')])[" + (itemIndex + 1) + "]"
        );
        click(removeButton);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
