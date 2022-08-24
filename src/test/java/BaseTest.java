import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utility.TestSteps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static utility.TestConfigurationData.*;

public abstract class BaseTest extends TestSteps {

    protected RequestSpecification requestSpec;
    public static RequestSpecBuilder builder;

    @BeforeSuite
    public void setupRequestSpecBuilder()
    {
        builder = new RequestSpecBuilder();
        try (FileReader file = new FileReader(CONFIG_DATA_FILE_PATH);
             BufferedReader reader = new BufferedReader(file)) {
            builder.setBaseUri(reader.readLine());
            builder.addQueryParam("key", reader.readLine());
            builder.addQueryParam("token", reader.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        requestSpec = builder.build();
    }
}
