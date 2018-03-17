package ru.matrosov.vladimir.diplom.client.retrofit;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.matrosov.vladimir.diplom.client.LoginActivity;

/**
 * Created by Valeriy on 17.03.2018.
 */

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
                Toast.makeText(context, "Error on getting", Toast.LENGTH_LONG).show();

            }
        });
    }


}
