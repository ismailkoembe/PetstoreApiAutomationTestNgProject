package Api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Pet extends BaseClass {
    public static Response addNewPet (String environment, int id, String petName, String status,
                                      int categoryId, String categoryName, int tagId, String tagName, String photoUrl) {
        RequestSpecification request = addHeaders(environment);

        JSONObject pet = new JSONObject();
        pet.put("id", id);
        pet.put("name", petName);
        pet.put("status",status);

        JSONArray photoUrls= new JSONArray();
        photoUrls.put(photoUrl);


        JSONObject tag = new JSONObject();
        tag.put("id", tagId);
        tag.put("name", tagName);

        JSONArray tags = new JSONArray();
        tags.put(tag);

        JSONObject category = new JSONObject();
        category.put("id", categoryId);
        category.put("name", categoryName);

        pet.put("category",category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);


        Response response =
                given()
                        .spec(request).log().all()
                        .when()
                        .body(pet.toString())
                        .post(getBaseAPI(environment) + "/pet")
                        .then()
                        .extract().response();


        return response;


    }

    public static Response updateExistingPet (String environment, int id, String petName, String status,
                                              int categoryId, String categoryName, int tagId, String tagName, String photoUrl) {
        RequestSpecification request = addHeaders(environment);
        JSONObject pet = new JSONObject();
        pet.put("id", id);
        pet.put("name", petName);
        pet.put("status",status);

        JSONArray photoUrls= new JSONArray();
        photoUrls.put(photoUrl);


        JSONObject tag = new JSONObject();
        tag.put("id", categoryId);
        tag.put("name", categoryName);

        JSONArray tags = new JSONArray();
        tags.put(tag);

        JSONObject category = new JSONObject();
        category.put("id", categoryId);
        category.put("name", categoryName);

        pet.put("category",category);
        pet.put("photoUrls", photoUrls);
        pet.put("tags", tags);
        Response response =
                given()
                        .spec(request)
                        .body(pet)
                        .when()
                        .put(getBaseAPI(environment) + "/pet")
                        .then()
                        .extract().response();


        return response;


    }

    public static Response getPetById (String environment,  int petId) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .pathParam("petId" , petId)
                        .get(getBaseAPI(environment) + "/pet/{petId}")
                        .then()
                        .extract().response();


        return response;

    }

    public static Response deletePetById (String environment, int petId) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .pathParam("petId" , petId)
                        .delete(getBaseAPI(environment) + "/pet/{petId}")
                        .then()
                        .extract().response();


        return response;

    }

    public static Response uploadPetImage (String environment, String petId) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .pathParam("petId" , petId)
                        .post(getBaseAPI(environment) + "/pet/{petId}/uploadImage")
                        .then()
                        .extract().response();


        return response;


    }

    public static Response findByStatus (String environment, String status) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .param("status", status)
                        .when()
                        .get(getBaseAPI(environment) + "/pet/findByStatus")
                        .then()
                        .extract().response();

        return response;

    }


    public static Response findBTags (String environment, String tag) {
        RequestSpecification request = addHeaders(environment);
        Response response =
                given()
                        .spec(request)
                        .when()
                        .param("tags", tag)
                        .get(getBaseAPI(environment) + "/pet/findByTags")
                        .then()
                        .extract().response();


        return response;

    }

}
