import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class BaseTest {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeTest
    public void beforeTest(){
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    @Test
    public void Test(){
        webDriver.get("https://www.google.com/");
        webDriver.findElement(By.name("q")).sendKeys("webdriver");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnK")));
        btn.click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
