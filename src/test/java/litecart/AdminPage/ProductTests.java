package litecart.AdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void checkProductsLogsTest() {
        webDriver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        List<String> links = webDriver.findElements(By.xpath("//table[@class='dataTable']//td[3]/a[contains(@href,'category_id=1')]")).stream().map(x -> x.getAttribute("href")).collect(Collectors.toList());
        links.forEach(link ->{
            webDriver.get(link);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div#tab-general")));
            List<LogEntry> logs = webDriver.manage().logs().get("browser").getAll();
            if (logs.size()>0){
            for (LogEntry l : webDriver.manage().logs().get("browser").getAll()) {
                System.out.println(l);
            }
            throw new AssertionError("Найдены ошибки в логе браузера");
        }});
    }
}
