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

    //This test method will demo navigating to a URL, locating web elements by name and then
    //submitting a text string to the targeted element, followed by submitting a form.
    //The test then asserts that the page contains the results we searched for.
    @Test
    public void edgeTest(){
        kaiju.impWait(10);
        kaiju.setDimensionBrowserWindow(1440, 900);
        kaiju.getUrl("https://edge.ceterus.com/");

    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
