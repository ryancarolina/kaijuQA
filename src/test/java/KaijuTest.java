import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ghyst on 6/2/2017.
 */
public class KaijuTest {
    public static Kaiju kaiju;

    @Before
    public void setUp(){
        kaiju = new Kaiju("HTML");
    }

    @Test
    public void NavigateToTargetSite(){
        kaiju.getUrl("http://kaijuqa.com/");
    }

    @After
    public void killKaiju(){
        kaiju.killKaijuDriver();
    }
}
