import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseTest;

import static io.restassured.RestAssured.given;
import static utility.BaseTest.requestSpec;
import static utility.TestConfigurationData.*;

public class BoardTests extends BaseTest {

    @Test
    String shouldCreateBoard() {
        RequestSpecification requestSpecification = given().baseUri(BASE_URI).
                queryParam("name", "test1").queryParam("key", KEY).queryParam("token", TOKEN);
        Response response1 = requestSpecification.when().post("/1/boards/");

        String id = given().baseUri(BASE_URI).
                queryParam("name", "test1").queryParam("key", KEY).queryParam("token", TOKEN).
                contentType(ContentType.JSON).log().all().post("/1/boards/").
                then().extract().path("id");
        System.out.print(id);
        //System.out.println(response1.then().extract().response().asString());
        //Assert.assertEquals(response1.statusCode(), 200);
        return id;
    }

    String id = shouldCreateBoard();

    @Test
    void shouldGetBoardById() {
        RequestSpecification requestSpecification = given().baseUri(BASE_URI).
                queryParam("key", KEY).queryParam("token", TOKEN);
        Response responseGet = requestSpecification.when().get("/1/boards/"+id);

        System.out.println(responseGet.then().extract().response().asString());
        Assert.assertEquals(responseGet.statusCode(), 200);
    }
}
