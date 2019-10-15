package kaijuTests;

import kaijuUtil.Kaiju;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpaceCampTestClass {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("GRID");
    }

    @Test
    public void assertGoPageTitle(){
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("http://localhost/?name=Felipe%20Ortiz");
        kaiju.checkTitle("EXPANSIA - Deploy Technology Faster");
    }

    @Test
    public void assertUrlNameParameter(){
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("http://localhost/?name=Felipe%20Ortiz");
        kaiju.checkForTextBySelector("body > div.container > p", "Felipe Ortiz");
    }

    @Test
    public void assertAboutLinkNavigation(){
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("http://localhost/?name=Felipe%20Ortiz");
        kaiju.waitForElementVisibleByPartialLinkText("About", 20);
        kaiju.clickPartialLink("About");

        kaiju.waitForElementVisibleById("thb-image-5d9b9a6122904", 20);
        kaiju.takeScreenShot();
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }
}
