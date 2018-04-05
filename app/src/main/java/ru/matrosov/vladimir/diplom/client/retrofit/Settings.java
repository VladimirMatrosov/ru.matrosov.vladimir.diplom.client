package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.FIRST_NAME_KEY;
import static constants.RequestParameters.LAST_NAME_KEY;
import static constants.RequestParameters.NEW_EMAIL_KEY;
import static constants.RequestParameters.POST_KEY;

public interface Settings {

    @POST("/changeUser")
    Call<SettingResponse> setting(@Query(EMAIL_KEY) String email, @Query(FIRST_NAME_KEY) String firstName,
                                  @Query(LAST_NAME_KEY) String lastName, @Query(NEW_EMAIL_KEY) String newEmail,
                                  @Query(POST_KEY) String post);
}
