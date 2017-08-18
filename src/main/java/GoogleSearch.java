import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GoogleSearch {

    public static void main(String[] args) throws InterruptedException, IOException {

        Date start = new Date();

        PhantomJsDriverManager.getInstance().setup();

        String url = "https://www.mwcamericas.com/exhibition/2017-exhibitors/";
        String name = "top-area-cont";
        String country = "list-country";
        String phone = "flex-contents";
        String site = "websitebox";
        String email = "emailbox";
        String tags = "entity-tags";
        String href;


        String pageClass = "next";
        String classOrId = "exhibitor";
        String attribute = "href";
        String nextPageUrl;

        WebDriver driver = new PhantomJSDriver();

        Company company;
        List<Company> companies = new ArrayList<>();

        Long value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        Long currentHeight = 0L;

        List<WebDriver> driverPages = new ArrayList<>();

        driver.get(url);

        List<WebElement> elements = driver.findElements(By.className(classOrId));

        for (WebElement element : elements) {

            href = element.getAttribute(attribute);

            company = new Company();
            company.id = href;
            companies.add(company);

        }

        while (!value.equals(currentHeight)) {

            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0," + value + ");");


            currentHeight = value;

            System.out.println("Sleeping... wleepy");


            Thread.sleep(5000);


            value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
        }

        nextPageUrl = driver.findElement(By.className(pageClass)).getAttribute(attribute);

        int nextElement = driver.findElements(By.className(pageClass)).size();
        while (nextElement != 0) {


            driver.get(nextPageUrl);
            System.out.println("visit " + nextPageUrl);


            elements = driver.findElements(By.className(classOrId));


            if (driver.findElements(By.className(pageClass)).size() != 0) {


                nextPageUrl = driver.findElement(By.className(pageClass)).getAttribute(attribute);

            } else {
                nextElement = 0;
            }

            for (WebElement element : elements) {

                href = element.getAttribute(attribute);

                company = new Company();
                company.id = href;
                companies.add(company);
            }

            driverPages.add(driver);

        }

        int total = companies.size();
        int visited = 0;

        for (Company c : companies) {

            driver.get(c.id);

            if (driver.findElements(By.className(name)).size() == 0) {
                c.name = null;
            } else {
                c.name = driver.findElement(By.className(name)).getText();
            }

            if (driver.findElements(By.className(country)).size() == 0) {
                c.country = null;
            } else {
                c.country = driver.findElement(By.className(country)).getText();

            }

            if (driver.findElements(By.className(email)).size() == 0) {
                c.email = null;

            } else {
                c.email = driver.findElement(By.className(email)).getAttribute(attribute).replaceAll("mailto:", "");

            }

            if (driver.findElements(By.className(site)).size() == 0) {
                c.site = null;

            } else {

                c.site = driver.findElement(By.className(site)).getAttribute(attribute);

            }

            if (driver.findElements(By.className(phone)).size() == 0) {
                c.phone = null;

            } else {

                List<WebElement> phones = driver.findElements(By.tagName("p"));

                for (WebElement ph : phones) {

                    if (ph.getText().contains("Phone")) {
                        c.phone = ph.getText();
                    }
                }
            }

            if (driver.findElements(By.className(tags)).size() == 0) {
                c.tags = null;

            } else {

                List<WebElement> webTags = driver.findElements(By.className(tags));

                for (WebElement webTag : webTags) {

                    c.tags.add(webTag.getText());
                }

            }

            visited++;
            System.out.println("Visited " + visited + " of " + total);

        }

        driver.close();


        toXlsx("company.xlsx", companies);
        Date end = new Date();
        System.out.println((end.getTime() - start.getTime()) / (1000 * 60));

    }


    private static void toXlsx(String excelFileName, List<Company> companies) throws IOException

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
            if (company.tags != null) {
                String tags = String.valueOf(company.tags);
                row.createCell(6).setCellValue(tags.substring(1, tags.length() - 1));
            } else {
                row.createCell(6).setCellValue("");
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        }
    }

}
