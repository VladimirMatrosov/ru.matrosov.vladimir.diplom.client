package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Registration {
    @POST("/registration")
    Call<ServerResponse> register(@Query("firstname") String firstname, @Query("lastname") String lastname,
                                  @Query("email") String email, @Query("post") String post,
                                  @Query("password1") String password1, @Query("password2") String password2);
}
