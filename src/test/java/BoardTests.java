import clients.ApacheClient;
import clients.OkHttpClient;
import clients.RetrofitClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BoardInfo;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BoardTests extends BaseTest {
    private static final List<String> IDS = new ArrayList<>();

    @AfterSuite
    void cleanUp(){
        IDS.forEach(BaseTest::deleteBoard);
        IDS.clear();
    }

    @Test
    void apacheShouldCreateBoard() throws IOException {
        ApacheClient apacheClient = new ApacheClient();
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(apacheClient.setupHttpPost("postApache"));

        BoardInfo board = apacheClient.getModel(response);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(board.getName(), "postApache");
        IDS.add(board.getId());
    }

    @Test
    void okHttpShouldCreateBoard() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        okhttp3.Response response = okHttpClient.createBoard("postOkHttp");

        BoardInfo board = okHttpClient.getModel(response);

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(board.getName(), "postOkHttp");
        IDS.add(board.getId());
    }

    @Test
    void retrofitShouldCreateBoard() throws IOException {
        RetrofitClient retrofitClient = new RetrofitClient();
        retrofit2.Call<BoardInfo> client = retrofitClient.setupRetrofit();

        BoardInfo board = client.execute().body();

        Assert.assertEquals(board.getName(), "postRetrofit");
        IDS.add(board.getId());
    }

    @Test
    void shouldCreateBoard() {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", "post").
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        BoardInfo board = response.getBody().as(BoardInfo.class);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(board.getName(), "post");
        IDS.add(board.getId());
    }

    @Test
    void shouldGetBoardById() throws JsonProcessingException {
        BoardInfo board = createAndReturnBoard("get");
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().get("/1/boards/" + board.getId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(board.getName(), "get");
        Assert.assertTrue(checkIfShortUrlMatches(board.getShortUrl()));
        IDS.add(board.getId());
    }

    @Test
    void shouldUpdateBoardById() {
        BoardInfo board = createAndReturnBoard("put");
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().put("/1/boards/" + board.getId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(board.getName(), "put");
        Assert.assertTrue(checkIfShortUrlMatches(board.getShortUrl()));
        IDS.add(board.getId());
    }

    @Test
    void shouldDeleteBoardById() {
        BoardInfo board = createAndReturnBoard("delete");
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().delete("/1/boards/" + board.getId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(requestSpecification.when().get("/1/boards/" + board.getId()).getStatusCode(),
                404);
    }
}
