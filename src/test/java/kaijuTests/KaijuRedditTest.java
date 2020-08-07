package kaijuTests;

import kaijuUtil.Kaiju;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KaijuRedditTest {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("CHROME");
    }

    @Test
    public void redditTest(){
        //Set implicit wait time
        kaiju.impWait(10);
        //The site under test
        kaiju.getUrl("https://www.reddit.com/");
        //Set the window size
        kaiju.setDimensionBrowserWindow(1440, 900);
        //Assert the site title
        kaiju.checkTitle("reddit: the front page of the internet");
        //Assert the log in button7777
        kaiju.waitForElementVisibleByXpath("/html/body/div[1]/div/div[2]/div[1]/header/div/div[2]/div/div[1]/a[1]", 5);
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }

}
