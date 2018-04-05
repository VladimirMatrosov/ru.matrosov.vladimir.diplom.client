package ru.matrosov.vladimir.diplom.client.retrofit;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static constants.RequestParameters.EMAIL_KEY;
import static constants.RequestParameters.ID_CHAT_KEY;

public interface LeaveChat {
    @POST("/leaveChat")
    Call<LeaveChatResponse> leavingChat(@Query(ID_CHAT_KEY) Integer idChat, @Query(EMAIL_KEY) String email);
}
