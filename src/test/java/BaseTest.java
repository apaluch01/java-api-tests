import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utility.TestSteps;

import static utility.TestConfigurationData.*;

public abstract class BaseTest extends TestSteps {

    protected RequestSpecification requestSpec;
    public static RequestSpecBuilder builder;

    @BeforeSuite
    public void setupRequestSpecBuilder()
    {
        builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URI);
        builder.addQueryParam("key", KEY);
        builder.addQueryParam("token", TOKEN);
        requestSpec = builder.build();
    }
}
