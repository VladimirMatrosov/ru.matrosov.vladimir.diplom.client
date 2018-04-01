package ru.matrosov.vladimir.diplom.client.retrofit;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerConnection {
    private Retrofit retrofit;
    private Context context;

    public ServerConnection(String serverUrl, Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void autorize(String email, String password, ResponseSuccessCallback<AutorizationResponse> callback) {
        Autorization service = retrofit.create(Autorization.class);
        service.autorize(email, password).enqueue(new Callback<AutorizationResponse>() {
            @Override
            public void onResponse(Call<AutorizationResponse> call, Response<AutorizationResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AutorizationResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void register(String firstname, String lastname, String email, String post,
                         String password1, String password2, ResponseSuccessCallback<ServerResponse> callback) {
        Registration service = retrofit.create(Registration.class);
        service.register(firstname, lastname, email, post, password1, password2).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showingUsers(ResponseSuccessCallback<ServerResponse> callback) {
        ShowUsers service = retrofit.create(ShowUsers.class);
        service.showingUsers().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }
}
