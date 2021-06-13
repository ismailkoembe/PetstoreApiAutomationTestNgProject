package Api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class Store extends BaseClass{
    public static Response getStoreInventory (String environment) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .get(getBaseAPI(environment) + "/store/inventory")
                        .then()
                        .extract().response();

        return response;

    }

    public static Response getOrderById (String environment, Integer orderId) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .pathParam("orderId", orderId)
                        .get(getBaseAPI(environment) + "/store/order/{orderId}")
                        .then()
                        .extract().response();

        return response;

    }

    public static Response deleteOrderById (String environment, Integer orderId) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .pathParam("orderId", orderId)
                        .delete(getBaseAPI(environment) + "/store/order/{orderId}")
                        .then()
                        .extract().response();

        return response;

    }

    public static Response storeOrder (String environment, Map<String, Object> orders) {
        RequestSpecification request = addHeaders(environment);

        Response response =
                given()
                        .spec(request)
                        .body(orders)
                        .when()
                        .post(getBaseAPI(environment) + "/store/order")
                        .then()
                        .extract().response();

        return response;

    }
}
