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
        kaiju = new Kaiju("CHROME");
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
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }
}
