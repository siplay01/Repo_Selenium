package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Homework_12 {

    @Test
    public void feelsLikeTemp() {

        /**
         *  Testcase 1: Verify the feels-like temp value is between low and high temp values at any zipcode
         *  1. Go to "https://www.darksky.net"
         *  2. Get text value for feels like temperature and store value in a String
         *  3. Remove the degree symbol
         *  4. Convert String to int
         *  5. Repeat the steps from 2 to 4 for low temperature
         *  6. Repeat the steps from 2 to 4 for high temperature
         *  7. Compare the values and if feels like temp is in the range assign true value to a boolean
         *  8. Assert if boolean is true - if not throw a message "Feels like temperature is out of range"
         */

        String testZip = "96704";
        boolean isTempInRange = false;

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.darksky.net");

        driver.findElement(By.xpath("//input[@type='text']")).clear();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys(testZip);

        try {
            driver.findElement(By.className("searchButton")).click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        sleepFor2Sec();

        String feelsLikeTemp = driver.findElement(By.xpath("//span[@class='feels-like-text']")).getText();
        String lowTemp = driver.findElement(By.xpath("//span[@class='low-temp-text']")).getText();
        String highTemp = driver.findElement(By.xpath("//span[@class='high-temp-text']")).getText();

        String feelsLikeT = feelsLikeTemp.substring(0, (feelsLikeTemp.length()-1));
        String lowT = feelsLikeTemp.substring(0, (lowTemp.length()-1));
        String highT = feelsLikeTemp.substring(0, (highTemp.length()-1));

        int low = Integer.parseInt(lowT);
        int feelsLike = Integer.parseInt(feelsLikeT);
        int high = Integer.parseInt(highT);

        if (feelsLike >= low && feelsLike <= high) {
            isTempInRange = true;
        }

        Assert.assertTrue(isTempInRange, "Feels like temperature is out of range.");

        driver.quit();
    }

    @Test
    public void blogDates() {

        /**
         *  Testcase 2: Verify the dates on the Blog Page page appears in reverse chronological order
         *  1. Go to "https://www.darksky.net"
         *  2. Navigate to the end of the page and click on Blog hyper text
         *  3. Locate date value of the posts and store all in a list
         *  4. Convert List of Strings to List of Date
         *  5. Copy the List and sort in reverse chronological order
         *  6. Assert if the Lists are equal - if not throw a message "The dates on the Blog Page page appears NOT in reverse chronological order."
         */

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.darksky.net");
        driver.manage().window().maximize();

        WebElement element  = driver.findElement(By.xpath("//a[contains(@href, 'apple.com')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        sleepFor2Sec();

        try {
            driver.findElement(By.xpath("//a[contains(@href, 'blog.darksky')]")).click();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        sleepFor2Sec();

        List<WebElement> datesList = driver.findElements(By.xpath("//time[@itemprop='datePublished']"));

        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

        List<Date> originalList = new ArrayList<>();
        List<Date> copyList = new ArrayList<>();

        for (WebElement w : datesList) {
            Date date = null;
            try {
                date = format.parse(w.getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            originalList.add(date);
                copyList.add(date);
        }

        copyList.sort(Collections.reverseOrder());

        Assert.assertEquals(originalList, copyList, "The dates on the Blog Page page appears NOT in reverse chronological order.");

        driver.quit();
    }

    @Test
    public void tempConversion() {

        /**
         *  Testcase 3: Verify the temperature value converts as expected as the the unit selected
         *  1. Go to "https://www.darksky.net"
         *  2. Get value of current temperature and store the value in a String 1
         *  3. Remove the degree symbol
         *  4. Convert String to int
         *  5. Navigate to the Drop Down menu at the upper right corner and click
         *  6. Click on the "˚C, mph"
         *  7. Repeat the steps from 2 to 4 and store the value in a String 2
         *  8. Convert String 2 to Fahrenheit (Celsius * 9 / 5) + 32
         *  9. Assert if the Lists are equal - if not throw a message "The dates on the Blog Page page appears NOT in reverse chronological order."
         */

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.darksky.net");

        WebElement tempLocator = driver.findElement(By.xpath("//span[@class='summary swap']"));
        String tempInF = tempLocator.getText();
        tempInF = tempInF.substring(0, (tempInF.indexOf('˚')));
        int fahTempInt = Integer.parseInt(tempInF);

        driver.findElement(By.xpath("//div[@id='header']//b[@class='button']//..//span[contains(text(), 'F')]")).click();
        driver.findElement(By.xpath("(//div[@class='selectric-scroll'])[1]//li[@data-index='3']")).click();
        sleepFor2Sec();

        WebElement newTempLocator = driver.findElement(By.xpath("//span[@class='summary swap']"));
        String tempInC = newTempLocator.getText();
        tempInC = tempInC.substring(0, (tempInC.indexOf('˚')));

        int celTempInt = Integer.parseInt(tempInC);
        int celToFah = (celTempInt * 9 / 5) + 32;

        Assert.assertEquals(fahTempInt, celToFah, "Conversion is Not correct.");

        driver.quit();
    }

    public void sleepFor2Sec() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
