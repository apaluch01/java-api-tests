import clients.BaseConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Root;
import org.testng.annotations.BeforeSuite;

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
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(BaseConfig.class));
    public static RequestSpecification requestSpec;
    BaseConfig config = new BaseConfig();
    @BeforeSuite
    void getConfigData() {
        try (FileReader file = new FileReader(CONFIG_DATA_FILE_PATH);
             BufferedReader reader = new BufferedReader(file)) {
            config.setBaseUrl(reader.readLine());
            config.setKey(reader.readLine());
            config.setToken(reader.readLine());
        } catch (IOException e) {
            LOGGER.info((Supplier<String>) e);
        }

        requestSpec = config.setupRequestSpecBuilder();
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
