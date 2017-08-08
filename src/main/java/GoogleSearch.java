
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
//comment the above line and uncomment below line to use Chrome
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class GoogleSearch {


    public static void main(String[] args) throws InterruptedException {


        // System.setProperty("webdriver.gecko.driver", "/valentin");


        PhantomJsDriverManager.getInstance().setup();

        WebDriver driver = new PhantomJSDriver();

        String url = "https://play.google.com/store/search?q=calculator&c=apps";

        driver.get(url);

        Long value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");

        Long currentHeight = 0L;

        while (!value.equals(currentHeight)) {

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + value + ");");



            currentHeight = value;

            System.out.println("Sleeping... wleepy");



            Thread.sleep(5000);


            value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        }


        String content = driver.getPageSource();


        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("text.html"), "utf-8"));
            writer.write(content);


        } catch (IOException ex) {

            ex.getMessage();


        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }

            } catch (Exception ex) {

            }
        }


        System.out.println("Tittle:" + driver.getTitle());
        driver.close();

    }
}
