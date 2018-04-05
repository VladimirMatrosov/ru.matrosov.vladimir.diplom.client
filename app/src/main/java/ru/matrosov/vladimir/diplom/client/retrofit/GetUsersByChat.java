package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetUsersByChat {
    @POST("/getUsersByChat")
    Call<GetUsersByChatResponse> gettingUsersByChat(@Query("id_chat") Integer idChat);
}
