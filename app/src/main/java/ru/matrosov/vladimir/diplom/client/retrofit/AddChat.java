package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddChat {
    @POST("/addChat")
    Call<AddChatResponse> addingChat(@Query("email") String email, @Query("chatName") String chatName);
}
