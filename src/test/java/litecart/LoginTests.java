package litecart;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTests {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeTest
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

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
