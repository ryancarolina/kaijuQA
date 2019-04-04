import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KaijuExpyTestClass {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("CHROME");
        System.out.println(kaiju + " Unleashed!");
    }

    @Test
    public void navToExpyLandingPage(){
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("https://expansiagroup.com/");
        kaiju.checkTitle("EXPANSIA - Deploy Technology Faster");
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }
}
