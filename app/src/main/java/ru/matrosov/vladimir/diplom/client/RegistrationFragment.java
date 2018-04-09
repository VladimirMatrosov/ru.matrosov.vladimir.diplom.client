package ru.matrosov.vladimir.diplom.client;

import android.content.Intent;
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

import ru.matrosov.vladimir.diplom.client.retrofit.RegistrationResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.FIRST_NAME;
import static constants.IntentParameters.LAST_NAME;
import static constants.IntentParameters.PASSWORD;
import static constants.IntentParameters.POST;
import static constants.IntentParameters.USER_ID;
import static constants.IpAdress.IP_ADRESS;


public class RegistrationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        Button registerBut = view.findViewById(R.id.registration_button);
        registerBut.setOnClickListener(this::actionRegistration);
        return view;
    }

    public void actionRegistration(View view) {
        RelativeLayout relativeLayout = getView().findViewById(R.id.registration_form);

        EditText editTextEmail = relativeLayout.findViewById(R.id.email_input_registration);
        EditText editTextFirstName = relativeLayout.findViewById(R.id.firstName_input_registration);
        EditText editTextLastName = relativeLayout.findViewById(R.id.lastName_input_registration);
        EditText editTextPost = relativeLayout.findViewById(R.id.post_input_registration);
        EditText editTextPassword1 = relativeLayout.findViewById(R.id.password1_input_registration);
        EditText editTextPassword2 = relativeLayout.findViewById(R.id.password2_input_registration);

        ServerConnection serverConnection = new ServerConnection(IP_ADRESS, this.getContext());
        serverConnection.register(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                editTextEmail.getText().toString(), editTextPost.getText().toString(),
                editTextPassword1.getText().toString(), editTextPassword2.getText().toString(),
                this::onRegistrationResponse);

        setEnabledRegButton(relativeLayout,false);
    }

    void onRegistrationResponse(RegistrationResponse response) {
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
            Toast.makeText(this.getContext(), R.string.not_match_pass,
                    Toast.LENGTH_LONG).show();
            setEnabledRegButton(this.getView(),true);
        } else if (response.getStatus() == -1) {
            Toast.makeText(this.getContext(), R.string.system_has_user_with_email,
                    Toast.LENGTH_LONG).show();
            setEnabledRegButton(this.getView(),true);
        } else if (response.getStatus() == -5){
            Toast.makeText(this.getContext(), R.string.null_error,
                    Toast.LENGTH_LONG).show();
            setEnabledRegButton(this.getView(),true);
        }
    }

    void setEnabledRegButton(View view, Boolean bool){
        Button regButton = view.findViewById(R.id.registration_button);
        regButton.setEnabled(bool);
    }
}
