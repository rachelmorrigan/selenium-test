package litecart.Pages;

import litecart.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends BasePage {

    @FindBy(name = "add_cart_product")
    private WebElement addToCartButton;
    @FindBy(name = "quantity")
    private WebElement quantityInput;
    @FindBy(name = "options[Size]")
    private List<WebElement> sizeSelect;
    @FindBy(css = "#cart span.quantity")
    private WebElement cartItems;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart(int quantity){
        int ItemsInCartBefore = Integer.parseInt(cartItems.getText());
        quantityInput.clear();
        quantityInput.sendKeys(Integer.toString(quantity));
        if(sizeSelect.size() != 0){
            new Select(sizeSelect.get(0)).selectByIndex(1);
        }
        addToCartButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(cartItems, Integer.toString(ItemsInCartBefore+1)));
    }

}
