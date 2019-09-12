package kaijuTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import kaijuUtil.KaijuVarUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by Ghyst on 6/5/2017.
 */
public class KaijuTenableApiTest extends KaijuVarUtil {

    CookieFilter filter = new CookieFilter();

    public RequestSpecification getTokenTenable(String username, String password){

        //Send hash map with login creds.
        Map<String, String> userPass = new HashMap<>();
        userPass.put("username", username);
        userPass.put("password", password);
        //Format login creds to JSON and attempt to login
        Response tokenResponse =

                given()

                        .relaxedHTTPSValidation()
                        .contentType("application/json")
                        .filter(filter)
                        .body(userPass)
                        .when().post("https://ec2-52-61-216-124.us-gov-west-1.compute.amazonaws.com:4444/rest/token")
                        .then().log().all()
                        .statusCode(200)
                        .extract().response();
        //Parse response and get Token
        String token = tokenResponse.getBody().jsonPath().getString("response.token");
        //Build header with Token
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("X-SecurityCenter", token);
        RequestSpecification requestSpec = builder.build();

        RequestSpecification retVal = requestSpec;
        //Return the token value
        System.out.println(retVal);
        return retVal;
    }

//    @Test
//    public void apiTestAcasReturnScans(){
//        given()
//                .relaxedHTTPSValidation()
//                .filter(filter)
//                .spec(getTokenTenable(acasName, acasPass))
//                .get("https://ec2-52-61-216-124.us-gov-west-1.compute.amazonaws.com:4444/rest/scan")
//                .then()
//                .log()
//                .all();
//    }

//    @Test
//    public void apiTestAcasReturnScanById(){
//        given()
//                .relaxedHTTPSValidation()
//                .filter(filter)
//                .spec(getTokenTenable(acasName, acasPass))
//                .get("https://ec2-52-61-216-124.us-gov-west-1.compute.amazonaws.com:4444/rest/scan/1")
//                .then()
//                .log()
//                .all();
//    }

    @Test
    public void apiTestAcasLaunchScan(){
        given()
                .relaxedHTTPSValidation()
                .filter(filter)
                .spec(getTokenTenable(acasName, acasPass))
                .post("https://ec2-52-61-216-124.us-gov-west-1.compute.amazonaws.com:4444/rest/scan/1/launch")
                .then()
                .log()
                .all().statusCode(200);
    }

}
