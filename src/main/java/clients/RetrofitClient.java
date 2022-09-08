package clients;

import com.fasterxml.jackson.databind.ser.Serializers;
import models.BoardInfo;
import models.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient extends BaseConfig {
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
