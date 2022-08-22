package utility;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest extends TestSteps{
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeSuite
    public void setBaseURI() {
/*
        AllureLogger.logToAllure("The base URI is : "+readConfigurationFile("Base_URI"));
        requestSpec = new RequestSpecBuilder().
                setBaseUri(readConfigurationFile("Base_URI")).
                build();*/

    }

    @BeforeMethod
    public void beforeMethod() {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

}
