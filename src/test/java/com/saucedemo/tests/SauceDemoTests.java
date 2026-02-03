package com.saucedemo.tests;

import com.saucedemo.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceDemoTests extends BaseTest {

    @Test(description = "Позитивный логин с корректными учётными данными")
    public void testPositiveLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        loginPage.waitForUrlContains("inventory.html");

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(
                inventoryPage.isInventoryPageLoaded(),
                "Ошибка: страница товаров не загружена после успешного логина"
        );

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("inventory.html"),
                "Ошибка: ожидали URL с 'inventory.html', получили: " + currentUrl
        );
    }

    @Test(description = "Негативный логин - заблокированный пользователь")
    public void testNegativeLoginLockedUser() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(
                loginPage.isErrorMessageVisible(),
                "Ошибка: сообщение об ошибке не отобразилось"
        );

        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(
                errorText.contains("Epic sadface"),
                "Ошибка: ожидали текст 'Epic sadface' в сообщении, получили: " + errorText
        );

        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(
                currentUrl.contains("inventory.html"),
                "Ошибка: пользователь не должен был попасть на страницу товаров"
        );
    }

    @Test(description = "Добавление товара в корзину")
    public void testAddProductToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForUrlContains("inventory.html");

        InventoryPage inventoryPage = new InventoryPage(driver);

        int initialCartCount = inventoryPage.getCartCount();
        Assert.assertEquals(
                initialCartCount, 0,
                "Ошибка: корзина должна быть пуста в начале"
        );

        inventoryPage.addProductToCart("Sauce Labs Backpack");

        int cartCountAfterAdding = inventoryPage.getCartCount();
        Assert.assertEquals(
                cartCountAfterAdding, 1,
                "Ошибка: после добавления товара счётчик должен быть 1, получили: " + cartCountAfterAdding
        );

        inventoryPage.openCart();

        CartPage cartPage = new CartPage(driver);

        String cartItemName = cartPage.getCartItemName();
        Assert.assertTrue(
                cartItemName.contains("Sauce Labs Backpack"),
                "Ошибка: ожидали товар 'Sauce Labs Backpack', получили: " + cartItemName
        );

        String cartItemPrice = cartPage.getCartItemPrice();
        Assert.assertFalse(
                cartItemPrice.isEmpty(),
                "Ошибка: цена товара должна быть указана"
        );
    }

    @Test(description = "Добавление нескольких товаров в корзину")
    public void testAddMultipleProductsToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        loginPage.waitForUrlContains("inventory.html");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Bike Light");

        int cartCount = inventoryPage.getCartCount();
        Assert.assertEquals(
                cartCount, 2,
                "Ошибка: после добавления двух товаров счётчик должен быть 2, получили: " + cartCount
        );
    }

    @Test(description = "Проверка логина с problem_user")
    public void testProblemUserLogin() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("problem_user", "secret_sauce");

        loginPage.waitForUrlContains("inventory.html");

        InventoryPage inventoryPage = new InventoryPage(driver);

        Assert.assertTrue(
                inventoryPage.isInventoryPageLoaded(),
                "Ошибка: problem_user не может войти на страницу товаров"
        );
    }
}
