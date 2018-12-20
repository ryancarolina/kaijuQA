import com.github.myzhan.locust4j.Locust;
import com.github.myzhan.locust4j.AbstractTask;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.junit.Test;


public class OpenApplicationTask extends AbstractTask {


    private int weight;

    @Override
    public int getWeight() {
        return weight;
    }


    @Override
    public String getName() {
        return "Open application task";
    }

    public OpenApplicationTask(int weight){
        this.weight = weight;
    }

    @Override

    public void execute() {
        try {
            Response response = given().get("http://blazedemo.com");
            Locust.getInstance().recordSuccess("http", getName(), response.getTime(), 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

