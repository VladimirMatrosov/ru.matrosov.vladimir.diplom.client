package ru.matrosov.vladimir.diplom.client.retrofit;

/**
 * Created by Valeriy on 17.03.2018.
 */

public interface ResponseSuccessCallback<T> {
    void onResponse(T responseObject);
}
