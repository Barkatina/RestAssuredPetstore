import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ApiTests {
    private final int unexistingPetId = 234568;
    private final String userName = "string";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @DisplayName("Проверка ошибки при запросе id несуществующего питомца(GET)")
    @Test
    public void typeErrorTest() {
        given().when()
                .get(baseURI + "pet/{Id}", unexistingPetId)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("type", equalTo("error"),
                        "message", equalTo("Pet not found"));
    }
    @DisplayName("Проверка добавления нового питомца(POST)")
    @Test
    public void newPetTest() {
        Integer id = 11;
        String name = "dogg";
        String status = "sold";
        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);
        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "pet/")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo(name))
                .body("status", equalTo(status));
    }

    @DisplayName("Проверка наличия username 'string'(GET)")
    @Test
    public void
    availabilityUserNameTest() {
        given().when()
                .get(baseURI + "user/{username}", userName)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 " +
                        "OK")
                .body("username", equalTo("string"));
    }
}
