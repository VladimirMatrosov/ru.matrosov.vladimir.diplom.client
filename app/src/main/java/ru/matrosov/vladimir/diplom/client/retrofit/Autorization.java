package ru.matrosov.vladimir.diplom.client.retrofit;

import data.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.PASSWORD_KEY;

public interface Autorization {
    @POST("/autorization")
    Call<AutorizationResponse> autorize(@Query(EMAIL_KEY) String email, @Query(PASSWORD_KEY) String password);
}
