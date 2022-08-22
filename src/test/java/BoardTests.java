import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static utility.TestConfigurationData.*;

public class BoardTests extends BaseTest {

    String id = "";
    @Test (priority = 1)
    void shouldCreateBoard() {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().post("/1/boards/");

        id = given().baseUri(BASE_URI).
                queryParam("name", "test1").queryParam("key", KEY).queryParam("token", TOKEN).
                contentType(ContentType.JSON).log().all().post("/1/boards/").
                then().extract().path("id");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 2)
    void shouldGetBoardById() {
        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().get("/1/boards/" + id);

        System.out.println(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 2)
    void shouldUpdateBoardById() {
        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().put("/1/boards/" + id);

        System.out.println(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test (priority = 3)
    void shouldDeleteBoardById() {
        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().delete("/1/boards/" + id);

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
