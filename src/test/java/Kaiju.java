import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.lang.String;


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
    public java.lang.String getOsName(){
        if(osName == null){
            osName = System.getProperty("os.name");
        }
        System.out.println(osName);
        return osName;
    }






}
