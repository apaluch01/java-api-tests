package clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.BoardInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class ApacheClient extends BaseConfig{
    public static HttpPost setupHttpPost(String name) {
        HttpPost post = new HttpPost(baseUrl + "/1/boards/?name=" + name + "&key=" + key + "&token=" + token);

        return post;
    }

    public static BoardInfo getModel(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getEntity().getContent(), BoardInfo.class);
    }

}
