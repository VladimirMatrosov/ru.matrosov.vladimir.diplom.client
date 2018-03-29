package ru.matrosov.vladimir.diplom.client.retrofit;


public interface ResponseSuccessCallback<T> {
    void onResponse(T responseObject);
}
