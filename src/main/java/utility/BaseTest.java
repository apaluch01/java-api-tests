package utility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import static utility.TestConfigurationData.*;

public abstract class BaseTest extends TestSteps{

    public static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    public static RequestSpecBuilder builder;

    @BeforeSuite
    public static void setupRequestSpecBuilder()
    {
        builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URI);
        builder.addQueryParam("key", KEY);
        builder.addQueryParam("token", TOKEN);
        requestSpec = builder.build();
    }

    @BeforeMethod
    public void beforeMethod() {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

}
