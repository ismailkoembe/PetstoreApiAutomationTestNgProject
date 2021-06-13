package Responses;


import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class UserResponse {
    Response response ;

    public UserResponse() {
    }

    public UserResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getUserId(){
        return response.jsonPath().getInt("id");
    }

    public String getUserFirstName(){
        return response.jsonPath().getString("firstName");
    }

    public String getUserLastName(){
        return response.jsonPath().getString("lastName");
    }

    public String getUserEmail(){
        return response.jsonPath().getString("email");
    }

    public String getUserPassword(){
        return response.jsonPath().getString("password");
    }

    public String getUserPhone(){
        return response.jsonPath().getString("phone");
    }

    public String getUserName(){
        return response.jsonPath().getString("username");
    }

    public Object getUserDetailWithQuery(String path){
        return response.jsonPath().getString(path);
    }

    public int getUserStatus(){
        return response.jsonPath().getInt("userStatus");
    }
    public int getUserListSize(){
        return response.jsonPath().getList("id").size();
    }
    public List<Map<String, Object>> getCreatedUserUserList(){
        return response.jsonPath().get();
    }

    public String getSessionNumber(){
        return response.asString().split(": ")[1];
    }
}
