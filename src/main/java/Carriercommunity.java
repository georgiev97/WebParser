import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Carriercommunity {

    public static void main(String[] args) throws InterruptedException, IOException {

        Date start = new Date();

        ChromeDriverManager.getInstance().setup();

        PhantomJsDriverManager.getInstance().setup();

        String url = "https://www.carriercommunity.com/members/ani-muradyan/browse-delegates/";
        String href;
        List<String> getEmail = new ArrayList<>();


        String pageClass = "browse-delegate_next";
        String profileId = "btn-profile";
        String emailId = "btn-request";
        String attribute = "href";
        String email;

        WebDriver driver = new PhantomJSDriver();

        Company company = null;
        List<Company> companies = new ArrayList<>();

        driver.get(url);

        driver.findElement(By.id("bp-login-widget-user-login")).sendKeys("Ani Muradyan");
        driver.findElement(By.id("bp-login-widget-user-pass")).sendKeys("speedflow2017");
        driver.findElement(By.id("bp-login-widget-submit")).click();

        driver.get(url);

        Thread.sleep(1000);

        WebElement nextPageUrl = driver.findElement(By.xpath("//*[@name='browse-delegate_length']")).findElement(By.xpath("//*[@value='-1']"));

        nextPageUrl.click();

        Thread.sleep(1000);


//        Long value = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight;");
//        Long currentHeight = 0L;
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

        List<WebElement> elements = driver.findElements(By.id(profileId));
        System.out.println("elements " + elements.size());
        List<WebElement> emailsId = driver.findElements(By.id(emailId));
        System.out.println("emailsId " + emailsId.size());




        for (WebElement element : elements) {

                href = element.getAttribute(attribute);
                company = new Company();
                company.id = href;
                companies.add(company);
                System.out.println("company.id = " + company.id);

        }

        for (WebElement idEmail : emailsId) {

                getEmail.add(idEmail.getAttribute(attribute));
        }

        for (String emailLinks : getEmail){

            driver.get(emailLinks);
            email = driver.findElement(By.className("invitee")).getAttribute("data-email");
            if (company != null) {
                company.email = email;
            }

        }


        int total = companies.size();
        int visited = 0;

        String firstName;
        String lastName;
        String position;
        String companyName;
        String segments;

        for (Company c : companies) {

            driver.get(c.id);

            Thread.sleep(2000);

            if (driver.findElements(By.xpath("//*[@class='field_8 field_first-name required-field visibility-public field_type_textbox']")).size() == 0) {
                firstName = "";
            } else {
                firstName = driver.findElement(By.xpath("//*[@class='field_8 field_first-name required-field visibility-public field_type_textbox']"))
                        .findElement(By.className("data")).getText();
            }

            if (driver.findElements(By.xpath("//*[@class='field_17 field_last-name required-field visibility-public alt field_type_textbox']")).size() == 0) {
                lastName = "";
            } else {
                lastName = driver.findElement(By.xpath("//*[@class='field_17 field_last-name required-field visibility-public alt field_type_textbox']"))
                        .findElement(By.className("data")).getText();
            }


            if (driver.findElements(By.xpath("//*[@class='field_28 field_job-title required-field visibility-public field_type_textbox']")).size() == 0) {
                position = "";
            } else {
                position = driver.findElement(By.xpath("//*[@class='field_28 field_job-title required-field visibility-public field_type_textbox']"))
                        .findElement(By.className("data")).getText();
            }


            if (driver.findElements(By.xpath("//*[@class='field_6 field_company-name optional-field visibility-public alt field_type_textbox']")).size() == 0) {
                companyName = "";
            } else {
                companyName = driver.findElement(By.xpath("//*[@class='field_6 field_company-name optional-field visibility-public alt field_type_textbox']"))
                        .findElement(By.className("data")).getText();
            }


            if (driver.findElements(By.xpath("//*[@class='field_9 field_sector required-field visibility-public field_type_checkbox']")).size() == 0) {
                segments = "";
            } else {
                segments = driver.findElement(By.xpath("//*[@class='field_9 field_sector required-field visibility-public field_type_checkbox']"))
                        .findElement(By.className("data")).getText();
            }

            c.name = firstName + " " + lastName;
            c.position = position;
            c.companyName = companyName;
            c.segment = segments;

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
        r1.createCell(0).setCellValue("Name");
        r1.createCell(1).setCellValue("Company");
        r1.createCell(2).setCellValue("Position");
        r1.createCell(3).setCellValue("Email");
        r1.createCell(4).setCellValue("Segments");


        for (Company company : companies) {
            index++;
            XSSFRow row = sheet.createRow(index);

            row.createCell(0).setCellValue(company.name);
            row.createCell(1).setCellValue(company.companyName);
            row.createCell(2).setCellValue(company.position);
            row.createCell(3).setCellValue(company.email);
            row.createCell(4).setCellValue(company.segment);
        }
        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {
            wb.write(fileOut);
            fileOut.flush();
        }
    }

}


