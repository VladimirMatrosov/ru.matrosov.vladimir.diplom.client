package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.ID_CHAT_KEY;

public interface GetUsersByChat {
    @POST("/getUsersByChat")
    Call<GetUsersByChatResponse> gettingUsersByChat(@Query(ID_CHAT_KEY) Integer idChat);
}
