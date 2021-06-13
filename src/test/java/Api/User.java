package Api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;

import static io.restassured.RestAssured.given;

public class User extends BaseClass {
    public static Response createUser(String environment, Map<String, Object> user) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request).log().body()
                        .body(user)
                        .when()
                        .post(getBaseAPI(environment) + "/user")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response updateUserName(String environment, String username, Map<String, Object> newUSer) {
        RequestSpecification request = addHeaders(environment);

        Response response =
                given()
                        .spec(request).log().all()
                        .pathParam("username", username)
                        .body(newUSer)
                        .when()
                        .put(getBaseAPI(environment) + "/user/{username}")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response deleteUserName(String environment,  String username) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request).log().body()
                        .pathParam("username", username)
                        .when()
                        .delete(getBaseAPI(environment) + "/user/{username}")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response createUserWithList(String environment,  List<Map<String, String>> testUsers) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request).log().all()
                        .body(testUsers)
                        .when()
                        .post(getBaseAPI(environment) + "/user/createWithList")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response userLogin(String environment, String username, String password) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .param("username", username)
                        .param("password", password)
                        .when()
                        .get(getBaseAPI(environment) + "/user/login")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response userLogout(String environment) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request).log().body()
                        .when()
                        .get(getBaseAPI(environment) + "/user/logout")
                        .then()
                        .extract().response();
        return response;
    }

    public static Response getUserInfo(String environment, String username) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request).log().body()
                        .pathParam("username", username)
                        .when()
                        .get(getBaseAPI(environment) + "/user/{username}")
                        .then()
                        .extract().response();
        return response;
    }





}