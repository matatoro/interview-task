package org.nv.seleniumTests;

import org.nv.pom.BookPage;
import org.nv.pom.HomePage;
import org.nv.pom.LoginPage;
import org.nv.helpers.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SeleniumTest {
    WebDriver driver;
    Helper helper = new Helper();

    @BeforeTest()
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(helper.getBaseUrl());
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test()
    public void LoginTest() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.loginWithCredentials(helper.getUser(), helper.getPass());

        boolean addBookButtonShown = loginPage.isAddBookButtonShown();
        Assert.assertTrue(addBookButtonShown, "Login not OK!");
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][]{
                {"gatsby"}
        };
    }
    @Test(dependsOnMethods = {"LoginTest"}, dataProvider = "searchTerms")
    public void SearchBookTest(String term) {
        HomePage homePage = new HomePage(driver);
        homePage.searchBook(term);

        boolean isFound = homePage.areResultsShownForTerm(term);
        Assert.assertTrue(isFound,
                "Search result not found!");
    }

    @Test()
    public void BuyBookTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.selectFirstAvailableBook();
        BookPage bookPage = new BookPage(driver);
        bookPage.selectBookQuantity(1);
        bookPage.buyBook();
        Assert.assertTrue(bookPage.isPurchaseSuccessful(), "Success message was not shown!");
    }

}
