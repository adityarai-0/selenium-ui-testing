package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(css = ".navigation-menu")
    private WebElement navigationMenu;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public String getWelcomeMessage() {
        return getElementText(welcomeMessage);
    }

    public LoginPage logout() {
        clickElement(logoutButton);
        return new LoginPage(driver);
    }

    public boolean isNavigationMenuDisplayed() {
        return isElementDisplayed(navigationMenu);
    }

    @Override
    public boolean isPageLoaded() {
        return isElementDisplayed(welcomeMessage) &&
                isElementDisplayed(logoutButton);
    }
}
