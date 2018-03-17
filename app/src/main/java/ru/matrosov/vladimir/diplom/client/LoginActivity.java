package ru.matrosov.vladimir.diplom.client;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import data.User;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void actionSignIn(View view) {

        ActionAutorization actionAutorization = new ActionAutorization();
        String res = actionAutorization.doInBackground("192.168.43.240:8080/autorization");
        Gson gson = new Gson();
        gson.toJson(res);

        AutorizationResponse autorizationResponse = gson.fromJson(res, AutorizationResponse.class);
        String response = "";
        if (autorizationResponse.getStatus() == 0) {
            Intent intObj = new Intent(this, MainActivity.class);

            intObj.putExtra("User", autorizationResponse.getUser().toString());

            EditText editTextEmail = findViewById(R.id.email_sign_in);
            String emailInput = editTextEmail.getText().toString();
            intObj.putExtra(EMAIL, emailInput);

            EditText editTextPassword = findViewById(R.id.password_sign_in);
            String passwordInput = editTextPassword.getText().toString();
            intObj.putExtra(PASSWORD, passwordInput);

            startActivity(intObj);
        } else if (autorizationResponse.getStatus() == -2){

        }
        else if (autorizationResponse.getStatus() == -1)
            response = "@string/error_incorrect_email";
    }

    public void actionRegistration(View view) {
        Intent intObj = new Intent(this, MainActivity.class);
        startActivity(intObj);
    }

    public void actionRegistrationForm(View view) {
        setContentView(R.layout.registration_login);
    }

    public class ActionAutorization extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... path) {
            String content;
            EditText editTextEmail = findViewById(R.id.email_sign_in);
            EditText editTextPassword = findViewById(R.id.password_sign_in);

            try {
                content = getContent(path[0], editTextEmail.getText().toString(), editTextPassword.getText().toString());
            } catch (IOException ex) {
                content = ex.getMessage();
            }

            return content;

        }

        private String getContent(String s, String email, String password) throws IOException {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("email", email);
            connection.setRequestProperty("password", password);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = reader.toString();
            return str;
        }

    }

    public class AutorizationResponse {
        int status;
        User user;

        public AutorizationResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}

