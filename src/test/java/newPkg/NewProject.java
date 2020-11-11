package newPkg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


public class NewProject {

    @Test
    public void search() {

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement searchField = driver.findElement(By.name("q"));

        searchField.sendKeys("Hello World");
        searchField.submit();

        WebElement newLink = driver.findElements(By.linkText("Videos")).get(0);
        newLink.click();

        driver.close();
    }
}
