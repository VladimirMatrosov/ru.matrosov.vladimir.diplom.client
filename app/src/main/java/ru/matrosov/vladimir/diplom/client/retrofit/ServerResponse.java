package ru.matrosov.vladimir.diplom.client.retrofit;

public class ServerResponse<T> {
    int status;
    T responseObj;
    Class aClass;

    public ServerResponse(int status, T responseObj, Class aClass) {
        this.status = status;
        this.responseObj = responseObj;
        this.aClass = aClass;
    }

    public Class getaClass() {
        return aClass;
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
