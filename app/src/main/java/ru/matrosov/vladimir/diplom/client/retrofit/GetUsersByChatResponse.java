package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import data.User;

public class GetUsersByChatResponse {
    int status;
    @SerializedName("responseObject")
    ArrayList<User> users;

    public int getStatus() {
        return status;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public GetUsersByChatResponse(int status, ArrayList<User> users) {

        this.status = status;
        this.users = users;
    }

    @Override
    public String toString() {
        return "GetUsersByChatResponse{" +
                "status=" + status +
                ", users=" + users +
                '}';
    }
}
