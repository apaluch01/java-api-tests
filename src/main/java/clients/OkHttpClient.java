package clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.BoardInfo;
import okhttp3.*;

import java.io.IOException;

public class OkHttpClient extends BaseConfig{
    ObjectMapper objectMapper = new ObjectMapper();

    public String setupOkHttp(String name) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/1/boards/?name=" + name).newBuilder();
        urlBuilder.addQueryParameter("key", key).
                addQueryParameter("token", token);

        return urlBuilder.build().toString();
    }

    public Response createBoard(String name) throws IOException {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        RequestBody reqBody = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(setupOkHttp(name))
                .method("POST", reqBody)
                .build();

        Call call = client.newCall(request);

        return call.execute();
    }

    public BoardInfo getModel(Response response) throws IOException {
        return objectMapper.readValue(response.body().string(), BoardInfo.class);
    }
}
