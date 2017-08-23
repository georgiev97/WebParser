import io.github.bonigarcia.wdm.FirefoxDriverManager;

import io.github.bonigarcia.wdm.PhantomJsDriverManager;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;


import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileOutputStream;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws InterruptedException, IOException {

        Date start = new Date();

        PhantomJsDriverManager.getInstance().setup();


        WebDriver driver = new PhantomJSDriver();

        String url = "https://acc2017.pathable.com/session?auth_token=JgBCekp6pqCYJf6cEohp";

        String accUrl = "https://acc2017.pathable.com/users?per_page=500&with[filter_scope]=users";

        String name ;

        String href;

        String classOrId = "block";

        Company company = new Company();

        List<Company> companies = new ArrayList<>();

        driver.get(url);

        Thread.sleep(3000);

        driver.get(accUrl);

        Thread.sleep(3000);

        List<WebElement> elements;

        Thread.sleep(3000);


        List<WebElement> allElements = driver.findElements(By.xpath("//*"));

        System.out.println(allElements.size());

        elements = driver.findElements(By.className(classOrId));

        Thread.sleep(3000);


        for (WebElement element : elements) {



            if (element.findElements(By.className("first_name")).size() != 0) {

              name = driver.findElement(By.className("first_name")).getText();

            }

            else {

                name= "";

            }

            if (driver.findElements(By.className("last_name")).size() != 0) {

               name += " " + driver.findElement(By.className("last_name")).getText();

            }

            else {

                name= "";

            }
            company.name = name;

            System.out.println(company.name);

            if (driver.findElements(By.xpath("//*[@data-text='title']")).size() !=0){

                company.position = driver.findElement(By.xpath("//*[@data-text='title']")).getText();
                System.out.println(company.position);

            }

            else {

                company.position= "";

            }

            if (driver.findElements(By.xpath("//*[@data-text='organization_name']")).size() !=0){

                company.companyName = driver.findElement(By.xpath("//*[@data-text='organization_name']")).getText();
                System.out.println(company.companyName);

            }

            else {

                company.companyName= "";

            }


        }

        driver.close();
        Date end = new Date();
        System.out.println((end.getTime() - start.getTime()) / (1000 * 60));

        toXlsx("company.xlsx", companies);

    }

    private static void toXlsx(String excelFileName, List<Company> companies) throws IOException

    {

        String sheetName = "carriercommunity";//name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet(sheetName);

        int index = 0;

        XSSFRow r1 = sheet.createRow(index);

        r1.createCell(0).setCellValue("Name");

        r1.createCell(1).setCellValue("Position");

        r1.createCell(2).setCellValue("Company");

        for (Company company : companies) {

            index++;

            XSSFRow row = sheet.createRow(index);

            row.createCell(0).setCellValue(company.name);

            row.createCell(1).setCellValue(company.position);

            row.createCell(2).setCellValue(company.companyName);

        }

        try (FileOutputStream fileOut = new FileOutputStream(excelFileName)) {

            wb.write(fileOut);

            fileOut.flush();

        }

    }

}