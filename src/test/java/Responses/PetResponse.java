package Responses;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class PetResponse {
    Response response;

    public PetResponse() {
    }

    public PetResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getCategoryInformation(){
        return response.jsonPath().getMap("category");

    }

    public List<Map<String, Object>> getTagsInformation(){
        return response.jsonPath().getList("tags");

    }

    public List<String> getPhotoUrls(){
        return response.jsonPath().getList("photoUrls");

    }


    public Object getInformationByType(String type){
        return response.jsonPath().get(type);

    }

}
