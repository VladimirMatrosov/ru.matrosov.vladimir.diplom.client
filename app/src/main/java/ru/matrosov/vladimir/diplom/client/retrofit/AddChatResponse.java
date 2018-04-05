package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.Chatroom;

public class AddChatResponse {
    int status;
    @SerializedName("responseObject")
    Chatroom chatroom;

    public AddChatResponse(int status, Chatroom chatroom) {
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
        return "AddChatResponse{" +
                "status=" + status +
                ", chatroom=" + chatroom +
                '}';
    }
}
