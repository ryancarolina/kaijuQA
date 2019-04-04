import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.opencsv.CSVReader;
import io.gatling.commons.stats.assertion.In;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Today;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.lang.String;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
    String gridNode = "http://ec2-18-232-71-47.compute-1.amazonaws.com:4444/wd/hub";

    public Kaiju(String browserType){
        if (browserType.equals("CHROME")) {

            try {
                //ChromeDriver ver 2.36
                if(getOsName().contains("Windows")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
                    //ChromeDriver ver 2.43
                }else if(getOsName().contains("Mac OS X")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver");
                }
                kaijuDriver = new ChromeDriver();
            } catch (Exception e) {
                System.out.println("Error during Chrome Test Setup" + e.toString());
            }
            //This is for SaucesLabs/Selenium Grid
        } else if (browserType.equals("SAUCE")) {
            try {
                //Set the browser type and other options below
                DesiredCapabilities caps = DesiredCapabilities.chrome();
                //caps.setCapability("platform", "Windows 2016");
                caps.setCapability("version", "");
                caps.setCapability("name", "Test: " + getTime() );
                //caps.setCapability("extendedDebugging", "false"); //Saucelabs known 504 gateway error with xDebugging enabled
                caps.setCapability("idleTimeout", "1000");

                //noinspection deprecation
                kaijuDriver = new RemoteWebDriver(new java.net.URL(sauceURL), caps);
            } catch (Exception e) {
                System.out.println("Error during Remote Test Setup" + e.toString());
            }
            //Selenium Grid
        } else if (browserType.equals("GRID")) {
            try {
                //Set the browser type and other options below
                DesiredCapabilities caps = DesiredCapabilities.chrome();
                //caps.setCapability("platform", "Windows 2016");
                caps.setCapability("version", "");
                caps.setCapability("name", "Test: " + getTime() );
                //caps.setCapability("extendedDebugging", "false"); //Saucelabs known 504 gateway error with xDebugging enabled
                caps.setCapability("idleTimeout", "1000");

                //noinspection deprecation
                kaijuDriver = new RemoteWebDriver(new java.net.URL(gridNode), caps);
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

    //Time and Dates


    //Get timestamp
    public String getTime(){
        DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(timeStamp.format(now));

        return timeStamp.format(now);
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
        System.out.print("Navigating to " + url + " ");
    }

    //Kill the kaijuDriver
    public void killKaijuDriver(){
        kaijuDriver.close();
    }

    //Maximize browser window
    public void setDimensionBrowserWindow(Integer width, Integer height){
        kaijuDriver.manage().window().setSize(new Dimension(width, height));
    }

    //Waits **********************************

    //Wait for attribute value to be visible by Xpath
    public void waitForConditionAttributeContainsXpath(String xpath, Integer secondsToWait, String attribute, String value){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + value + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.attributeContains(By.xpath(xpath),attribute,value));
    }

    //Wait for element to be visible className
    public void waitForElementVisibleClass(String className, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + className + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(className)));

    }

    //Wait for element to contain text CSS
    public void waitForElementContainsTextCss(String selector, String text, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + text + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(selector),text));
    }

    //Wait for element to contain text using Class
    public void waitForElementContainsTextClass(String className, String text, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + text + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className(className),text));
    }

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

    //Wait for element to be visible by ID
    public void waitForElementVisibleById(String id, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + id + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    //Wait for element to be visible by Selector
    public void waitForElementVisibleBySelector(String selector, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + selector + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
    }

    //Wait for element to be visible by Xpath
    public void waitForElementVisibleByXpath(String xpath, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + xpath + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    //Wait for element to be visible by partial link text
    public void waitForElementVisibleByPartialLinkText(String linkText, Integer secondsToWait){
        System.out.println("Waiting a max of " + secondsToWait + " seconds for " + linkText + " to become visible...");
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(linkText)));
    }

    //Assertions

    //Take a screenshot and save it to the resources file
    public void takeScreenShot(){
        try {
            System.out.println("Taking screenshot...");
            File scrFile = ((TakesScreenshot) kaijuDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/src/test/resources/" + getTime() + "AutoScreenShot.png"));

        }catch (Exception e){
            System.out.println("Error during screenshot " + e);
        }
    }

    //Check if an element is selected
    public Boolean isElementSelectedByName(String name){
        waitForElementVisibleByName(name, 120);

        Boolean  isChecked = kaijuDriver.findElement(By.name(name)).isSelected();

        if(isChecked == true){
            System.out.println(name + " is selected");
        } else {
            System.out.println(name + " is NOT selected");
        }
        return isChecked;
    }

    //Check that a element located by selector contains a string
    public void checkForTextBySelector(String selector, String text){
        System.out.println("Checking for string " + text + " within " + selector);
        kaijuDriver.findElement(By.cssSelector(selector)).getText().contains(text);
        System.out.println(selector + " does contain the string " + text);
    }

    //Check that a string does NOT exist
    public void assertTextFalse(String text, String tagName){
        String domText = kaijuDriver.findElement(By.tagName(tagName)).getText();
        assertFalse(String.valueOf(true), domText.contains(text));
        System.out.println(tagName + " does NOT contain " + text);
    }

    //Check that a string does exist
    public void assertTextTrue(String text, String tagName){
        String domText = kaijuDriver.findElement(By.tagName(tagName)).getText();
        assertTrue(String.valueOf(true), domText.contains(text));
        System.out.println(tagName + " contains " + text);
    }

    //Locate element and click it ***************************

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

    //Read from data source *************************

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

    //Return a random integer between 1 and 100
    public Integer random1To100(){
        Integer target = rangeOfRandomNum(1, 100);

        return target;
    }

    //Return a range of random numbers
    public Integer rangeOfRandomNum(Integer min, Integer max){
        int range = (max - min) + 1;

        return (int)(Math.random() * range) + min;
    }

    //Locate web element and send input ***************************

    //Find element by id and send text
    public void getIdSendKeys(String id,String text){
        System.out.println("Locating element by ID " + id + " sending text input " + text);
        kaijuDriver.findElement(By.id(id)).sendKeys(text);
    }

    public void getNameSendKeyReturn(String name){
        System.out.println("Locating element by NAME " + name + " sending return/enter key...");
        kaijuDriver.findElement(By.name(name)).sendKeys(Keys.RETURN);
    }

    //Get element by name and send text
    public void getNameSendKeys(String name, String text){
        System.out.println("Locating element by name " + name + " sending text input " + text);
        kaijuDriver.findElement(By.name(name)).sendKeys(text);
    }
}
