import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.net.URL;

public class GetWebContent {

    public static void main(String[] args) throws IOException, InterruptedException {

        URL url = new URL("https://play.google.com/store/search?q=calculator&c=apps");



        WebClient webClient = new WebClient(BrowserVersion.getDefault());
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);

        String s = "window.scrollTo(0,document.body.scrollHeight)";


        WebRequest request = new WebRequest(url);

        HtmlPage page = webClient.getPage(url);


        webClient.waitForBackgroundJavaScript(30000);


        ScriptResult result = page.executeJavaScript(s);


        page = (HtmlPage) result.getNewPage();

        webClient.waitForBackgroundJavaScript(30000);

        page.getWebClient().getPage(url);


        ScriptResult rs = page.executeJavaScript(s);


        page = (HtmlPage) result.getNewPage();

        webClient.waitForBackgroundJavaScript(30000);

        page.getWebClient().getPage(url);


        String content = page.asXml();

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


    }


}
