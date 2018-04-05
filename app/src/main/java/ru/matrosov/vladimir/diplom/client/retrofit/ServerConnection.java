package ru.matrosov.vladimir.diplom.client.retrofit;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import data.User;
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
                         String password1, String password2, ResponseSuccessCallback<RegistrationResponse> callback) {
        Registration service = retrofit.create(Registration.class);
        service.register(firstname, lastname, email, post, password1, password2).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showingUsers(ResponseSuccessCallback<ShowUsersResponse> callback) {
        ShowUsers service = retrofit.create(ShowUsers.class);
        service.showingUsers().enqueue(new Callback<ShowUsersResponse>() {
            @Override
            public void onResponse(Call<ShowUsersResponse> call, Response<ShowUsersResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ShowUsersResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setting(String email, String firstName, String lastName, String newEmail,
                        String post,ResponseSuccessCallback<SettingResponse> callback){
        Settings service = retrofit.create(Settings.class);
        service.setting(email, firstName, lastName, newEmail, post).enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(Call<SettingResponse> call, Response<SettingResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SettingResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void changingPassword(String email, String password, String password1, String password2,
                                 ResponseSuccessCallback<ChangePasswordResponse> callback){
        ChangePassword service = retrofit.create(ChangePassword.class);
        service.changingPassword(email, password, password1, password2).enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showingChatrooms(String email, ResponseSuccessCallback<ShowChatroomsResponse> callback){
        ShowChatrooms service = retrofit.create(ShowChatrooms.class);
        service.showingChatrooms(email).enqueue(new Callback<ShowChatroomsResponse>() {
            @Override
            public void onResponse(Call<ShowChatroomsResponse> call, Response<ShowChatroomsResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ShowChatroomsResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addingChat(String email, String chatName, ResponseSuccessCallback<AddChatResponse> callback){
        AddChat service = retrofit.create(AddChat.class);
        service.addingChat(email, chatName).enqueue(new Callback<AddChatResponse>() {
            @Override
            public void onResponse(Call<AddChatResponse> call, Response<AddChatResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddChatResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addingUserToChat(Integer idChat, String email, ResponseSuccessCallback<AddUserToChatResponse> callback){
        AddUserToChat service = retrofit.create(AddUserToChat.class);
        service.addingUserToChat(idChat, email).enqueue(new Callback<AddUserToChatResponse>() {
            @Override
            public void onResponse(Call<AddUserToChatResponse> call, Response<AddUserToChatResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<AddUserToChatResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showingChat(Integer idChat, String email, ResponseSuccessCallback<ShowChatResponse> callback){
        ShowChat service = retrofit.create(ShowChat.class);
        service.showingChat(idChat, email).enqueue(new Callback<ShowChatResponse>() {
            @Override
            public void onResponse(Call<ShowChatResponse> call, Response<ShowChatResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ShowChatResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sendingMessage(String email, String text, Integer idChat, ResponseSuccessCallback<SendMessageResponse> callback){
        SendMessage service = retrofit.create(SendMessage.class);
        service.sendingMessage(email, text, idChat).enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
                Toast.makeText(context, "Проверьте подключение к сети", Toast.LENGTH_LONG).show();
            }
        });
    }
}
