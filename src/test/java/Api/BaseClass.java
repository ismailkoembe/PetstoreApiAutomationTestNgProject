package Api;

import Configuration.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
    public static String getBaseAPI(String environment) {
        return PropManager.getProperties(environment, "baseApi");
    }

    protected static RequestSpecification addHeaders(String environment) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json");
//                .addHeader("Authorization", bearer)
        RequestSpecification requestSpec = builder.build();
        return requestSpec;
    }
}
