package org.nv.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BookPage extends BasePage {
    private final By quantityControlsLocator = By.className("quantity-controls");
    private final By buyButtonLocator = By.className("buy-now-btn");
    private final By successLocator =  By.xpath("//div[contains(text(), 'Order placed successfully!')]");

    public BookPage(WebDriver driver) {
        super(driver);
    }

    public void selectBookQuantity(int newCount)  {
        WebElement quantityControls = getElementIfVisible(quantityControlsLocator);
        List<WebElement> button = quantityControls.findElements(By.tagName("button"));
        //Waits for + and - to be clickable
        button.forEach(this::isElementClickable);
        WebElement plusButton = button.stream().filter(el -> el.getText().equals("+")).findFirst().orElseThrow();
        for (int j = 0; j < newCount; j++) {
            plusButton.click();
        }
    }

    public void buyBook() {
        driver.findElement(buyButtonLocator).click();
    }

    public boolean isPurchaseSuccessful(){
        return isElementVisible(successLocator);
    }
}
