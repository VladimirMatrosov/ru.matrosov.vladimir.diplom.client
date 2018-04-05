package ru.matrosov.vladimir.diplom.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import ru.matrosov.vladimir.diplom.client.retrofit.AutorizationResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.FIRST_NAME;
import static constants.IntentParameters.LAST_NAME;
import static constants.IntentParameters.PASSWORD;
import static constants.IntentParameters.POST;
import static constants.IntentParameters.USER_ID;
import static constants.IpAdress.IP_ADRESS;


public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button autorizeBut = view.findViewById(R.id.email_sign_in_button);
        autorizeBut.setOnClickListener(this::actionSignIn);

        Button regBut = view.findViewById(R.id.email_registration_button);
        regBut.setOnClickListener(this::actionRegistrationForm);
        return view;
    }

    public void actionSignIn(View view) {

        LinearLayout linearLayout = getView().findViewById(R.id.email_login_form);

        EditText editTextEmail = linearLayout.findViewById(R.id.email_sign_in);
        EditText editTextPassword = linearLayout.findViewById(R.id.password_sign_in);

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, this.getContext());
        serverConnection.autorize(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                this::onAutorizationResponse);
    }

    void onAutorizationResponse(AutorizationResponse response) {
        if (response.getStatus() == 0) {
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            intent.putExtra(EMAIL, response.getUser().getEmail());
            intent.putExtra(PASSWORD, response.getUser().getPassword());
            intent.putExtra(FIRST_NAME, response.getUser().getFirstName());
            intent.putExtra(LAST_NAME, response.getUser().getLastName());
            intent.putExtra(POST, response.getUser().getPost());
            intent.putExtra(USER_ID, response.getUser().getUserID());

            startActivity(intent);
        } else if (response.getStatus() == -2) {
            Toast.makeText(this.getContext(), "Набран неправильный пароль",
                    Toast.LENGTH_LONG).show();
        } else if (response.getStatus() == -1) {
            Toast.makeText(this.getContext(), "Пользователя с таким email нет",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void actionRegistrationForm(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        fragmentTransaction.replace(R.id.login_form,registrationFragment);
        fragmentTransaction.commit();
    }

}
