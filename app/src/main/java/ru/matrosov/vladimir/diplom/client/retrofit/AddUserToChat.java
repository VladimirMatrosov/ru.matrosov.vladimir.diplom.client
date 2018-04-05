package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AddUserToChat {
    @POST("/addUserToChat")
    Call<AddUserToChatResponse> addingUserToChat(@Query("idChat") Integer idChat, @Query("email") String email);
}
