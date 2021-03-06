package litecart;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.File;
import java.time.ZonedDateTime;

public class CreateAccountTests {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @BeforeClass
    public void beforeTest(){
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 10);
    }

    @Test
    public void createNewAccountTest() {
        webDriver.get("http://localhost/litecart/en/create_account");
        wait.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
        webDriver.findElement(By.name("firstname")).sendKeys("test");
        webDriver.findElement(By.name("lastname")).sendKeys("test");
        webDriver.findElement(By.name("address1")).sendKeys("test street 25");
        webDriver.findElement(By.name("city")).sendKeys("New York");
        webDriver.findElement(By.name("postcode")).sendKeys("12345");
        webDriver.findElement(By.className("select2-selection__rendered")).click();
        WebElement selectCountry = webDriver.findElement(By.className("select2-search__field"));
        selectCountry.sendKeys("United States");
        selectCountry.sendKeys(Keys.ENTER);
        String email = "test" + ZonedDateTime.now().toEpochSecond() + "@gmail.com";
        String password = "pass";
        webDriver.findElement(By.name("email")).sendKeys(email);
        webDriver.findElement(By.name("phone")).sendKeys("+12345678999");
        webDriver.findElement(By.name("password")).sendKeys(password);
        webDriver.findElement(By.name("confirmed_password")).sendKeys(password);
        webDriver.findElement(By.name("create_account")).click();
        WebElement selectZone = webDriver.findElement(By.name("zone_code"));
        selectZone.click();
        selectZone.findElement(By.cssSelector("option[value='NY']")).click();
        webDriver.findElement(By.name("password")).sendKeys("pass");
        webDriver.findElement(By.name("confirmed_password")).sendKeys("pass");
        webDriver.findElement(By.name("create_account")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.notice.success")));
        webDriver.findElement(By.xpath("//a[.='Logout']")).click();
        webDriver.findElement(By.cssSelector("input[name='email']")).sendKeys(email);
        webDriver.findElement(By.cssSelector("input[name='password']")).sendKeys(password);
        webDriver.findElement(By.cssSelector("button[name='login']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.notice.success")));
        webDriver.findElement(By.xpath("//a[.='Logout']")).click();
    }

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }
}
