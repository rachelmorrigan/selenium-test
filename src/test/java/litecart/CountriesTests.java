package litecart;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CountriesTests {
    private WebDriver webDriver;

    @BeforeTest
    public void beforeTest(){
        webDriver = new EdgeDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        webDriver.get("http://localhost/litecart/admin/");
        webDriver.findElement(By.name("username")).sendKeys("admin");
        webDriver.findElement(By.name("password")).sendKeys("admin");
        webDriver.findElement(By.name("login")).click();
        wait.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
    }

    @Test
    public void checkCountries(){
        webDriver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<WebElement> countries = webDriver.findElements(By.xpath("//form[@name='countries_form']//tr[@class='row']/td[5]"));
        List<String> names = countries.stream().map(WebElement::getText).collect(Collectors.toList());
        checkAlphabeticalOrder(names);
        List<WebElement> countriesWithZones = webDriver.findElements(By.xpath("//form[@name='countries_form']//tr[@class='row']/td[6][not(contains(.,'0'))]/../td[5]/a"));
        List<String> countriesWithZonesLinks = countriesWithZones.stream().map(x -> x.getAttribute("href")).collect(Collectors.toList());
        countriesWithZonesLinks.forEach(country -> {
            webDriver.get(country);
            List<WebElement> countriesLink = webDriver.findElements(By.xpath("//table[@id='table-zones']//tr/td[3]"));
            List<String> subNames = countriesLink.stream().map(WebElement::getText).filter(x -> !x.equals("")).collect(Collectors.toList());
            checkAlphabeticalOrder(subNames);
        });
    }

    @Test
    public void checkGeoZones() {
        webDriver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> geoZones = webDriver.findElements(By.xpath("//form[@name='geo_zones_form']//tr[@class='row']//td[3]/a"));
        List<String> geoZonesLink = geoZones.stream().map(x -> x.getAttribute("href")).collect(Collectors.toList());
        geoZonesLink.forEach(geoZone -> {
            webDriver.get(geoZone);
            List<WebElement> zoneOptions = webDriver.findElements(By.xpath("//table[@id='table-zones']//select[contains(@name, 'zone_code')]/option[@selected='selected']"));
            List<String> zoneNames = zoneOptions.stream().map(WebElement::getText).collect(Collectors.toList());
            checkAlphabeticalOrder(zoneNames);
        });
    }

    @AfterTest
    public void afterTest(){
        webDriver.quit();
        webDriver = null;
    }

    private void checkAlphabeticalOrder(List<String> listToCheck){
        List<String> listToCompare = new ArrayList<>(listToCheck);
        listToCompare.sort(Comparator.naturalOrder());
        assert listToCheck.equals(listToCompare) : "Страны расположены не в алфавитном порядке";
    }
}
