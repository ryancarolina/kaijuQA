package kaijuTests;

import kaijuPageMaps.ExpyLandingPageMap;
import kaijuUtil.Kaiju;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//Test logic is written here, while page objects are called from the page map class.
public class KaijuExpyTestClass extends ExpyLandingPageMap {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("GRID");
        System.out.println(kaiju + " Unleashed!");
    }

    @Test
    public void ansiblePlaybookLint(){
        kaiju.yamlLint("src/test/resources/test_packages.yaml");
    }

    @Test
    public void navToExpyLandingPage(){
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl(expyWebSite());
        kaiju.checkTitle(expyWebSiteTitle());

        kaiju.waitForElementVisibleByPartialLinkText("About", 20);
        kaiju.clickPartialLink("About");
        kaiju.waitForElementVisibleByPartialLinkText("About EXPANSIA", 20);
        kaiju.clickPartialLink("About EXPANSIA");

        kaiju.waitForElementVisibleById("thb-image-5d9b9a6122904", 20);
        kaiju.takeScreenShot();

    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }
}
