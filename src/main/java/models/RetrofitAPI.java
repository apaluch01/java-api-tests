package models;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("/1/boards/?name=postRetrofit")
    Call<BoardInfo> createBoard(@Query("key") String key, @Query("token") String token);
}
