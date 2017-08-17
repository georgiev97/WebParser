import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {}

//    public static void main(String[] args) throws InterruptedException {
//
//        //just for test
//        //System.setProperty("webdriver.gecko.driver", "/valentin");
//        PhantomJsDriverManager.getInstance().setup();
//
//        String url = "https://www.mwcamericas.com/exhibition/2017-exhibitors/page/23/";
//        String name = "";
//        String country = "list-country";
//        String phone = "";
//        String site = "websitebox";
//        String email = "emailbox";
//        String tags="entity-tags";
//
//        String pageClass="next";
//        String classOrId = "exhibitor";
//        String attribute = "href";
//        String nextPageUrl;
//
//
//        WebDriver web;
//        WebDriver pageDriver = null;
//        List<WebDriver> pages = null;
//        WebDriver driver = new PhantomJSDriver();//new FirefoxDriver();
//
//
//        Company company;
//        List<Company> companies = new ArrayList<>();
//
//        Long value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
//        Long currentHeight = 0L;
//
//
//        List<WebDriver> driverPages = new ArrayList<>();
//
//
//        driver.get(url);
//
//        nextPageUrl=driver.findElement(By.className("next")).getAttribute("href");
//
//        List<WebElement> elements = driver.findElements(By.className(classOrId));
//
//        scrolling(value, currentHeight, driver);
//
//        pagination(driver,elements,driverPages,nextPageUrl,companies);
//
//        getContent(driverPages,companies);
//
//
//
//
//
//
//    }
//
//
//    public static void scrolling(Long value, Long currentHeight, WebDriver driver) throws InterruptedException {
//
//        while (!value.equals(currentHeight)) {
//
//            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + value + ");");
//
//
//            currentHeight = value;
//
//            System.out.println("Sleeping... wleepy");
//
//
//            Thread.sleep(5000);
//
//
//            value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
//        }
//    }
//
//    public static void pagination (WebDriver driver, List<WebElement> elements, List<WebDriver> driverPages, String nextPageUrl, List<Company> companies){
//
//        WebDriver pageDriver;
//        WebElement nextPage;
//        WebElement currentPage;
//        Company company;
//
//        int nextElement =driver.findElements(By.className("next")).size();
//
//        while(nextElement !=0){
//
//            pageDriver = new PhantomJSDriver();
//
//            pageDriver.get(nextPageUrl);
//
//
//            currentPage= pageDriver.findElement(By.className("current"));
//
//
//
//            if (pageDriver.findElements(By.className("next")).size() == 0) {
//
//                nextPage = currentPage;
//
//            }else{
//
//                nextPage= pageDriver.findElement(By.className("next"));
//            }
//
//            nextPageUrl=nextPage.getAttribute("href");
//
//
//            for (WebElement element : elements) {
//
//                String href = element.getAttribute("href");
//
//                company = new Company();
//                company.id = href;
//                companies.add(company);
//
//            }
//
//            driverPages.add(pageDriver);
//
//            pageDriver.quit();
//
//
//        }
//
//
//    }
//
//    public static void getContent(List<WebDriver> driverPages, List<Company> companies){
//
//
//        for (WebDriver page : driverPages) {
//
//            for (Company c : companies) {
//
//                page = new PhantomJSDriver();
//
//                page.get(c.id);
//
//                System.out.println(c.id);
//
//                c.country = page.findElement(By.className("list-country")).getText();
//
//                System.out.println(c.country);
//
//                c.email = page.findElement(By.className("emailbox")).getText();
//
//                System.out.println(c.email);
//
//                page.quit();
//            }
//        }
//
//    }
//
//    public static void saveToXlsx(){
//
//
//    }
//
//
//}
