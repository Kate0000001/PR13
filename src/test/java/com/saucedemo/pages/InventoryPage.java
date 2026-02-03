package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage extends BasePage {

    private By pageTitle = By.xpath("//span[contains(text(), 'Products')]");
    private By cartIcon = By.className("shopping_cart_link");
    private By cartBadge = By.className("shopping_cart_badge");
    private By sortDropdown = By.cssSelector("[data-test='product_sort_container']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInventoryPageLoaded() {
        return isElementVisible(pageTitle);
    }

    public void addProductToCart(String productName) {
        By addToCartButton = By.xpath(
                "//div[contains(text(), '" + productName + "')]" +
                        "/ancestor::div[@class='inventory_item']" +
                        "//button[contains(text(), 'Add to cart')]"
        );
        click(addToCartButton);
    }

    public int getCartCount() {
        try {
            String count = getText(cartBadge).trim();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        click(cartIcon);
    }

    public void selectSort(String sortOption) {
        WebElement dropdown = driver.findElement(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByValue(sortOption);
    }
}
