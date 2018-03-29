package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Autorization {
    @POST("/autorization")
    Call<AutorizationResponse> autorize(@Query("email") String email, @Query("password") String password);
}
