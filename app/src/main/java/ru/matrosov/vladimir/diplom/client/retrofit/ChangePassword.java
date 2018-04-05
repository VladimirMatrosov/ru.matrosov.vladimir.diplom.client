package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChangePassword {

    @POST("/changePassword")
    Call<ChangePasswordResponse> changingPassword(@Query("email") String email, @Query("password") String password,
                                                  @Query("password1") String password1, @Query("password2")
                                                  String password2);
}
