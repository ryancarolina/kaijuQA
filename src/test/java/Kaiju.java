import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.lang.String;

import static junit.framework.TestCase.assertFalse;


/**
 * Created by Ghyst on 6/2/2017.
 */
public class Kaiju {
    private static WebDriver kaijuDriver;
    String sauceUsername = " ";
    String sauceAccessKey = " ";
    String sauceURL = "https://" + sauceUsername + ":" + sauceAccessKey + "@ondemand.saucelabs.com:443/wd/hub";
    String osName = null;


    public Kaiju(String browserType){
        if (browserType.equals("CHROME")) {

            try {
                //ChromeDriver ver 2.29
                if(getOsName().contains("Windows")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
                }else if(getOsName().contains("Mac OS X")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver");
                }
                kaijuDriver = new ChromeDriver();
            } catch (Exception e) {
                System.out.println("Error during Chrome Test Setup" + e.toString());
            }
            //This is for SaucesLabs/Selenium Grid
        } else if (browserType.equals("REMOTE")) {
            try {
                DesiredCapabilities caps = DesiredCapabilities.firefox();
                caps.setCapability("platform", "Windows 10");
                caps.setCapability("version", "latest");
                //noinspection deprecation
                kaijuDriver = new RemoteWebDriver(new java.net.URL(sauceURL), caps);
            } catch (Exception e) {
                System.out.println("Error during Remote Test Setup" + e.toString());
            }
        } else if (browserType.equals("HTML")) {
            try {
                kaijuDriver = new HtmlUnitDriver(BrowserVersion.BEST_SUPPORTED,true) {
                    //HtmlUnitDriver by default logs all notices, warnings, script errors.
                    @Override
                    protected WebClient newWebClient(BrowserVersion version) {
                        WebClient webClient = super.newWebClient(version);
                        webClient.getOptions().setThrowExceptionOnScriptError(false);
                        return webClient;
                    }
                };
            } catch(Exception e){
                System.out.println("Error during HtmlUnit Test Setup" + e.toString());
            }
        }
    }

    //Return OS type driver is running on
    public String getOsName(){
        if(osName == null){
            osName = System.getProperty("os.name");
        }
        System.out.println(osName);
        return osName;
    }

    //Get target URL
    public void getUrl(String url){
        kaijuDriver.get(url);
    }

    //Kill the kaijuDriver
    public void killKaijuDriver(){
        kaijuDriver.close();
    }

    //Check that a string does NOT exist
    public void checkForTextFalse(String text, String tagName){
        String domText = kaijuDriver.findElement(By.tagName(tagName)).getText();
        assertFalse(String.valueOf(true), domText.contains(text));
        System.out.println(tagName + " does NOT contain " + text);
    }

    //CSV read and parse, this will take each separated value and insert it into an array
    public void csvReadParse() throws java.io.IOException{
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"/src/test/resources/testCSV.csv"), ',', '"', '\'', 1);
        String [] nextLine;
        while((nextLine = reader.readNext()) != null){
            System.out.println(nextLine[0] + " " + nextLine[1] + " " + nextLine[2]);
        }
    }






}
