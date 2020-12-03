package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Homework_14 {

    WebDriver driver;

    public void openBrowser (String url) {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void openBrowser () {
        driver.quit();
    }

    private void sleepFor(int sec) {

        try {
            Thread.sleep((sec * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectFromDropdown(String id, String element) {
        WebElement roomsElem = driver.findElement(By.id(id));
        Select roomsEl = new Select(roomsElem);
        roomsEl.selectByVisibleText(element);
    }

    @Test
    public void verifyTemp() {

        /**
         * Testcase-1: Verify low/high temp on Today timeline
         * Go to darksky.net
         * Get the today's values of the lowest and the highest temperature from the weekly summary range bar
         * Click on the range bar
         * From the extended detailed menu get the today's values of the lowest and the highest temperature
         * Verify the lowest values are equal and the highest values are equal.
         * If NOT throw the message - "Low/high temp on Today timeline are NOT equal"
         */

        openBrowser("https://www.darksky.net");
        sleepFor(2);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,700)", "");
        sleepFor(2);

        WebElement minBarTemp = driver.findElement(By.xpath("//a[@data-day=0]//span[@class='minTemp']"));
        String minBarTempS = minBarTemp.getText();

        WebElement maxBarTemp = driver.findElement(By.xpath("//a[@data-day=0]//span[@class='maxTemp']"));
        String maxBarTempS = maxBarTemp.getText();

        WebElement tempRangeBar = driver.findElement(By.xpath("//span[contains(text(), 'Today')]"));
        tempRangeBar.click();

        WebElement lowTemp = driver.findElement(By.xpath("//div[@class='dayDetails revealed']//span[@class='highTemp swip']//span[@class='temp']"));
        String lowTempS = lowTemp.getText();

        WebElement highTemp = driver.findElement(By.xpath("//div[@class='dayDetails revealed']//span[@class='lowTemp swap']//span[@class='temp']"));
        String highTempS = highTemp.getText();

        boolean isMinTempEqual = minBarTempS.equals(lowTempS);
        boolean isMaxTempEqual = maxBarTempS.equals(highTempS);
        boolean result = false;
        if (isMinTempEqual==true && isMaxTempEqual==true) {
            result = true;
        }

        Assert.assertTrue(result, "Low/high temp on Today timeline are NOT equal");
    }

    @Test
    public void nightsOnBriefcase() {

        /**
         * Testcase-2: Verify the number of nights on black briefcase
         * Go to hotels.com page
         * Select CheckIn as Tomorrow
         * Select CheckOut as 7 days after Tomorrow
         * Verify the number of nights on black briefcase
         * If NOT match throw the message "The number of nights on the black briefcase does NOT match"
         */

        int days = 7;

        openBrowser("https://www.hotels.com");
        sleepFor(2);

        driver.findElement(By.name("q-localised-check-in")).click();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);

        driver.findElement(By.xpath("//td[@data-date='" + dateFormat.format(cal.getTime()) + "']")).click();

        driver.findElement(By.name("q-localised-check-out")).click();

        cal.add(Calendar.DAY_OF_MONTH, days);
        driver.findElement(By.xpath("//td[@data-date='" + dateFormat.format(cal.getTime()) + "']")).click();

        String strNights = driver.findElement(By.xpath("//span[@class='widget-query-num-nights']")).getText();
        int nights = Integer.parseInt(strNights);

        Assert.assertEquals(days, nights, "The number of nights on the black briefcase does NOT match");
    }

    @Test
    public void enteredEqualsSelected() {

        /**
         * Testcase-3: Enter the user details as mentioned
         * Go to hotels.com page
         * Enter data:
         *      Rooms - 1
         *      Adult - 1
         *      Children - 2 (Ages: 1, 2)
         *
         * Click "Search" button
         * Verify user details entered on Home page match on Search page
         * If NOT throw the message "Entered/selected user details on Homepage are NOT match on Search page"
         */

        String rooms = "1";
        String adults = "1";
        String children = "2";
        String age1 = "1";
        String age2 = "2";
        String toEnter = "San Francisco";
        String toSelect = "San Francisco, California, United States of America";
        boolean isEnteredEqualsSelected = false;

        openBrowser("https://www.hotels.com");
        sleepFor(2);

        driver.findElement(By.id("qf-0q-destination")).clear();
        driver.findElement(By.id("qf-0q-destination")).sendKeys(toEnter);
        sleepFor(2);

        By autoSuggestionsLocator = By.xpath("//div[@class='autosuggest-category-result']");
        List<WebElement> suggestions = driver.findElements(autoSuggestionsLocator);

        for (WebElement suggestion : suggestions) {
            String suggestionText = suggestion.getText();
            if (toSelect.equalsIgnoreCase(suggestionText)) {
                suggestion.click();
                break;
            }
        }
        sleepFor(1);
        selectFromDropdown("qf-0q-rooms", rooms);
        selectFromDropdown("qf-0q-room-0-adults", adults);
        selectFromDropdown("qf-0q-room-0-children", children);
        sleepFor(1);
        selectFromDropdown("qf-0q-room-0-child-0-age", age1);
        selectFromDropdown("qf-0q-room-0-child-1-age", age2);
        sleepFor(1);

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        sleepFor(4);
        String roomsSelected = driver.findElement(By.xpath("//select[@id='q-rooms']/option[@selected='selected']")).getText();
        String adultsSelected = driver.findElement(By.xpath("//select[@id='q-room-0-adults']/option[@selected='selected']")).getText();
        String childrenSelected = driver.findElement(By.xpath("//select[@id='q-room-0-children']/option[@selected='selected']")).getText();
        String age1Selected = driver.findElement(By.xpath("//select[@id='q-room-0-child-0-age']/option[@selected='selected']")).getText();
        String age2Selected = driver.findElement(By.xpath("//select[@id='q-room-0-child-1-age']/option[@selected='selected']")).getText();

        boolean isRoomsEqual = rooms.equals(roomsSelected);
        boolean isAdultsEqual = adults.equals(adultsSelected);
        boolean isChildrenEqual = children.equals(childrenSelected);
        boolean isAge1Equal = age1.equals(age1Selected);
        boolean isAge2Equal = age2.equals(age2Selected);

        if (isRoomsEqual && isAdultsEqual && isChildrenEqual && isAge1Equal && isAge2Equal)
            isEnteredEqualsSelected = true;

        Assert.assertTrue(isEnteredEqualsSelected, "Entered/selected user details on Homepage are NOT match on Search page");
    }
}
