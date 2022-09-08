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

    public static Request setupOkHttp(String name) {
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
