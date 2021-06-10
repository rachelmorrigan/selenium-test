package litecart.AdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CountriesTests extends AdminBase {

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

    private void checkAlphabeticalOrder(List<String> listToCheck){
        List<String> listToCompare = new ArrayList<>(listToCheck);
        listToCompare.sort(Comparator.naturalOrder());
        assert listToCheck.equals(listToCompare) : "Страны расположены не в алфавитном порядке";
    }
}
