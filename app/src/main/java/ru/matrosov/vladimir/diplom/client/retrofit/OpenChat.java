package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OpenChat {
    @POST("/openChat")
    Call<OpenChatResponse> openingChat(@Query("email") String email, @Query("email_user") String email_user);
}
