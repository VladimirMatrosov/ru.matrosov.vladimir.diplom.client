package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import data.Chatroom;

public class ShowChatroomsResponse {
    int status;

    @SerializedName("responseObject")
    List<Chatroom> chatrooms;

    public ShowChatroomsResponse(int status, List<Chatroom> chatrooms) {
        this.status = status;
        this.chatrooms = chatrooms;
    }

    public int getStatus() {
        return status;
    }

    public List<Chatroom> getChatrooms() {
        return chatrooms;
    }

    @Override
    public String toString() {
        return "ShowChatrooms{" +
                "status=" + status +
                ", chatrooms=" + chatrooms +
                '}';
    }
}
