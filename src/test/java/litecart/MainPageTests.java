package litecart;

import litecart.Pages.CartPage;
import litecart.Pages.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

public class MainPageTests {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeTest
    public void beforeTest(){
        webDriver = new FirefoxDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    @Test
    public void checkProducts(){
        webDriver.get("http://localhost/litecart/");
        webDriver.findElements(By.cssSelector(".products .image-wrapper")).forEach(webElement -> {
            List<WebElement> webElements = webElement.findElements(By.cssSelector("div[class ^=sticker]"));
            assert webElements.size() == 1 : "Кол-во стикеров на товаре не равно 1";
        });
    }

    @Test
    public void checkCart(){
        IntStream.range(0,3).forEach((x) ->{
        webDriver.get("http://localhost/litecart/");
        webDriver.findElements(By.cssSelector("div.content div.box li.product.column.shadow.hover-light")).get(x).click();
            List<WebElement> requiredSize = webDriver.findElements(By.name("options[Size]"));
            if(!requiredSize.isEmpty()){
                new Select(requiredSize.get(0)).selectByIndex(1);
            }
        WebElement webElement = webDriver.findElement(By.cssSelector("#cart span.quantity"));
        webDriver.findElement(By.name("add_cart_product")).click();
        wait.until(ExpectedConditions.textToBePresentInElement(webElement, Integer.toString(x+1)));
        });
        webDriver.findElement(By.cssSelector("#cart a.link")).click();
        int itemsCount = webDriver.findElements(By.cssSelector("li.item")).size();
        IntStream.range(0, itemsCount).forEach(x -> {
            WebElement item = webDriver.findElement(By.cssSelector("li.item"));
            String itemName = item.findElement(By.cssSelector("strong")).getText();
            WebElement dtaTableItem = webDriver.findElement(
                    By.xpath("//div[@id='order_confirmation-wrapper']//td[@class='item'][.='" +itemName+ "']"));
            item.findElement(By.name("remove_cart_item")).click();
            wait.until(ExpectedConditions.stalenessOf(dtaTableItem));
        });
    }

    @Test
    public void checkCartNew(){
        Application application = new Application(webDriver);
        IntStream.range(0,3).forEach((x) ->{
            MainPage mainPage = application.openMainPage();
            List<WebElement> products = mainPage.getAllMostPopularProducts();
            mainPage.openProductPage(products.get(x)).addItemToCart(1);
        });
        CartPage cartPage = application.openCartPage();
        cartPage.getAllProductsInCart().forEach((x) -> {
            cartPage.deleteProduct(cartPage.getAllProductsInCart().get(0));
        });
    }

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
