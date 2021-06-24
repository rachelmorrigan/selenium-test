package litecart.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {

    @FindBy(css = "div#box-most-popular li.product.column.shadow.hover-light")
    private List<WebElement> mostPopularItems;
    @FindBy(css = "div#box-campaigns li.product.column.shadow.hover-light")
    private List<WebElement> campaignsItems;
    @FindBy(css = "div#box-latest-products li.product.column.shadow.hover-light")
    private List<WebElement> latestItems;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open(){
        driver.get("http://localhost/litecart/");
        return this;
    }

    public List<WebElement> getAllMostPopularProducts(){
        return mostPopularItems;
    }

    public ProductPage openProductPage(WebElement product){
        product.click();
        return new ProductPage(driver);
    }
}
