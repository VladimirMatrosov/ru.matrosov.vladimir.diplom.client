package ru.matrosov.vladimir.diplom.client.retrofit;

public class ServerResponse<T> {
    int status;
    T responseObj;

    public ServerResponse(int status, T responseObj) {
        this.status = status;
        this.responseObj = responseObj;
    }

    public int getStatus() {
        return status;
    }

    public T getResponseObj() {
        return responseObj;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + status +
                ", responseObj=" + responseObj +
                '}';
    }
}
