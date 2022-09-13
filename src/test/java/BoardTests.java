import clients.ApacheClient;
import clients.RetrofitClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BoardInfo;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    public static final List<String> ids = new ArrayList<>();

    @AfterSuite
    void cleanUp(){
        ids.forEach(BaseTest::deleteBoard);
        ids.clear();
    }

    @Test
    void apacheShouldCreateBoard() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(ApacheClient.setupHttpPost("postApache"));

        ObjectMapper objectMapper = new ObjectMapper();
        BoardInfo board = objectMapper.readValue(response.getEntity().getContent(), BoardInfo.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(board.getName(), "postApache");
        ids.add(board.getId());
    }

    @Test
    void okHttpShouldCreateBoard() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = clients.OkHttpClient.setupOkHttp("postOkHttp");
        Call call = client.newCall(request);

        okhttp3.Response response = call.execute();
        ObjectMapper objectMapper = new ObjectMapper();
        BoardInfo board = objectMapper.readValue(response.body().string(), BoardInfo.class);

        Assert.assertEquals(response.code(), 200);
        Assert.assertEquals(board.getName(), "postOkHttp");
        ids.add(board.getId());
    }

    @Test
    void retrofitShouldCreateBoard() throws IOException {
        retrofit2.Call<BoardInfo> client = RetrofitClient.setupRetrofit();

        BoardInfo board = client.execute().body();

        Assert.assertEquals(board.getName(), "postRetrofit");
        ids.add(board.getId());
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
        ids.add(board.getId());
    }

    @Test
    void shouldGetBoardById() throws JsonProcessingException {
        BoardInfo board = createAndReturnBoard("get");
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().get("/1/boards/" + board.getId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(board.getName(), "get");
        Assert.assertTrue(checkIfShortUrlMatches(board.getShortUrl()));
        ids.add(board.getId());
    }

    @Test
    void shouldUpdateBoardById() {
        BoardInfo board = createAndReturnBoard("put");
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.when().put("/1/boards/" + board.getId());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(board.getName(), "put");
        Assert.assertTrue(checkIfShortUrlMatches(board.getShortUrl()));
        ids.add(board.getId());
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
