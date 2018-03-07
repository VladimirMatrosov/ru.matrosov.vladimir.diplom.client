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

import java.util.ArrayList;
import java.util.List;

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
        Intent intObj = new Intent(this, MainActivity.class);

        EditText editTextEmail = findViewById(R.id.email_sign_in);
        String emailInput = editTextEmail.getText().toString();
        intObj.putExtra(EMAIL, emailInput);

        EditText editTextPassword = findViewById(R.id.password_sign_in);
        String passwordInput = editTextPassword.getText().toString();
        intObj.putExtra(PASSWORD, passwordInput);

        startActivity(intObj);
    }

    public void actionRegistration(View view) {
        Intent intObj = new Intent(this, MainActivity.class);
        startActivity(intObj);
    }

    public void actionRegistrationForm(View view) {
        setContentView(R.layout.registration_login);
    }
}

