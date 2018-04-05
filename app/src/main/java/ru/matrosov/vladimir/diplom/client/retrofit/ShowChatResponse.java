package ru.matrosov.vladimir.diplom.client.retrofit;

import java.util.List;

import data.Chatroom;
import data.Message;
import data.User;

public class ShowChatResponse {
    int status;
    List<User> users;
    List<Message> messages;

    public ShowChatResponse(int status, List<User> users, List<Message> messages) {
        this.status = status;
        this.users = users;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "ShowChatResponse{" +
                "status=" + status +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
