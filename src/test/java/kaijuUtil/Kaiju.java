package kaijuUtil;

import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static junit.framework.TestCase.*;


/**
 * Created by Ryan Conklin on 6/2/2017.
 */
public class Kaiju extends KaijuVarUtil {
    private static WebDriver kaijuDriver;

    public Kaiju(String browserType){
        if (browserType.equals("CHROME")) {
            try {
                //ChromeDriver ver 2.36
                if(getOsName().contains("Windows")){
                    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
                    //ChromeDriver ver 2.46
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
        }
    }

    //DO NOT REMOVE
    public void loggingOff(){
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }

    //Access Driver
    public WebDriver getDriver(){
        WebDriver driver = kaijuDriver;
        return driver;
    }

    //Time and Dates *********************************

    //Get date
    public LocalDate getDate(){
        LocalDate today = LocalDate.now();
        System.out.println(today);
        return today;
    }

    //Get month
    public String getMonth(){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Integer monthRaw = localDate.getMonthValue();
        if(monthRaw > 9){
            String month = monthRaw.toString();
            return month;
        }else{
            String month = "0" + monthRaw;
            System.out.println("Month " + month);
            return month;
        }
    }

    //Get year
    public String getYear(){
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        System.out.println("Year " + year);
        return year;
    }

    //Get timestamp
    public String getTime(){
        DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(timeStamp.format(now));

        return timeStamp.format(now);
    }

    //Misc ****************************************

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

    //Return OS type driver is running on
    public String getOsName(){
        if(osName == null){
            osName = System.getProperty("os.name");
        }
        System.out.println(osName);
        return osName;
    }

    //Execute Java Script
    public void executeJavaScript(String javascript){
        JavascriptExecutor js = (JavascriptExecutor)kaijuDriver;
        js.executeScript(javascript);

    }

    //Kill the web driver
    public void killKaijuDriver(){
        kaijuDriver.close();
        System.out.println("Driver destroyed!");
    }

    //Scroll to bottom of page
    public void scrollToBottom(){
        JavascriptExecutor js = (JavascriptExecutor)kaijuDriver;
        js.executeScript("scroll(0, 1000)");
    }

    //Maximize browser window
    public void setDimensionBrowserWindow(Integer width, Integer height){
        kaijuDriver.manage().window().setSize(new Dimension(width, height));
    }

    //Waits **********************************

    //Wait for element to be NOT visible
    public void waitForElementToBeNotVisibleByClass(String className, Integer secondsToWait, String attribute, String value){
        System.out.println("Waiting for element " + className + " to be hidden for a max time of " + secondsToWait);
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.attributeContains(By.className(className),attribute,value));
    }

    //Wait for element to be clickable
    public void waitForElementToBeClickableByPartialLinkText(String text, Integer secondsToWait){
        System.out.println("Waiting for element " + text + " to be clickable for a max time of " + secondsToWait);
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(text)));
    }

    //Wait for condition attribute contains by ID
    public void waitForConditionAttributeContainsId(String id, Integer secondsToWait, String attribute, String value){
        System.out.println("Waiting for attribute " + attribute + " to obtain value " + value + " for a max time of " + secondsToWait);
        WebDriverWait wait = new WebDriverWait(kaijuDriver, secondsToWait);
        wait.until(ExpectedConditions.attributeContains(By.id(id),attribute,value));
    }

    //Wait for attribute value to be visible by Xpath
    public void waitForConditionAttributeContainsXpath(String xpath, Integer secondsToWait, String attribute, String value){
        System.out.println("Waiting for attribute " + attribute + " to obtain value " + value + " for a max time of " + secondsToWait);
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

    //Assertions *******************************

    //Check expected title vs actual title
    public String checkTitle(String title){
        String actualTitle = kaijuDriver.getTitle();
        String expectedTitle = title;
        assertEquals(expectedTitle, actualTitle);
        System.out.println("Title is " + actualTitle + " as expected");
        return actualTitle;
    }

    //Check if element is displayed by Css Selector
    public void checkIfElementIsDisplayedByCssSelector(String cssSelector){
        kaijuDriver.findElement(By.cssSelector(cssSelector)).isDisplayed();
        System.out.println("Element " + cssSelector + " is displayed");
    }

    //Check if element is displayed by Partial Link Text
    public void checkIfElementIsDisplayedByPartialLinkText(String text){
        kaijuDriver.findElement(By.partialLinkText(text)).isDisplayed();
        System.out.println("Element " + text + " is displayed");
    }

    //Check if element is displayed by ID
    public void checkIfElementIsDisplayedById(String id){
        kaijuDriver.findElement(By.id(id)).isDisplayed();
        System.out.println("Element " + id + " is displayed");
    }

    //Check if element is displayed by name
    public void checkIfElementIsDisplayedByName(String name){
        kaijuDriver.findElement(By.name(name)).isDisplayed();
        System.out.println("Element " + name + " is displayed");
    }

    //Check if element is displayed by tagName
    public  void checkIfElementIsDisplayedTagName(String tagName){
        kaijuDriver.findElement(By.tagName(tagName)).isDisplayed();
        System.out.println("Element " + tagName + " is displayed");
    }

    //Check if element is displayed using xpath
    public void checkIfElementIsDisplayedByXpath(String path){
        kaijuDriver.findElement(By.xpath(path)).isDisplayed();
        System.out.println("Element " + path + " is displayed");
    }

    //Check for text in URL
    public void checkForTextTrueUrl(String text){
        String URL = kaijuDriver.getCurrentUrl();
        assertTrue(text,true);
        System.out.println(URL + " does contain " + text);
    }

    //Verify a URL
    public void verifyUrl(String url){
        String URL = kaijuDriver.getCurrentUrl();
        Assert.assertEquals(url,URL);
    }

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

    //Locate element and perform an action with it ***************************

    //Get Text Using CSS
    public String getTextUsingCss(String selector){
        String uiText = kaijuDriver.findElement(By.cssSelector(selector)).getText();
        return uiText;
    }

    //Get text using Class
    public String getTextUsingClass(String className){
        String uiText = kaijuDriver.findElement(By.className(className)).getText();
        return uiText;
    }

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

    //Get element by xpath and send text
    public void getXpathSendKeys(String xpath, String text){
        System.out.println("Locating element by xapth " + xpath + " sending text input " + text);
        kaijuDriver.findElement(By.xpath(xpath)).sendKeys(text);
    }

    //Get attribute from web element through css selector
    public String getAttributeFromWebElement(String cssSelector, String attribute){
        WebElement onElement = kaijuDriver.findElement(By.cssSelector(cssSelector));

        String returnAttribute = onElement.getAttribute(attribute);

        return returnAttribute;
    }

    //hover element using action and CSS Selector
    public void hoverElementUsingActionCssSelector(String selector) {
        WebElement element = kaijuDriver.findElement(By.cssSelector(selector));
        Actions actions = new Actions(kaijuDriver);
        actions.moveToElement(element).perform();
    }

    //Click an element by Class name
    public void clickClass(String className){
        kaijuDriver.findElement(By.className(className)).click();
        System.out.println("Clicking" + className);
    }

    //Click element using action and CSS Selector
    public void clickElementUsingActionCssSelector(String selector) {
        WebElement element = kaijuDriver.findElement(By.cssSelector(selector));
        System.out.println(element);
        Actions actions = new Actions(kaijuDriver);
        actions.moveToElement(element).click().perform();
        System.out.println("Clicking " + element + " element with actions");

    }

    //Click element using action and ID
    public void clickElementUsingActionID(String id){
        WebElement element = kaijuDriver.findElement(By.id(id));
        System.out.println(element);
        Actions actions = new Actions(kaijuDriver);
        actions.moveToElement(element).click().perform();
        System.out.println("Clicking " + element + " element with actions");
    }

    //Click an element by tagName
    public void clickTagName(String tagName){
        kaijuDriver.findElement(By.tagName(tagName)).click();
        System.out.println("Clicking " + tagName);
    }

    //Click the dropdown by ID and select a value by index
    public void selectDropDownById(String id, String index){
        Select dropDown = new Select(kaijuDriver.findElement(By.id(id)));
        //COA is currently index value 9
        dropDown.selectByValue(index);
        System.out.println("Selecting drop down value " + index);
    }

    //Click the dropdown by Name and select a value based on visible text
    public void selectDropDownByNameAndSelectValueByVisibleText(String name, String text){
        Select dropDown = new Select(kaijuDriver.findElement(By.name(name)));
        dropDown.selectByVisibleText(text);
        System.out.println("Selecting drop down by text " + text);
    }

    //Click the dropdown by ID and select a value based on visible text
    public void selectDropDownByIdAndSelectValueByVisibleText(String id, String text){
        Select dropDown = new Select(kaijuDriver.findElement(By.id(id)));
        dropDown.selectByVisibleText(text);
        System.out.println("Selecting drop down by text " + text);
    }

    //Clear a field by CSS Selector
    public void getSelectorClearText(String cssSelector){
        kaijuDriver.findElement(By.cssSelector(cssSelector)).clear();
    }

    //Clear a field by id
    public void getIdClearText(String id){
        kaijuDriver.findElement(By.id(id)).clear();
    }

    //Clear a field by Name
    public void getNameClearText(String name){
        kaijuDriver.findElement(By.name(name)).clear();
    }

    //Find element by selector and submit it
    public void getFormCssSelectorAndSubmit(String selector){
        kaijuDriver.findElement(By.cssSelector(selector)).submit();
        System.out.println("Submitted Form");
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
        System.out.println("Clicking partial link " + link);
    }

    //Get target URL
    public String getUrl(String url){
        kaijuDriver.get(url);
        System.out.println("Navigating to " + url + " ");
        return url;
    }

    //Read from data source *************************

    //Connect to db
    public Connection dataBaseConnection() {
        String targetDbase = "jdbc:mysql://35.188.77.255:3306/clone";
        String uName = "username";
        String pWord = "password";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Attempting to connect to target database " + targetDbase);

            connection = DriverManager.getConnection(targetDbase, uName, pWord);

        } catch (Exception e) {
            System.out.println("Error during db connection..." + e);
        }

        return connection;
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

    //Handle alerts and/or JS popups ***************************************

    //Alert
    public void alert(){
        Alert alert = kaijuDriver.switchTo().alert();
        alert.accept();
    }
}
