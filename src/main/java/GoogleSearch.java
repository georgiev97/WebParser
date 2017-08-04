
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//comment the above line and uncomment below line to use Chrome
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.net.URL;

public class GoogleSearch {


    public static void main(String[] args) throws InterruptedException {
        WebDriver driver;

        System.setProperty("webdriver.gecko.driver", "/valentin");

        driver= new FirefoxDriver();

        String url = "https://play.google.com/store/search?q=calculator&c=apps";

        driver.get(url);

        JavascriptExecutor js=(JavascriptExecutor) driver;

        String s = "window.scrollTo(0,document.body.scrollHeight);";

        js.executeScript(s);

        Thread.sleep(10*1000);

         js=(JavascriptExecutor) driver;

         s = "window.scrollTo(0,document.body.scrollHeight);";

        js.executeScript(s);

        Thread.sleep(10*1000);

         js=(JavascriptExecutor) driver;

         s = "window.scrollTo(0,document.body.scrollHeight);";

        js.executeScript(s);

        Thread.sleep(10*1000);






        String content = driver.getPageSource();


        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("text.html"), "utf-8"));
            writer.write(content);


        } catch (IOException ex) {


        } finally {
            try {
                writer.close();

            } catch (Exception ex) {

            }
        }


        System.out.println("done");
driver.close();

    }
 }
