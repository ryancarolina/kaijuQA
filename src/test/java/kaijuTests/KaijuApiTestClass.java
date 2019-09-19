package kaijuTests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class KaijuApiTestClass {

    @Test

    public void apiLabTestMethod(){

        given()
                .relaxedHTTPSValidation()
                .get("https://www.google.com")
                .then()
                .body(containsString("google"))
                .statusCode(200)
                .log()
                .all();

    }
}
