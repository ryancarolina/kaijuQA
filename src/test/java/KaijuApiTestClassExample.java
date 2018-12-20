import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by Ghyst on 6/5/2017.
 */
public class KaijuApiTestClassExample {
    @Test
    public void apiTestExample(){
        given()
                .relaxedHTTPSValidation()
                .get("https://www.google.com")
                .then()
                .body(containsString("google"))
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void apiTestReturnHttpRequestHeaders(){
        given()
                .relaxedHTTPSValidation()
                .get("http://headers.jsontest.com/")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void apiTestReturnJsonAssertString(){
        given()
                .relaxedHTTPSValidation()
                .get("http://echo.jsontest.com/key/value/Kaiju/QA")
                .then()
                .body(containsString("Kaiju"))
                .statusCode(200)
                .log()
                .all();
    }
}
