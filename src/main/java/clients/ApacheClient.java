package clients;

import org.apache.http.client.methods.HttpPost;

public class ApacheClient extends BaseConfig{
    public static HttpPost setupHttpPost(String name) {
        HttpPost post = new HttpPost(baseUrl + "/1/boards/?name=" + name + "&key=" + key + "&token=" + token);

        return post;
    }
}
