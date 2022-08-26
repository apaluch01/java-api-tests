import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.IOException;
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

    @Test
    void apacheShouldCreateBoard() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(setupHttpPost("postApache"));

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(findName(body), "postApache");
        ids.add(findId(body));
    }

    @Test
    void okHttpShouldCreateBoard() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(setupOkHttp("postOkHttp"));
        okhttp3.Response response = call.execute();

        String body = response.body().string();
        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(findName(body), "postOkHttp");
        ids.add(findId(body));
    }

    @Test
    void shouldCreateBoard() {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", "post").
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        ids.add(response.then().extract().path("id"));

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "post");
    }

    @Test
    void shouldGetBoardById() {
        id = new StringBuilder(createBoard("get"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().get("/1/boards/" + id);

        LOGGER.info(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "get");
    }

    @Test
    void shouldUpdateBoardById() {
        id = new StringBuilder(createBoard("put"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().put("/1/boards/" + id);

        LOGGER.info(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "put");
    }

    @Test
    void shouldDeleteBoardById() {
        id = new StringBuilder(createBoard("delete"));

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().delete("/1/boards/" + id);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(requestSpecification.when().get("/1/boards/" + id).getStatusCode(), 404);
    }
}
