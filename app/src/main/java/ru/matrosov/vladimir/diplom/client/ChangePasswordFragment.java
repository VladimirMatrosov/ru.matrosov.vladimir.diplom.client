package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import ru.matrosov.vladimir.diplom.client.retrofit.ChangePasswordResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;


public class ChangePasswordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        Button savePass = view.findViewById(R.id.change_button);
        savePass.setOnClickListener(this::savePassword);
        return view;
    }

    public void savePassword(View view) {
        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(LoginActivity.EMAIL);

        RelativeLayout relativeLayout = getView().findViewById(R.id.change_pass_fragment);

        EditText editTextPass = relativeLayout.findViewById(R.id.password_input_change);
        String password = editTextPass.getText().toString();

        EditText editTextPass1 = relativeLayout.findViewById(R.id.password1_input_change);
        String password1 = editTextPass1.getText().toString();

        EditText editTextPass2 = relativeLayout.findViewById(R.id.password2_input_change);
        String password2 = editTextPass2.getText().toString();

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.changingPassword(email, password, password1, password2, this::onChangePassResponse);
    }

    void onChangePassResponse(ChangePasswordResponse response) {
        if (response.getStatus() == 0) {
            Toast.makeText(this.getContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            Intent intent = getActivity().getIntent();
            intent.putExtra(LoginActivity.PASSWORD, response.getUser().getPassword());
        } else if(response.getStatus() == -1){
            Toast.makeText(this.getContext(), "Введен неправильный текущий пароль", Toast.LENGTH_LONG).show();
        } else if(response.getStatus() == -2){
            Toast.makeText(this.getContext(), "Введенные пароль не совпадают", Toast.LENGTH_LONG).show();
        } else if(response.getStatus() == -5){
            Toast.makeText(this.getContext(), "Пароль не может быть пустым", Toast.LENGTH_LONG).show();
        }
    }
}