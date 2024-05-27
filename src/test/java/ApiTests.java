import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class ApiTests {
    private final int unexistingPetId = 234568;

    @BeforeEach
    public void setup(){
        RestAssured.baseURI="https://petstore.swagger.io/v2/";
    }
    @Test
    public void typeErrorTest(){
        given().when()
                .get(baseURI + "pet/{Id}", unexistingPetId)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body( "type",equalTo("error"));
    }
}
