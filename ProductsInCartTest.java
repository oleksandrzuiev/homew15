package homeWork15;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class ProductsInCartTest extends ProductsInCart {

    @Test
    public void testAddingProductsToCart() {

        // add first item to cart
        String price1 = findItemElement(pricePath, firstItem).getText();
        String title1 = findItemElement(titlePath, firstItem).getText();
        findAndClickItemElement(addButtonPath, firstItem);

        //close opened cart
        findAndClick(closeCartPath);

        // add second item to cart
        String price2 = findItemElement(pricePath, secondItem).getText();
        String title2 = findItemElement(titlePath, secondItem).getText();
        findAndClickItemElement(addButtonPath, secondItem);

        // check that cart has 2 items
        assertEquals(driver.findElements(By.xpath(itemFieldPath)).size(),
                2, "Incorrect number of items in cart.");

        // check that cart is not empty
        assertTrue(driver.findElements(By.xpath(emptyCartViewPath)).isEmpty(), "Items in cart are empty");

        // check that cart is opened
        WebElement cartDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(cartViewClass)));
        assertTrue(cartDiv.isDisplayed(), "Cart is not opened");

        // check that first item in cart is correct
        assertTrue(findItemElement(priceCartPath, firstItem).getText().contains(price1),
                "Incorrect price of first item");
        assertTrue(findItemElement(titleCartPath, firstItem).getText().contains(title1),
                "Incorrect title of first item");
        assertEquals(extractNumber(findItemElement(quantityCartPath, firstItem).getText(), quantityCartRegex),
                1,
                "Incorrect quantity for first item");

        // check that second item in cart is correct
        assertTrue(findItemElement(priceCartPath, secondItem).getText().contains(price2),
                "Incorrect price of second item");
        assertTrue(findItemElement(titleCartPath, secondItem).getText().contains(title2),
                "Incorrect title of second item");
        assertEquals(extractNumber(findItemElement(quantityCartPath, secondItem).getText(), quantityCartRegex),
                1,
                "Incorrect quantity for second item");

        // checking for total price
        assertEquals(extractNumber(driver.findElement(By.className(totalCartPriceClass)).getText(), totalPriceRegex),
                Integer.parseInt(price1) + Integer.parseInt(price2),
                "Incorrect total price in cart");

        // looking for button CHECKOUT
        assertTrue(driver.findElement(By.xpath(checkoutButtonPath)).isDisplayed(),
                "Checkout button is not visible.");
    }

    @Test
    public void testRemovingProductFromCart() {

        // add item to cart
        String price1 = findItemElement(pricePath, firstItem).getText();
        String title1 = findItemElement(titlePath, firstItem).getText();
        findAndClickItemElement(addButtonPath, firstItem);

        // check that cart is not empty
        assertTrue(driver.findElements(By.xpath(emptyCartViewPath)).isEmpty(), "Items in cart are empty");

        // check that item in cart is correct
        assertTrue(findItemElement(priceCartPath, firstItem).getText().contains(price1),
                "Incorrect price of added item");
        assertTrue(findItemElement(titleCartPath, firstItem).getText().contains(title1),
                "Incorrect title of added item");
        assertEquals(extractNumber(findItemElement(quantityCartPath, firstItem).getText(), quantityCartRegex),
                1,
                "Incorrect quantity of added item");

        // remove item from cart
        findAndClick(removeItemButtonPath);

        // verify that cart is empty
        List<WebElement> itemsInCart = driver.findElements(By.xpath(itemFieldPath));
        WebElement emptyCart = driver.findElement(By.xpath(emptyCartViewPath));
        assertFalse(!itemsInCart.isEmpty() && !emptyCart.getText().isEmpty(), "Cart is not empty");

        // checking for total price
        assertEquals(extractNumber(driver.findElement(By.className(totalCartPriceClass)).getText(), totalPriceRegex),
                0,
                "Total price is not empty");
        // looking for button CHECKOUT
        assertTrue(driver.findElements(By.xpath(checkoutButtonPath)).isEmpty(),
                "Checkout button is still visible");
    }
}
