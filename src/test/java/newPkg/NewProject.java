package newPkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class NewProject {

    @Test
    public void sample() {

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.facebook.com");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();

        Assert.assertEquals(1, 1);
    }
}
