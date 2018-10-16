import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.opencsv.CSVReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.lang.String;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


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
                //ChromeDriver ver 2.36
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

    //Maximize browser window
    public void maximizeBrowserWindow(){
        kaijuDriver.manage().window().maximize();
    }

    //Waits

    //Set an implicit wait
    public void impWait(Integer seconds){
        kaijuDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    //Wait for element to be visible by name
    public void waitForElementVisibleByName(String name, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + name + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
    }

    //Assertions

    //Check that a element located by selector contains a string
    public void checkForTextBySelector(String selector, String text){
        System.out.println("Checking for string " + text + " within " + selector);
        kaijuDriver.findElement(By.cssSelector(selector)).getText().contains(text);
        System.out.println(selector + " does contain the string " + text);
    }

    //Check that a string does NOT exist
    public void checkForTextFalse(String text, String tagName){
        String domText = kaijuDriver.findElement(By.tagName(tagName)).getText();
        assertFalse(String.valueOf(true), domText.contains(text));
        System.out.println(tagName + " does NOT contain " + text);
    }

    //Check that a string does exist
    public void checkforTextTrue(String text, String tagName){
        String domText = kaijuDriver.findElement(By.tagName(tagName)).getText();
        assertTrue(String.valueOf(true), domText.contains(text));
        System.out.println(tagName + " does contain " + text);
    }

    //Find element by css selector and click it
    public void clickSelector(String selector){
        kaijuDriver.findElement(By.cssSelector(selector)).click();
    }

    //Find element by xpath and click it
    public void clickXpath(String xpath){
        kaijuDriver.findElement(By.xpath(xpath)).click();
    }

    //Find element by name and click it
    public void clickName(String name){
        kaijuDriver.findElement(By.name(name)).click();
    }

    //Find element by id and click it
    public void clickId(String id){
        kaijuDriver.findElement(By.id(id)).click();
    }

    //Find element by partial link text and click it
    public void clickPartialLink(String link){
        kaijuDriver.findElement(By.partialLinkText(link)).click();
    }

    //CSV read and parse, this will take each separated value and insert it into an array
    public String[] csvReadParse() throws java.io.IOException{
        //FileReader("yourFileHere.csv") the method is currently getting the csv file from the resources dir.
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"/src/test/resources/testCSV.csv"), ',', '"', '\'', 1);
        String [] nextLine;
        while((nextLine = reader.readNext()) != null){
            System.out.println(nextLine[0] + " " + nextLine[1] + " " + nextLine[2]);
        }
        return nextLine;
    }

    //Get web element and send keys

    //Find element by id and send text
    public void getIdSendKeys(String id,String text){
        System.out.println("Locating element by ID " + id + " sending text input " + text);
        kaijuDriver.findElement(By.id(id)).sendKeys(text);
    }

    //Get element by name and send text
    public void getNameSendKeys(String name, String text){
        System.out.println("Locating element by name " + name + " sending text input " + text);
        kaijuDriver.findElement(By.name(name)).sendKeys(text);
    }
}
