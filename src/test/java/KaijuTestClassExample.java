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
    }

    @Test
    public void csvTest() throws IOException {
        kaiju.csvReadParse();
    }

    @Test
    public void sendKeysIdTest(){
        kaiju.impWait(10);
        kaiju.maximizeBrowserWindow();
        kaiju.getUrl("https://www.google.com/");
        kaiju.sendKeysId("lst-ib","cheese");
    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
