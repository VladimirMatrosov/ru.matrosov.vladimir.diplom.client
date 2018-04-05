package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import data.User;

public class ShowUsersResponse {
    int status;
    @SerializedName("responseObject")
    List<User> users;

    public ShowUsersResponse(int status, List<User> users) {
        this.status = status;
        this.users = users;
    }

    public int getStatus() {
        return status;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ShowUsersResponse{" +
                "status=" + status +
                ", users=" + users +
                '}';
    }
}
