package ru.matrosov.vladimir.diplom.client.retrofit;

import data.User;

/**
 * Created by Valeriy on 17.03.2018.
 */

public class AutorizationResponse {
    private final int status;
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