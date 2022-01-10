import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresInTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void getSingleUser() {
        given()
                .when()
                .get("api/users/7")
                .then()
                .statusCode(200)
                .body("data.id", is(7), "data.email", is("michael.lawson@reqres.in"));
    }

    @Test
    void getSingleUserNotFound() {
        given()
                .when()
                .get("api/users/27")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    void createdUser() {
        String userData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .contentType(JSON)
                .body(userData)
                .when()
                .post("api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"),
                        "job", is("leader"),
                        "id", notNullValue());
    }

    @Test
    void registerSuccess() {
        String userData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .contentType(JSON)
                .body(userData)
                .when()
                .post("api/register")
                .then()
                .statusCode(200)
                .body("id", notNullValue(), "token", notNullValue());
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("api/users/2")
                .then()
                .statusCode(204);
    }
}
