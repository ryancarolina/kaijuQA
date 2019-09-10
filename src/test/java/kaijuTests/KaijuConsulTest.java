package kaijuTests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class KaijuConsulTest {

    @Test
    //Asserting the consul UI is showing after cluster is deployed.
    public void assertConsul(){
        given()
                .relaxedHTTPSValidation()
                //Consul Agent IP
                .get("https://demo.consul.io/ui/dc1/services")
                .then().assertThat().body(containsString("Consul by HashiCorp"))
                .log().all().statusCode(200);
    }
}
