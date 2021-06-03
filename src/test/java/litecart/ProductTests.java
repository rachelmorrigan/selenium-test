package litecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.TestException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProductTests {
    private WebDriver webDriver;

    @Parameters("browser")
    @BeforeTest()
    public void beforeTestMultipleBrowsers(String browser){
        switch (browser){
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            case "edge":
                webDriver = new EdgeDriver();
                break;
            default:
                throw new TestException("Неверное название браузера или данный браузер не поддерживается");
        }
    }

    @Test
    public void checkProductInformation(){
        webDriver.get("http://localhost/litecart/");
        WebElement duck = webDriver.findElement(By.cssSelector("div#box-campaigns a.link[title='Yellow Duck']"));
        String duckName = duck.findElement(By.cssSelector("div.name")).getText();

        WebElement duckRegularPrice = duck.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        String duckRegularPriceValue = duckRegularPrice.getText();
        String duckRegularPriceDecoration = duckRegularPrice.getCssValue("text-decoration-line");
        String duckRegularPriceColor = duckRegularPrice.getCssValue("color");
        String duckRegularPriceSize = duckRegularPrice.getCssValue("font-size");

        WebElement duckCampaignPrice = duck.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));
        String duckCampaignPriceValue = duckCampaignPrice.getText();
        String duckCampaignPriceDecoration = duckCampaignPrice.getCssValue("font-weight");
        String duckCampaignPriceColor = duckCampaignPrice.getCssValue("color");
        String duckCampaignPriceSize = duckCampaignPrice.getCssValue("font-size");

        webDriver.get("http://localhost/litecart/en/rubber-ducks-c-1/subcategory-c-2/yellow-duck-p-1");
        WebElement duckSelected = webDriver.findElement(By.cssSelector("div#box-product"));
        String duckSelectedName = duckSelected.findElement(By.cssSelector("h1")).getText();

        WebElement duckSelectedRegularPrice = duckSelected.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        String duckSelectedRegularPriceValue = duckSelectedRegularPrice.getText();
        String duckSelectedRegularPriceDecoration = duckSelectedRegularPrice.getCssValue("text-decoration-line");
        String duckSelectedRegularPriceColor = duckSelectedRegularPrice.getCssValue("color");
        String duckSelectedRegularPriceSize = duckSelectedRegularPrice.getCssValue("font-size");

        WebElement duckSelectedCampaignPrice = duckSelected.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));
        String duckSelectedCampaignPriceValue = duckSelectedCampaignPrice.getText();
        String duckSelectedCampaignPriceDecoration = duckSelectedCampaignPrice.getCssValue("font-weight");
        String duckSelectedCampaignPriceColor = duckSelectedCampaignPrice.getCssValue("color");
        String duckSelectedCampaignPriceSize = duckSelectedCampaignPrice.getCssValue("font-size");

        assertThat(duckName).as("Название товара не совпадает").isEqualTo(duckSelectedName);
        assertThat(duckRegularPriceValue).as("Обычная цена товара не совпадает").isEqualTo(duckSelectedRegularPriceValue);
        assertThat(duckCampaignPriceValue).as("Аукционная цена товара не совпадает").isEqualTo(duckSelectedCampaignPriceValue);
        assertThat(duckRegularPriceDecoration).isEqualTo(duckSelectedRegularPriceDecoration).as("Шрифт обычной цены незачеркнутый").isEqualTo("line-through");
        assertThat(Integer.parseInt(duckCampaignPriceDecoration)).as("Шрифт акционной цены нежирный").isGreaterThan(0);
        assertThat(Integer.parseInt(duckSelectedCampaignPriceDecoration)).as("Шрифт акционной цены нежирный").isGreaterThan(0);
        checkColor(duckRegularPriceColor, Color.GREY);
        checkColor(duckSelectedRegularPriceColor, Color.GREY);
        checkColor(duckCampaignPriceColor, Color.RED);
        checkColor(duckSelectedCampaignPriceColor, Color.RED);
        assertThat(duckRegularPriceSize).as("Обычная цена крупнее, чем акционная").isLessThan(duckCampaignPriceSize);
        assertThat(duckSelectedRegularPriceSize).as("Обычная цена крупнее, чем акционная").isLessThan(duckSelectedCampaignPriceSize);

    }

    private void checkColor(String colorValue, Color color){
        Pattern pattern = Pattern.compile(".*\\((.*?)\\)");
        Matcher matcher = pattern.matcher(colorValue);
        if (matcher.find())
        {
            List<String> rgb = Arrays.asList(matcher.group(1).split(", "));
            switch (color){
                case GREY:
                    assertThat(rgb.get(0)).as("Цвет не серый").isEqualTo(rgb.get(1)).isEqualTo(rgb.get(2));
                    break;
                case RED:
                    assertThat(rgb.get(1)).as("Цвет не красный").isEqualTo(rgb.get(2)).isNotEqualTo(rgb.get(0));
                    break;
            }
        }
        else throw new AssertionError("Неверный формат значения" + colorValue);
    }
    enum Color{
        RED,
        GREY
    }
}
