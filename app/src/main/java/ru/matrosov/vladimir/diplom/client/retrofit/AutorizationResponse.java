package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.User;

public class AutorizationResponse {
    private final int status;
    @SerializedName("responseObject")
    private final User user;

    public AutorizationResponse(int status, User user) {
        this.status = status;
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "AutorizationResponse{" +
                "status=" + status +
                ", user=" + user +
                '}';
    }
}