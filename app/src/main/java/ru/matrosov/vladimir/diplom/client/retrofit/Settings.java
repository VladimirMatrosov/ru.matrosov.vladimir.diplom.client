package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Settings {

    @POST("/changeUser")
    Call<SettingResponse> setting(@Query("email") String email, @Query("firstName") String firstName,
                                  @Query("lastname") String lastName, @Query("newEmail") String newEmail,
                                  @Query("post") String post);
}
