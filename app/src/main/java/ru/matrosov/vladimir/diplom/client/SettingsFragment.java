package ru.matrosov.vladimir.diplom.client;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.SettingResponse;


public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(LoginActivity.EMAIL);
        String firstName = intent.getStringExtra(LoginActivity.FIRST_NAME);
        String lastName = intent.getStringExtra(LoginActivity.LAST_NAME);
        String post = intent.getStringExtra(LoginActivity.POST);

        EditText editTextEmail = view.findViewById(R.id.email_input_set);
        editTextEmail.setText(email);

        EditText editTextFirstName = view.findViewById(R.id.firstName_input_set);
        editTextFirstName.setText(firstName);

        EditText editTextLastName = view.findViewById(R.id.lastName_input_set);
        editTextLastName.setText(lastName);

        EditText editTextPost = view.findViewById(R.id.post_input_set);
        editTextPost.setText(post);

        Button saveButton = view.findViewById(R.id.save_settings_button);
        saveButton.setOnClickListener(this::saveSettings);

        Button passButton = view.findViewById(R.id.change_password_button);
        passButton.setOnClickListener(this::changePassword);
        return view;
    }

    public void changePassword(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        fragmentTransaction.replace(R.id.frame_main, changePasswordFragment);
        fragmentTransaction.commit();
    }

    public void saveSettings(View view) {
        Intent intent = this.getActivity().getIntent();
        String email = intent.getStringExtra(LoginActivity.EMAIL);

        RelativeLayout relativeLayout = getView().findViewById(R.id.settings_fragment);
        EditText editTextEmail = relativeLayout.findViewById(R.id.email_input_set);
        String newEmail = editTextEmail.getText().toString();

        EditText editTextFirstName = relativeLayout.findViewById(R.id.firstName_input_set);
        String firstName = editTextFirstName.getText().toString();

        EditText editTextLastName = relativeLayout.findViewById(R.id.lastName_input_set);
        String lastName = editTextLastName.getText().toString();

        EditText editTextPost = relativeLayout.findViewById(R.id.post_input_set);
        String post = editTextPost.getText().toString();

        ServerConnection serverConnection = new ServerConnection(LoginActivity.server, this.getContext());
        serverConnection.setting(email, firstName, lastName, newEmail, post, this::onSettingResponse);
    }

    void onSettingResponse(SettingResponse response) {
        if (response.getStatus() == 0){
            Toast.makeText(this.getContext(), "Response: " + response.toString(), Toast.LENGTH_LONG).show();
            Intent intent = getActivity().getIntent();
            intent.putExtra(LoginActivity.EMAIL, response.getUser().getEmail());
            intent.putExtra(LoginActivity.FIRST_NAME, response.getUser().getFirstName());
            intent.putExtra(LoginActivity.LAST_NAME, response.getUser().getLastName());
            intent.putExtra(LoginActivity.POST, response.getUser().getPost());

        } else if (response.getStatus() == -1){
            Toast.makeText(this.getContext(), "Проверьте введенные данные", Toast.LENGTH_LONG).show();
        }
    }
}
