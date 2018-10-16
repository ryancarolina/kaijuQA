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
    public void searchForScArrests(){
        kaiju.impWait(10);
        kaiju.maximizeBrowserWindow();
        kaiju.getUrl("https://southcarolina.arrests.org/");

        kaiju.waitForElementVisibleByName("fname", 30);
        kaiju.getNameSendKeys("fname", "Earl");

        kaiju.waitForElementVisibleByName("lname", 30);
        kaiju.getNameSendKeys("lname", "Burr");

        kaiju.clickSelector("#searchForm > input[type=\"submit\"]:nth-child(5)");

        kaiju.checkForTextBySelector("body > div.container.container_24 > div:nth-child(2) > div > div.search-results > ul > li:nth-child(2) > div > div.card-info > div.card-title", "Earl Burr");

    }

    @After
    public void killKaiju(){kaiju.killKaijuDriver();
    }
}
