package clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import models.BoardInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ApacheClient extends BaseConfig{
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient client = HttpClientBuilder.create().build();
    private HttpPost post = new HttpPost(baseUrl + "/1/boards/?name=" + "postApache" + "&key=" + key + "&token=" + token);

    public HttpResponse setupHttpPost() throws IOException {
        return client.execute(post);
    }

    public BoardInfo getModel(HttpResponse response) throws IOException {
        return objectMapper.readValue(response.getEntity().getContent(), BoardInfo.class);
    }

    public BoardInfo createBoardAndReturnModel() throws IOException {
        return objectMapper.readValue(client.execute(post).getEntity().getContent(), BoardInfo.class);
    }
}
