package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SendMessage {
    @POST("/sendMessage")
    Call<SendMessageResponse> sendingMessage(@Query("email") String email, @Query("text") String text,
                                             @Query("idChat") Integer idChat);
}
