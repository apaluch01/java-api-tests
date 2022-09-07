import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BoardInfo;
import models.Root;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.client.methods.HttpPost;
import org.testng.annotations.BeforeSuite;
import retrofit2.Retrofit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static utility.TestConfigurationData.*;

public abstract class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(BaseTest.class));
    protected RequestSpecification requestSpec;
    protected static String baseUrl;
    protected String key;
    protected String token;

    @BeforeSuite
    void getConfigData() {
        try (FileReader file = new FileReader(CONFIG_DATA_FILE_PATH);
             BufferedReader reader = new BufferedReader(file)) {
            baseUrl = reader.readLine();
            key = reader.readLine();
            token = reader.readLine();
        } catch (IOException e) {
            LOGGER.info((Supplier<String>) e);
        }
    }

    @BeforeSuite
    public void setupRequestSpecBuilder() {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(baseUrl);
        builder.addQueryParam("key", key);
        builder.addQueryParam("token", token);

        requestSpec = builder.build();
    }

    HttpPost setupHttpPost(String name) {
        HttpPost post = new HttpPost(baseUrl + "/1/boards/?name=" + name + "&key=" + key + "&token=" + token);

        return post;
    }

    Request setupOkHttp(String name) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/1/boards/?name=" + name).newBuilder();
        urlBuilder.addQueryParameter("key", key).
                addQueryParameter("token", token);

        String url = urlBuilder.build().toString();
        RequestBody reqBody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(url)
                .method("POST", reqBody)
                .build();

        return request;
    }

    Retrofit setupRetrofit(String name) {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(baseUrl + "/1/boards/?name=" + name)
                        .build();

        return retrofit;
    }

    String getId(String body) {

        Pattern pattern = Pattern.compile("(\"id\":\")([0-9a-z]+)");
        Matcher matcher = pattern.matcher(body);

        matcher.find();
        return (matcher.group(2));
    }

    String getName(String body) {
        Pattern pattern = Pattern.compile("(\"name\":\")([^\"]+)");
        Matcher matcher = pattern.matcher(body);

        matcher.find();
        return (matcher.group(2));
    }

    public String createBoardAndReturnId(String name) {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", name).
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        return(response.then().extract().path("id"));
    }

    boolean checkIfShortUrlMatches(String body) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Root root = om.readValue(body, Root.class);

        Pattern pattern = Pattern.compile("(https://trello\\.com/b/)(\\w{8})");
        Matcher matcher = pattern.matcher(root.getShortUrl());

        return (matcher.matches());
    }

    void deleteBoard(String id) {
        given().spec(requestSpec).when().delete("/1/boards/" + id);
    }
}
