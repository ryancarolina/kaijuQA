package kaijuTests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(DataProviderRunner.class)
public class KaijuApiTestClassExample {

    @DataProvider
    public static Object[][] zipCodesAndPlaces(){
        return new Object[][]{
                {"us", "90210", "Beverly Hills" },
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

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

    @Test

    public void carbonIntensity(){

        given()
                .relaxedHTTPSValidation()
                .get("https://api.carbonintensity.org.uk/intensity")
                .then().assertThat()
                .body(matchesJsonSchemaInClasspath("JSONSchemas/IntensitySchema.json"))
                .statusCode(200)
                .log()
                .all();

    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void pathParameterTest(String countryCode, String zipCode, String expectedPlaceName){

        given()
                .pathParam("countryCode", countryCode).pathParam("zipCode",zipCode).
                when()
                .get("https://zippopotam.us/{countryCode}/{zipCode}").
                then()
                .assertThat()
                .body("places[0].'place name'", equalTo(expectedPlaceName));
    }
}
