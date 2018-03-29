package ru.matrosov.vladimir.diplom.client;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletRequest;

import data.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.matrosov.vladimir.diplom.client.retrofit.AutorizationResponse;
import ru.matrosov.vladimir.diplom.client.retrofit.ResponseSuccessCallback;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerConnection;
import ru.matrosov.vladimir.diplom.client.retrofit.ServerResponse;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public final static String TAG = "LoginActivity";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void actionSignIn(View view) {

        EditText editTextEmail = findViewById(R.id.email_sign_in);
        EditText editTextPassword = findViewById(R.id.password_sign_in);

        ServerConnection serverConnection = new ServerConnection("http://192.168.0.102:8080", this);
        serverConnection.autorize(
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                this::onResponse);


        //  AutorizationResponse autorizationResponse = gson.fromJson(res, AutorizationResponse.class);
//        String response = "";
//        if (autorizationResponse.getStatus() == 0) {
//            Intent intObj = new Intent(this, MainActivity.class);
//
//            intObj.putExtra("User", autorizationResponse.getUser().toString());
//
//            EditText editTextEmail = findViewById(R.id.email_sign_in);
//            String emailInput = editTextEmail.getText().toString();
//            intObj.putExtra(EMAIL, emailInput);
//
//            EditText editTextPassword = findViewById(R.id.password_sign_in);
//            String passwordInput = editTextPassword.getText().toString();
//            intObj.putExtra(PASSWORD, passwordInput);
//
//            startActivity(intObj);
//        } else if (autorizationResponse.getStatus() == -2){
//
//        }
//        else if (autorizationResponse.getStatus() == -1)
//            response = "@string/error_incorrect_email";
    }

    void onResponse(AutorizationResponse response) {
        Toast.makeText(this, "User " + response.getUser() + " logged in!!" + "Status = " + response.getStatus(),
                Toast.LENGTH_LONG).show();
    }

    public void actionRegistration(View view) {
        EditText editTextEmail = findViewById(R.id.email_input_registration);
        EditText editTextFirstName = findViewById(R.id.firstName_input_registration);
        EditText editTextLastName = findViewById(R.id.lastName_input_registration);
        EditText editTextPost = findViewById(R.id.post_input_registration);
        EditText editTextPassword1 = findViewById(R.id.password1_input_registration);
        EditText editTextPassword2 = findViewById(R.id.password2_input_registration);

        ServerConnection serverConnection = new ServerConnection("http://192.168.0.102:8080", this);
        serverConnection.register(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                editTextEmail.getText().toString(), editTextPost.getText().toString(),
                editTextPassword1.getText().toString(), editTextPassword2.getText().toString(),
                this::onResponse);
        Intent intObj = new Intent(this, MainActivity.class);
        startActivity(intObj);

    }

    void onResponse(ServerResponse response) {
        Toast.makeText(this, "Response: " + response.toString(), Toast.LENGTH_LONG).show();
    }

    public void actionRegistrationForm(View view) {
        setContentView(R.layout.registration_login);
    }

}

