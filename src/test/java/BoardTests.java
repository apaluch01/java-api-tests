import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static utility.BaseTest.requestSpec;
import static utility.TestConfigurationData.*;

public class BoardTests {

    @Test
    void createBoard() {
        ValidatableResponse response = given().
                baseUri("https://api.trello.com").
                queryParams("name", "test", "key", "261478d38cec68e8e339c84b6096692d", "token", "10f8cec355295b29a25d24b2ae6a82b0fdda92c603bab4b2edda2fca4e88f4ed").
                when().
                post("/1/boards/").
                then().
                assertThat().
                statusCode(415);

    }

    @Test
    void shouldGetBoardById() {
        RequestSpecification requestSpecification = given().baseUri(BASE_URI).
                queryParams("key", KEY, "token", TOKEN);
        Response response = requestSpecification.when().get("/1/boards/630336c58f72ce00e9bd2b04");

        System.out.println(response.then().extract().response().asString());
        Assert.assertEquals(response.statusCode(), 200);
    }
}
