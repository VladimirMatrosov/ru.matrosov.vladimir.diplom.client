package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShowChatrooms {
    @POST("/showChatrooms")
    Call<ShowChatroomsResponse> showingChatrooms(@Query("email") String email);
}
