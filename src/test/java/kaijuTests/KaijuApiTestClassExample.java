package kaijuTests;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;

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

    public void apiTestExample2(){

        try{
            given()
                    .relaxedHTTPSValidation()
                    .get("https://expansiagroup.com")
                    .then()
                    .body(containsString("Nashua, New Hampshire 03060-5636"))
                    .statusCode(200)
                    .log()
                    .all();
        }catch (Exception e){
            System.out.println("Error during apiTestExample2 " + e);
            fail();
        }

        System.out.println("Body contains Nashua, New Hampshire 03060-5636 as expected");

    }

    @Test

    public void apiTestExample3(){

        given()
                .relaxedHTTPSValidation()
                .get("https://api.github.com/users")
                .then()
                .statusCode(200)
                .log()
                .all();

    }
}
