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
        //Do some cool test stuff here...
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }

}
