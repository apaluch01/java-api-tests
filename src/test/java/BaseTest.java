import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.HttpPost;
import org.testng.annotations.BeforeSuite;
import utility.TestSteps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utility.TestConfigurationData.*;

public abstract class BaseTest extends TestSteps {

    protected RequestSpecification requestSpec;
    public static RequestSpecBuilder builder;

    @BeforeSuite
    public void setupRequestSpecBuilder() {
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

    HttpPost setupHttpPost(String name) {
        HttpPost post = null;

        try (FileReader file = new FileReader(CONFIG_DATA_FILE_PATH);
             BufferedReader reader = new BufferedReader(file)) {
            post = new HttpPost(reader.readLine() + "/1/boards/?name=" + name +
                    "&key=" + reader.readLine() +
                    "&token=" + reader.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }

        return post;
    }

    public String createBoard(String name) {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", name).
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        return(response.then().extract().path("id"));
    }

    void deleteBoard(String id) {
        given().spec(requestSpec).when().delete("/1/boards/" + id);
    }
}
