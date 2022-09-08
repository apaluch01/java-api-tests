package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;

public class BaseConfig {
    public static RequestSpecification requestSpecification;
    @Setter
    protected static String baseUrl;
    @Setter
    protected static String key;
    @Setter
    protected static String token;

    public RequestSpecification setupRequestSpecBuilder() {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(baseUrl);
        builder.addQueryParam("key", key);
        builder.addQueryParam("token", token);

        requestSpecification = builder.build();
        return requestSpecification;
    }
}
