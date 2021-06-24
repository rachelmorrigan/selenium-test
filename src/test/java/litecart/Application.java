package litecart;

import litecart.Pages.CartPage;
import litecart.Pages.MainPage;
import org.openqa.selenium.WebDriver;

public class Application {
    private MainPage mainPage;
    private CartPage cartPage;
    public Application(WebDriver driver){
        mainPage = new MainPage(driver);
        cartPage = new CartPage(driver);
    }
    public MainPage openMainPage(){
        return mainPage.open();
    }
    public CartPage openCartPage(){
        return cartPage.open();
    }
}
