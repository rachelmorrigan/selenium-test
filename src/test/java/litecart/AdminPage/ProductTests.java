package litecart.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.time.ZonedDateTime;

public class ProductTests extends AdminBase {

    @Test
    public void createNewProductTest() {
        webDriver.get("http://localhost/litecart/admin/");
        webDriver.findElement(By.xpath("//li[@id='app-']//span[.='Catalog']")).click();
        webDriver.findElement(By.xpath("//a[.=' Add New Product']")).click();

        webDriver.findElement(By.xpath("//input[@name='status'][@value='1']")).click();
        String productName = "test product " + ZonedDateTime.now().toEpochSecond();
        webDriver.findElement(By.name("name[en]")).sendKeys(productName);
        webDriver.findElement(By.name("code")).sendKeys("12345");
        webDriver.findElement(By.xpath("//input[@name='categories[]'][@value='2']")).click();
        webDriver.findElement(By.xpath("//input[@name='product_groups[]'][@value='1-3']")).click();
        WebElement quantity = webDriver.findElement(By.name("quantity"));
        quantity.sendKeys(Keys.UP);
        Select soldStatus = new Select(webDriver.findElement(By.name("sold_out_status_id")));
        soldStatus.selectByIndex(2);
        File file = new File("src/test/resources/duck.jpg");
        webDriver.findElement(By.name("new_images[]")).sendKeys(file.getAbsolutePath());
        webDriver.findElement(By.name("date_valid_from")).sendKeys("02032001");
        webDriver.findElement(By.name("date_valid_to")).sendKeys("01012030");

        webDriver.findElement(By.xpath("//a[.='Information']")).click();
        Select manufacturer = new Select(webDriver.findElement(By.name("manufacturer_id")));
        manufacturer.selectByIndex(1);
        webDriver.findElement(By.name("short_description[en]")).sendKeys("test description");

        webDriver.findElement(By.xpath("//a[.='Prices']")).click();
        WebElement purchasePrice = webDriver.findElement(By.name("purchase_price"));
        purchasePrice.clear();
        purchasePrice.sendKeys("112");
        new Select(webDriver.findElement(By.name("purchase_price_currency_code"))).selectByValue("EUR");
        webDriver.findElement(By.name("prices[EUR]")).sendKeys("112");
        webDriver.findElement(By.name("prices[USD]")).sendKeys("100");

        webDriver.findElement(By.name("save")).click();
        assert webDriver.findElements(By.cssSelector("table.dataTable tr.row a"))
                .stream().map(WebElement::getText).anyMatch(x -> x.equals(productName)) : "Нового продукта нет в каталоге";
    }
}
