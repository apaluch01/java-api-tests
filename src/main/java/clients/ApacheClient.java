package clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.BoardInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ApacheClient extends BaseConfig{
    public HttpResponse setupHttpPost(String name) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(baseUrl + "/1/boards/?name=" + name + "&key=" + key + "&token=" + token);

        return client.execute(post);
    }

    public BoardInfo getModel(HttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getEntity().getContent(), BoardInfo.class);
    }

}
