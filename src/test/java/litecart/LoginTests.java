package litecart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class LoginTests {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeClass
    public void beforeTest(){
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    @Test
    public void positiveLoginTest(){
        webDriver.get("http://localhost/litecart/admin/");
        webDriver.findElement(By.name("username")).sendKeys("admin");
        webDriver.findElement(By.name("password")).sendKeys("admin");
        webDriver.findElement(By.name("login")).click();
        wait.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Test(dependsOnMethods = "positiveLoginTest" )
    public void checkSideBar(){
        List<String> names = webDriver.findElements(By.xpath("//ul[@id='box-apps-menu']/li/a/span[@class='name']")).stream().map(WebElement::getText).collect(Collectors.toList());
        names.forEach(name -> {
           WebElement webElement = webDriver.findElement(By.xpath("//ul[@id='box-apps-menu']/li/a/span[@class='name' and .='" + name + "']/.."));
           webElement.click();
           WebElement selectedWebElement = webDriver.findElement(By.xpath("//ul[@id='box-apps-menu']/li[@class='selected']"));
           assert webDriver.findElements(By.cssSelector("h1")).size() != 0 : "На странице нет элемента h1";
           List<String> subNames = selectedWebElement.findElements(By.xpath("//ul[@class='docs']/li[not(contains(@class,'selected'))]"))
                   .stream().map(WebElement::getText).collect(Collectors.toList());
           subNames.forEach(subName -> {
               WebElement subElement = webDriver.findElement(By.xpath("//ul[@id='box-apps-menu']/li[@class='selected']/ul[@class='docs']/li/a/span[@class='name' and .='" + subName + "']/.."));
               subElement.click();
               assert webDriver.findElements(By.cssSelector("h1")).size() != 0 : "На странице нет элемента h1";
           });
        });
    }

    @AfterClass
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
