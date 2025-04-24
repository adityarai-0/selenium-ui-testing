package tests;

import config.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get(ConfigManager.getInstance().getBaseUrl());
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded properly");
    }

    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        // Login with valid credentials
        HomePage homePage = loginPage.login("validUser", "validPassword");

        // Verify login was successful
        Assert.assertTrue(homePage.isPageLoaded(), "Home page is not loaded after login");
        Assert.assertTrue(homePage.isNavigationMenuDisplayed(), "Navigation menu is not displayed");
        Assert.assertTrue(homePage.getWelcomeMessage().contains("validUser"),
                "Welcome message does not contain the username");
    }

    @Test(description = "Verify error message with invalid credentials")
    public void testInvalidLogin() {
        // Login with invalid credentials
        loginPage.login("invalidUser", "invalidPassword");

        // Verify error message
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password",
                "Error message is not as expected");
    }

    @Test(description = "Verify logout functionality")
    public void testLogout() {
        // Login
        HomePage homePage = loginPage.login("validUser", "validPassword");
        Assert.assertTrue(homePage.isPageLoaded(), "Home page is not loaded after login");

        // Logout
        LoginPage logoutPage = homePage.logout();
        Assert.assertTrue(logoutPage.isPageLoaded(), "Login page is not loaded after logout");
    }
}
