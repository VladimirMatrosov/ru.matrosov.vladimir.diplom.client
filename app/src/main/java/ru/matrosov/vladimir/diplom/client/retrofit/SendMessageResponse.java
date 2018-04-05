package ru.matrosov.vladimir.diplom.client.retrofit;

import com.google.gson.annotations.SerializedName;

import data.Message;

public class SendMessageResponse {
    int status;

    @SerializedName("responseObject")
    Message message;

    public SendMessageResponse(int status, Message message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SendMessageResponse{" +
                "status=" + status +
                ", message=" + message +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }
}
