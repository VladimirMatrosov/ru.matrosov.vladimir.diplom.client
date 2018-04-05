package ru.matrosov.vladimir.diplom.client;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public final static String TAG = "LoginActivity";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String POST = "Post";
    public static final String USER_ID = "UserID";
    public static final String server = "http://192.168.0.102:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_form, loginFragment);
        fragmentTransaction.commit();
        setContentView(R.layout.activity_login);
    }

}

