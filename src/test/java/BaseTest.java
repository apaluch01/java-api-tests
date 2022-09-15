import clients.BaseConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.BoardInfo;
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
            BaseConfig.setBaseUrl(reader.readLine());
            BaseConfig.setKey(reader.readLine());
            BaseConfig.setToken(reader.readLine());
        } catch (IOException e) {
            LOGGER.info((Supplier<String>) e);
        }

        requestSpec = config.setupRequestSpecBuilder();
    }

    public BoardInfo createAndReturnBoard(String name) {
        RequestSpecification requestSpecification = given().spec(requestSpec);

        Response response = requestSpecification.queryParam("name", name).
                contentType(ContentType.JSON).
                when().post("/1/boards/");

        return(response.getBody().as(BoardInfo.class));
    }

    boolean checkIfShortUrlMatches(String shortUrl) {
        Pattern pattern = Pattern.compile("(https://trello\\.com/b/)(\\w{8})");
        Matcher matcher = pattern.matcher(shortUrl);

        return (matcher.matches());
    }

    static void deleteBoard(String id) {
        given().spec(requestSpec).when().delete("/1/boards/" + id);
    }
}
