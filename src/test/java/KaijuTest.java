import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ghyst on 6/2/2017.
 */
public class KaijuTest {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("CHROME");
    }

    @Test
    public void NavigateToTargetSite(){
        kaiju.impWait(10);
        kaiju.maximizeBrowserWindow();
        kaiju.getUrl("http://kaijuqa.com/");
        kaiju.checkForTextFalse("error", "body");
        kaiju.checkforTextTrue("Chaos Engineering", "body");
        kaiju.clickId("menu-item-172");
        kaiju.checkforTextTrue("About Us", "body");
        kaiju.clickPartialLink("Home");
        kaiju.checkforTextTrue("Home", "body");
    }

    @Test
    public void csvTest() throws IOException {
        kaiju.csvReadParse();
    }

    @After
    public void killKaiju(){
        //kaiju.killKaijuDriver();
    }
}