import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static io.restassured.RestAssured.given;

public class BoardTests extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(BoardTests.class));
    public static final List<String> ids = new ArrayList();

    StringBuilder id = new StringBuilder();
    @AfterSuite
    void cleanUp(){
        ids.forEach(id -> deleteBoard(id));
        ids.clear();
    }

    @Test (priority = 1)
    void shouldCreateBoard() {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", "post").
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        ids.add(response.then().extract().path("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "post");
    }

    @Test (priority = 2)
    void shouldGetBoardById() {
        id = new StringBuilder(createBoard("get"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().get("/1/boards/" + id);

        LOGGER.info(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "get");
    }

    @Test (priority = 2)
    void shouldUpdateBoardById() {
        id = new StringBuilder(createBoard("put"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().put("/1/boards/" + id);

        LOGGER.info(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "put");
    }

    @Test (priority = 3)
    void shouldDeleteBoardById() {
        id = new StringBuilder(createBoard("delete"));

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().delete("/1/boards/" + id);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(requestSpecification.when().get("/1/boards/" + id).getStatusCode(), 404);
    }
}
