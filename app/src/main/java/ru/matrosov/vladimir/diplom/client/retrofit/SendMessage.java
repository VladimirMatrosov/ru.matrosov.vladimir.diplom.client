package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.ID_CHAT_KEY;
import static constants.RequestParameters.TEXT_KEY;

public interface SendMessage {
    @POST("/sendMessage")
    Call<SendMessageResponse> sendingMessage(@Query(EMAIL_KEY) String email, @Query(TEXT_KEY) String text,
                                             @Query(ID_CHAT_KEY) Integer idChat);
}
