package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.Chatroom;

public class AddUserToChatResponse {
    int status;
    @SerializedName("responseObject")
    Chatroom chatroom;

    public AddUserToChatResponse(int status, Chatroom chatroom) {
        this.status = status;
        this.chatroom = chatroom;
    }

    public int getStatus() {
        return status;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    @Override
    public String toString() {
        return "AddUserToChatResponse{" +
                "status=" + status +
                ", chatroom=" + chatroom +
                '}';
    }
}
