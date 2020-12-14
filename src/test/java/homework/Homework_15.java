package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Homework_15 {

    @Test
    public void multipleWindows() {

        /**
         * 1. Launch the naurki application
         * 2. Close the advertisement windows (Do NOT close the main window)
         * 3. Type 'testing' in Skills, Designation and Company
         * 4. Select 'testing tools' from auto suggestion
         * 5. Click Search button
         * 6. Verify 'Testing Tools Jobs' appears above the search results.
         *
         */

        String toSelect = "testing tools";
        By titleElm = By.xpath("//h1[contains(text(), 'Testing Tools Jobs')]");
        By searchBtn = By.xpath("//button[contains(text(), 'Search')]");

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.naukri.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String lHandle = driver.getWindowHandle();
        Set<String> allHandels = driver.getWindowHandles();

        for (String handel : allHandels) {
            driver.switchTo().window(handel);
            if (!handel.equals(lHandle)) {
                driver.close();
            }
        }

        driver.switchTo().window(lHandle);

        driver.findElement(By.name("keyword")).sendKeys("testing");
        sleepFor(2);
        By autoSuggestionsLocator = By.xpath("//li[@class='sugTouple']");
        List<WebElement> suggestions = driver.findElements(autoSuggestionsLocator);
        for (WebElement suggestion : suggestions) {
            String suggestionText = suggestion.getText();
            if (toSelect.contains(suggestionText)) {
                suggestion.click();
                break;
            }
        }

        driver.findElement(searchBtn).click();
        sleepFor(2);
        boolean isPresent = driver.findElement(titleElm).getText().toLowerCase().contains(toSelect);
        Assert.assertTrue(isPresent);

        driver.quit();
    }

    public void sleepFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
