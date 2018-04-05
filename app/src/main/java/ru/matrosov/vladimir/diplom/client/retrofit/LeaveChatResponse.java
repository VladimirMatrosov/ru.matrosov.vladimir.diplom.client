package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.User;

public class LeaveChatResponse {
    int status;
    @SerializedName("responseObject")
    User user;

    @Override
    public String toString() {
        return "LeaveChatResponse{" +
                "status=" + status +
                ", user=" + user +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public LeaveChatResponse(int status, User user) {
        this.status = status;
        this.user = user;
    }
}
