package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.User;

public class SettingResponse {
    int status;

    @SerializedName("responseObject")
    User user;

    public SettingResponse(int status, User user) {
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
        return "SettingResponse{" +
                "status=" + status +
                ", user=" + user +
                '}';
    }
}
