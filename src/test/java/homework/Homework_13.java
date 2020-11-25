package homework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Homework_13 {

    @Test
    public  void yahooNotification () {

        /**
         * Testcase-1: User should be able to click on the yahoo notification
         * 1. Launch yahoo.com
         * 2. Move mouse to bell icon
         * 3. User should be able to click the first notification
         */

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.yahoo.com");
        sleepFor2Sec();

        WebElement bellIcon = driver.findElement(By.id("ybarNotificationMenu"));
        Actions act = new Actions(driver);
        act.moveToElement(bellIcon).build().perform();
        sleepFor2Sec();

        String currentUrl = driver.getCurrentUrl();
        WebElement firstNotif = driver.findElement(By.xpath("//li[contains(@class, 'yns-container')][2]"));
        firstNotif.click();
        sleepFor2Sec();
        String notifPageUrl = driver.getCurrentUrl();

        Assert.assertNotEquals(currentUrl, notifPageUrl, "User is NOT able to click on the first notification");

        driver.quit();
    }

    @Test
    public void facebookError() {

        /**
         * Testcase-2: User should get error on invalid date of birth
         * 1. Launch facebook.com
         * 2. Click 'Create new Account' button
         * 3. Enter fname as Firstname
         * 4. Enter lname as Lastname
         * 5. "abcd@test.com" as email address
         * 6. "abcd@1234" as New Password
         * 7. Enter your "Jan 4 1998" as birth date
         * 8. Click the 'Sign Up' button
         * 9. Verify user see the error msg for gender. (Please choose a gender. You can change who can see this later.)
         */

        String fname = "fname";
        String lname = "lname";
        String email = "abcd@test.com";
        String pass = "abcd@1234";
        String year = "1998";
        String month = "Jan";
        String day = "4";

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.facebook.com");

        driver.findElement(By.xpath("//a[contains(@data-testid, 'registration-form')]")).click();
        sleepFor2Sec();

        driver.findElement(By.name("firstname")).sendKeys(fname);
        driver.findElement(By.name("lastname")).sendKeys(lname);
        driver.findElement(By.name("reg_email__")).sendKeys(email);
        sleepFor2Sec();
        driver.findElement(By.name("reg_email_confirmation__")).sendKeys(email);
        driver.findElement(By.name("reg_passwd__")).sendKeys(pass);

        WebElement monthElem = driver.findElement(By.id("month"));
        Select monthSelect = new Select(monthElem);
        monthSelect.selectByVisibleText(month);

        WebElement dayElem = driver.findElement(By.id("day"));
        Select daySelect = new Select(dayElem);
        daySelect.selectByVisibleText(day);

        WebElement yearElem = driver.findElement(By.id("year"));
        Select yearSelect = new Select(yearElem);
        yearSelect.selectByVisibleText(year);

        driver.findElement(By.name("websubmit")).click();
        sleepFor2Sec();

        WebElement theErrorMsg = driver.findElement(By.xpath("//div[contains(text(), 'Please choose a gender')]"));
        boolean isTheErrorMsgDisplayed = theErrorMsg.isDisplayed();

        Assert.assertTrue(isTheErrorMsgDisplayed, "User cannot see the message 'Please choose a gender. You can change who can see this later.'");

        driver.quit();
    }

    public void sleepFor2Sec () {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
