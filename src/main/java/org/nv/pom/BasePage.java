package org.nv.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    private final int secondsToWait = 5;
    protected WebDriver driver;
    private Wait<WebDriver> wait;

    BasePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
        this.driver = driver;
    }

    protected boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    protected WebElement getElementIfVisible(By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        assert (element != null) : String.format("Could not find element with locator %s in the alloted timeout!", locator.toString());
        return element;
    }

    protected boolean isElementClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}
