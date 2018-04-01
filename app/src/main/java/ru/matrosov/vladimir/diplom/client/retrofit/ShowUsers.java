package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ShowUsers {

    @POST("/showUsers")
    Call<ServerResponse> showingUsers();
}
