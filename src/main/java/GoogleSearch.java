import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GoogleSearch {

    public static void main(String[] args) throws InterruptedException, IOException {


        //just for test
        //System.setProperty("webdriver.gecko.driver", "/valentin");
        PhantomJsDriverManager.getInstance().setup();

        String url = "https://www.mwcamericas.com/exhibition/2017-exhibitors/";
        String name = "top-area-cont";
        String country = "list-country";
        String phone = "flex-contents";
        String site = "websitebox";
        String email = "emailbox";
        String tags = "entity-tags";

        String pageClass = "next";
        String classOrId = "exhibitor";
        String attribute = "href";
        String nextPageUrl;

        WebDriver web;
        WebDriver pageDriver = null;
        List<WebDriver> pages = null;
        WebDriver driver = new PhantomJSDriver();

        Company company;
        List<Company> companies = new ArrayList<>();

        Long value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        Long currentHeight = 0L;

        List<WebDriver> driverPages = new ArrayList<>();

        driver.get(url);

        List<WebElement> elements = driver.findElements(By.className(classOrId));

        while (!value.equals(currentHeight)) {

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + value + ");");


            currentHeight = value;

            System.out.println("Sleeping... wleepy");


            Thread.sleep(5000);


            value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        }


        String href;

        nextPageUrl = driver.findElement(By.className(pageClass)).getAttribute(attribute);

        int nextElement = driver.findElements(By.className(pageClass)).size();
        while (nextElement != 0) {

            pageDriver = new PhantomJSDriver();

            pageDriver.get(nextPageUrl);
            System.out.println("visit "+nextPageUrl);

            if (pageDriver.findElements(By.className(pageClass)).size() != 0) {


                nextPageUrl = pageDriver.findElement(By.className(pageClass)).getAttribute(attribute);
            } else {
                nextElement = 0;
            }

            for (WebElement element : elements) {

                href = element.getAttribute(attribute);

                company = new Company();
                company.id = href;
                companies.add(company);

            }

            driverPages.add(pageDriver);

            pageDriver.quit();


        }

int total = driverPages.size();
        int visited = 0;
        for (WebDriver page : driverPages) {

            for (Company c : companies) {

                page = new PhantomJSDriver();

                //page.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);

                page.get(c.id);

//                System.out.println(c.id);

                if (page.findElements(By.className(name)).size() == 0) {
                    c.name = null;
                } else {
                    c.name = page.findElement(By.className(name)).getText();
//                    System.out.println("Name : " + c.name);
                }

                if (page.findElements(By.className(country)).size() == 0) {
                    c.country = null;
                } else {
                    c.country = page.findElement(By.className(country)).getText();

//                    System.out.println("Country : " + c.country);
                }

                if (page.findElements(By.className(email)).size() == 0) {
                    c.email = null;

                } else {
                    c.email = page.findElement(By.className(email)).getAttribute(attribute).replaceAll("mailto:", "");
//                    System.out.println("Email : " + c.email);

                }


                if (page.findElements(By.className(site)).size() == 0) {
                    c.site = null;

                } else {

                    c.site = page.findElement(By.className(site)).getAttribute(attribute);

//                    System.out.println("Site : " + c.site);
                }

                if (page.findElements(By.className(phone)).size() == 0) {
                    c.phone = null;

                } else {

                    List<WebElement> phones = page.findElements(By.tagName("p"));

                    for (WebElement ph : phones) {

                        if (ph.getText().contains("Phone")) {
                            c.phone = ph.getText();
                        }
                    }
//                    System.out.println("Phone : " + c.phone);
                }


                if (page.findElements(By.className(tags)).size() == 0) {
                    c.tags = null;

                } else {

                    List<WebElement> webTags = page.findElements(By.className(tags));

                    for (WebElement webTag : webTags) {

                        c.tags.add(webTag.getText());
//                        System.out.println("Tags : " + c.tags);
                    }


                }

visited++;
                System.out.println("Visited "+visited+ " of "+total);
                page.quit();

            }
        }

        driver.close();


        toXlsx("company.xlsx",companies);

    }


    private static void toXlsx (String excelFileName, List<Company> companies) throws IOException

    {
        String sheetName = "carriercommunity";//name of sheet
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        int index = 0;
        XSSFRow r1 = sheet.createRow(index);
        r1.createCell(0).setCellValue("id");
        r1.createCell(1).setCellValue("Country");
        r1.createCell(2).setCellValue("NAME");
        r1.createCell(3).setCellValue("Phone");
        r1.createCell(4).setCellValue("Email");
        r1.createCell(5).setCellValue("Site");
        r1.createCell(6).setCellValue("Tags");

        for (Company company : companies) {
            index++;
            XSSFRow row = sheet.createRow(index);

            row.createCell(0).setCellValue(company.id);
            row.createCell(1).setCellValue(company.country);
            row.createCell(2).setCellValue(company.name);
            row.createCell(3).setCellValue(company.phone);
            row.createCell(4).setCellValue(company.email);
            row.createCell(5).setCellValue(company.site);
            if(company.tags !=null) {
                String tags = String.valueOf(company.tags);
                row.createCell(6).setCellValue(tags.substring(1, tags.length() - 1));
            }else{
                row.createCell(6).setCellValue("");
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        }
    }

}
