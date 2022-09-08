import clients.ApacheClient;
import clients.RetrofitClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BoardInfo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
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
        ids.forEach(BaseTest::deleteBoard);
        ids.clear();
    }

    @Test
    void apacheShouldCreateBoard() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(ApacheClient.setupHttpPost("postApache"));

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(getName(body), "postApache");
        ids.add(getId(body));
    }

    @Test
    void okHttpShouldCreateBoard() throws IOException  {
        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(clients.OkHttpClient.setupOkHttp("postOkHttp"));
        okhttp3.Response response = call.execute();

        String body = response.body().string();
        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(getName(body), "postOkHttp");
        ids.add(getId(body));
    }

    @Test
    void retrofitShouldCreateBoard() throws IOException {
        retrofit2.Call<BoardInfo> client = RetrofitClient.setupRetrofit();

        ids.add(client.execute().body().getId());
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
    void shouldGetBoardById() throws JsonProcessingException {
        id = new StringBuilder(createBoardAndReturnId("get"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().get("/1/boards/" + id);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "get");
        Assert.assertTrue(checkIfShortUrlMatches(response.then().extract().response().asString()));
    }

    @Test
    void shouldUpdateBoardById() throws JsonProcessingException {
        id = new StringBuilder(createBoardAndReturnId("put"));
        ids.add(id.toString());

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().put("/1/boards/" + id);

        LOGGER.info(response.then().extract().response().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.then().extract().path("name"), "put");
        Assert.assertTrue(checkIfShortUrlMatches(response.then().extract().response().asString()));
    }

    @Test
    void shouldDeleteBoardById() {
        id = new StringBuilder(createBoardAndReturnId("delete"));

        RequestSpecification requestSpecification = given().spec(requestSpec);
        Response response = requestSpecification.when().delete("/1/boards/" + id);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(requestSpecification.when().get("/1/boards/" + id).getStatusCode(), 404);
    }
}
