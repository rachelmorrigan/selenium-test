package litecart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {
    @FindBy(css = "li.item")
    private List<WebElement> productList;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage open(){
        driver.get("http://localhost/litecart/en/checkout");
        return this;
    }

    public List<WebElement> getAllProductsInCart(){
        return productList;
    }
    public void deleteProduct(WebElement product){
        String itemName = product.findElement(By.cssSelector("strong")).getText();
        WebElement dtaTableItem = driver.findElement(
                By.xpath("//div[@id='order_confirmation-wrapper']//td[@class='item'][.='" +itemName+ "']"));
        product.findElement(By.name("remove_cart_item")).click();
        wait.until(ExpectedConditions.stalenessOf(dtaTableItem));
    }
}
