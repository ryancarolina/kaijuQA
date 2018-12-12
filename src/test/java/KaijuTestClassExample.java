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
        kaiju.csvReadParse();
    }

    //This test method will demo navigating to a URL, locating web elements by name and then
    //submitting a text string to the targeted element.
    @Test
    public void edgeTest(){
        kaiju.impWait(10);
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("https://edge.ceterus.com/");

        kaiju.waitForElementVisibleByPartialLinkText("Forgot Your Password?", 30);
        kaiju.clickPartialLink("Forgot Your Password?");
    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
