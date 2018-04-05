package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.EMAIL_USER_KEY;

public interface OpenChat {
    @POST("/openChat")
    Call<OpenChatResponse> openingChat(@Query(EMAIL_KEY) String email, @Query(EMAIL_USER_KEY) String email_user);
}
