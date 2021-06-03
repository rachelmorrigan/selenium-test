package litecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class MainPageTests {
    private WebDriver webDriver;

    @BeforeTest
    public void beforeTest(){
        webDriver = new FirefoxDriver();
    }

    @Test
    public void checkProducts(){
        webDriver.get("http://localhost/litecart/");
        webDriver.findElements(By.cssSelector("div.content div.box li.product.column.shadow.hover-light")).forEach(webElement -> {
            List<WebElement> webElements = webElement.findElements(By.cssSelector("a div.image-wrapper div[class ^=sticker]"));
            assert webElements.size() == 1 : "Кол-во стикеров на товаре не равно 1";
        });
    }

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
