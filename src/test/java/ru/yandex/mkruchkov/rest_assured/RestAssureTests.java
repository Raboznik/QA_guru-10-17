package ru.yandex.mkruchkov.rest_assured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RestAssureTests {

    String baseUrl = "https://reqres.in/api/";
    String data;


    @Test
    void getListUserTest() {

        given()
                .contentType(JSON)
                .get(baseUrl + "users?page=2")
                .then()
                .statusCode(200)
                .body("page", is(2))
                .body("data.first_name[2]", is("Tobias"));
    }


    @Test
    void getSingleUserTest() {

        given()
                .contentType(JSON)
                .get(baseUrl + "users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"));
    }


    @Test
    void getSingleUserNotFoundTest() {

        given()
                .get(baseUrl + "users/23")
                .then()
                .statusCode(404);
    }


    @Test
    void getListResourceTest() {

        given()
                .contentType(JSON)
                .get(baseUrl + "unknown")
                .then()
                .statusCode(200)
                .body("per_page", is(6))
                .body("data.year[0]", is(2000))
                .body("data.name[1]", is("fuchsia rose"));
    }


    @Test
    void getSingleResourceTest() {

        given()
                .get(baseUrl + "unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.name", is("fuchsia rose"));

    }


    @Test
    void getSingleResourceNotFoundTest() {

        given()
                .get(baseUrl + "unknown/23")
                .then()
                .statusCode(404);
    }


    @Test
    void postCreateUserTest() {

        data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseUrl + "users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }


    @Test
    void putUpdateUserTest() {

        data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .put(baseUrl + "users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }


    @Test
    void patchUpdateUserTest() {

        data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .patch(baseUrl + "users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }


    @Test
    void deleteUserTest() {

        given()
                .delete(baseUrl + "users/2")
                .then()
                .statusCode(204);
    }


    @Test
    void postRegisterTest() {

        data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseUrl + "register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void postRegisterUnsuccessfulTest() {

        data = "{ \"email\": \"sydney@fife\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseUrl + "register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }


    @Test
    void postLoginTest() {

        data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseUrl + "login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    void postLoginUnsuccessfulTest() {

        data = "{ \"email\": \"peter@klaven\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post(baseUrl + "login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }


    @Test
    void getDelayedResponseTest() {

        given()
                .when()
                .get(baseUrl + "users?delay=3")
                .then()
                .statusCode(200)
                .body("data.first_name[0]", is("George"));
    }
}
