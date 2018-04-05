package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DeleteUser {
    @POST("/deleteUser")
    Call<DeleteUserResponse> deletingUser(@Query("email") String email);
}
