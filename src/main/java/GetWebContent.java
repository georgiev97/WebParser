import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;


import java.io.*;
import java.net.URL;

public class GetWebContent {

    public static void main2(String[] args) throws IOException, InterruptedException {

        URL url = new URL("https://play.google.com/store/search?q=calculator&c=apps");


        WebClient webClient = new WebClient(BrowserVersion.getDefault());
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        webClient.getCurrentWindow().setInnerHeight(Integer.MAX_VALUE);

        String s = "window.scrollTo(0,document.body.scrollHeight);";



        HtmlPage page = webClient.getPage(url);
        page.initialize();
        webClient.waitForBackgroundJavaScript(30000);




        ScriptResult result = page.executeJavaScript(s);
        webClient.waitForBackgroundJavaScript(30000);
        page = (HtmlPage) result.getNewPage();
        page.initialize();
        webClient.waitForBackgroundJavaScript(30000);

//        result = page.executeJavaScript(s);
//        page = (HtmlPage) result.getNewPage();
//        result = page.executeJavaScript(s);
//        webClient.waitForBackgroundJavaScript(30000);
//        page = (HtmlPage) result.getNewPage();




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
