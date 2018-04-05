package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.PASSWORD_1_KEY;
import static constants.RequestParameters.PASSWORD_2_KEY;
import static constants.RequestParameters.PASSWORD_KEY;

public interface ChangePassword {

    @POST("/changePassword")
    Call<ChangePasswordResponse> changingPassword(@Query(EMAIL_KEY) String email, @Query(PASSWORD_KEY) String password,
                                                  @Query(PASSWORD_1_KEY) String password1, @Query(PASSWORD_2_KEY)
                                                  String password2);
}
