package org.nv.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By submitButton = By.cssSelector("button.submit-btn");
    private final By addBookButton = By.cssSelector("button.buy-now-btn");
    private final String loginLinkText = "Login";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        WebElement loginLink = driver.findElement(By.linkText(loginLinkText));
        loginLink.click();
    }

    public void loginWithCredentials(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public boolean isAddBookButtonShown() {
           return isElementVisible(addBookButton);
    }
}
