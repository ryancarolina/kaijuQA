package kaijuTests;

import kaijuPageMaps.EdgeLoginPageMap;
import kaijuUtil.Kaiju;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ghyst on 6/2/2017.
 */
public class KaijuTestClassExample extends EdgeLoginPageMap {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("CHROME");
        System.out.println(kaiju + " Unleashed!");
    }

    @Test
    public void csvTest() throws IOException {
        String[] csvOutput = kaiju.csvReadParse();
    }

    //This test method will demo navigating to a URL, locating web elements by partial link text and then
    //click it.

    @Test
    public void edgeTest(){
        //Set implicit wait
        kaiju.impWait(10);

        //Set the browser window size
        kaiju.setDimensionBrowserWindow(1440, 900);

        //Target URL
        kaiju.getUrl(edgeWebSite());

        //Confirm element is visible before trying to interact with it
        kaiju.waitForElementVisibleByPartialLinkText("Forgot Your Password?", 30);

        //Click on the element based on partial link
        kaiju.clickPartialLink("Forgot Your Password?");
    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
