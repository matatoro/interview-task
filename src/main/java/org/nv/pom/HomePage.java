package org.nv.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends BasePage {
    private final By searchBar = By.id("searchTerm");
    private final By searchButton = By.cssSelector("button.search-btn");
    private final By bookViewButton = By.className("book-card-button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void searchBook(String term) {
        assert isElementVisible(searchBar) : "Search bar is not visible on page!";
        driver.findElement(searchBar).sendKeys(term);
        driver.findElement(searchButton).click();
    }

    public boolean areResultsShownForTerm(String term) {
        By h3 = By.tagName("h3");
        assert (isElementVisible(h3)) : "Could not find any books on page!";
        List<WebElement> h3List = driver.findElements(h3);
        return h3List.stream().anyMatch(element -> element.getText().toLowerCase().contains(term));
    }

    public void selectFirstAvailableBook() {
        if (isElementVisible(bookViewButton))
        {
            List<WebElement> viewButtons = driver.findElements(bookViewButton);
            viewButtons.get(0).click();
        }
    }
}
