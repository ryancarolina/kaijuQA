import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ghyst on 6/2/2017.
 */
public class KaijuTestClassExample {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("CHROME");
        System.out.println(kaiju + " Unleashed!");
    }

    @Test
    public void csvTest() throws IOException {
        String[] csvOutput = kaiju.csvReadParse();

        System.out.println(csvOutput);
    }

    //This test method will demo navigating to a URL, locating web elements by name and then
    //submitting a text string to the targeted element.

    @Test
    public void edgeTest(){
        //Set implicit wait
        kaiju.impWait(10);

        //Set the browser window size
        kaiju.setDimensionBrowserWindow(1440, 900);

        //Target URL
        kaiju.getUrl("https://edge.ceterus.com/");

        //Confirm element is visible before trying to interact with it
        kaiju.waitForElementVisibleByPartialLinkText("Forgot Your Password?", 30);

        //Click on the element based on partial link
        kaiju.clickPartialLink("Forgot Your Password?");

        //Confirm element is visible before trying to interact with it
        kaiju.waitForElementVisibleById("forgot-username-input", 30);

        //Locate text input element by ID and then enter text
        kaiju.getIdSendKeys("forgot-username-input", "test@test.com");

        //Confirm element is visible before trying to interact with it
        kaiju.waitForElementVisibleByXpath("/html/body/div[1]/div/div/div/div[2]/div/div/div[2]/div[2]/div[2]/form/div[2]/button", 30);

        //Click on the element based on xpath
        kaiju.clickXpath("/html/body/div[1]/div/div/div/div[2]/div/div/div[2]/div[2]/div[2]/form/div[2]/button");

        //Confirm element is visible before trying to interact with it
        kaiju.waitForElementVisibleByXpath("/html/body/div[1]/div/div/div/div[2]/div/div/div[2]/div[2]/div[2]/form/div[2]/button", 30);

        //Assert the response
        kaiju.assertTextTrue("Success", "h3");
    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
