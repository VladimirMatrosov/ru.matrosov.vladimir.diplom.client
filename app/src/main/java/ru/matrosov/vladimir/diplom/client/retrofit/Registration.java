package ru.matrosov.vladimir.diplom.client.retrofit;

import data.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.FIRST_NAME_KEY;
import static constants.RequestParameters.LAST_NAME_KEY;
import static constants.RequestParameters.PASSWORD_1_KEY;
import static constants.RequestParameters.PASSWORD_2_KEY;
import static constants.RequestParameters.POST_KEY;

public interface Registration {
    @POST("/registration")
    Call<RegistrationResponse> register(@Query(FIRST_NAME_KEY) String firstname, @Query(LAST_NAME_KEY) String lastname,
                                        @Query(EMAIL_KEY) String email, @Query(POST_KEY) String post,
                                        @Query(PASSWORD_1_KEY) String password1, @Query(PASSWORD_2_KEY) String password2);
}
