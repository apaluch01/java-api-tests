package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import models.BoardInfo;
import models.RetrofitAPI;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.http.client.methods.HttpPost;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class BaseConfig {
    public static RequestSpecification requestSpecification;
    @Setter
    protected static String baseUrl;
    @Setter
    protected static String key;
    @Setter
    protected static String token;

    public RequestSpecification setupRequestSpecBuilder() {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(baseUrl);
        builder.addQueryParam("key", key);
        builder.addQueryParam("token", token);

        requestSpecification = builder.build();
        return requestSpecification;
    }

    public static Call<BoardInfo> setupRetrofit() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.trello.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);

        retrofit2.Call<BoardInfo> response = service.createBoard(key, token);
        return response;
    }
}
