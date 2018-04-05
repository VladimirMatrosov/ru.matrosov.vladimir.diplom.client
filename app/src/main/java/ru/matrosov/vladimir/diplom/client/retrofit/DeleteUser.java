package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;

public interface DeleteUser {
    @POST("/deleteUser")
    Call<DeleteUserResponse> deletingUser(@Query(EMAIL_KEY) String email);
}
