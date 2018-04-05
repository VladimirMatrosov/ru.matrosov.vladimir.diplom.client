package ru.matrosov.vladimir.diplom.client.retrofit;

public class DeleteUserResponse {
    int status;

    public DeleteUserResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
