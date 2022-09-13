package clients;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpClient extends BaseConfig{
    public static Request setupOkHttp(String name) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/1/boards/?name=" + name).newBuilder();
        urlBuilder.addQueryParameter("key", key).
                addQueryParameter("token", token);

        String url = urlBuilder.build().toString();
        RequestBody reqBody = RequestBody.create(null, new byte[0]);

        return new Request.Builder()
                .url(url)
                .method("POST", reqBody)
                .build();
    }
}
