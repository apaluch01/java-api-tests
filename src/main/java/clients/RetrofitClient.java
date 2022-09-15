package clients;

import models.BoardInfo;
import models.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient extends BaseConfig {
    public Call<BoardInfo> setupRetrofit() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        RetrofitAPI service = retrofit.create(RetrofitAPI.class);

        return service.createBoard(key, token);
    }
}
