package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.CHAT_NAME_KEY;
import static constants.RequestParameters.EMAIL_KEY;

public interface AddChat {
    @POST("/addChat")
    Call<AddChatResponse> addingChat(@Query(EMAIL_KEY) String email, @Query(CHAT_NAME_KEY) String chatName);
}
