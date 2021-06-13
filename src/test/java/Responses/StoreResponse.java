package Responses;

import io.restassured.response.Response;

public class StoreResponse {
    Response response ;

    public StoreResponse() {
    }

    public StoreResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Object detailsOfOrderByType(String type){
        return response.jsonPath().get(type);
    }

    public int getQuantityOfInventoryByType(String type){
        return response.jsonPath().getInt(type);
    }
}
