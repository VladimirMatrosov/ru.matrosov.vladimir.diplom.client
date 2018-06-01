package ru.matrosov.vladimir.diplom.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import services.IntentShowChat;
import services.MyIntentServiceShowChat;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.FIRST_NAME;
import static constants.IntentParameters.LAST_NAME;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    IntentShowChat intentShowChat = new IntentShowChat();

    MyIntentServiceShowChat serviceShowChat;
    boolean mBound = false;
    ServiceConnection mConnection;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intObj = getIntent();
        String emailOutput = intObj.getStringExtra(EMAIL);
        String firstNameOutput = intObj.getStringExtra(FIRST_NAME);
        String lastNameOutput = intObj.getStringExtra(LAST_NAME);

        FragmentManager fragmentManager = getSupportFragmentManager();
        new FragmentSupports().addFragments(fragmentManager, R.id.frame_main, new HomeFragment());

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imageView = headerLayout.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FragmentSupports().replaceFragments(fragmentManager, "home", R.id.frame_main, new HomeFragment());
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        TextView textViewEmailOutput = headerLayout.findViewById(R.id.textViewEmailOutput);
        textViewEmailOutput.setText(emailOutput);

        TextView textViewNameUser = headerLayout.findViewById(R.id.textViewNameUser);
        textViewNameUser.setText(firstNameOutput + " " + lastNameOutput);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyIntentServiceShowChat.ShowChatBinder binder = (MyIntentServiceShowChat.ShowChatBinder) service;
                serviceShowChat = binder.getService();
                binder.setFragmentManager(getSupportFragmentManager());
                binder.setContext(context);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
            }
        };
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            serviceStoping();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_users) {
            serviceStoping();
            new FragmentSupports().replaceFragments(fragmentManager, "main to users",
                    R.id.frame_main, new UsersFragment());
        } else if (id == R.id.nav_chatrooms) {
            serviceStoping();
            new FragmentSupports().replaceFragments(fragmentManager, "main to chatrooms",
                    R.id.frame_main, new ChatroomsFragment());
        } else if (id == R.id.nav_settings) {
            serviceStoping();
            new FragmentSupports().replaceFragments(fragmentManager, "main to settings",
                    R.id.frame_main, new SettingsFragment());
        } else if (id == R.id.nav_exit) {
            serviceStoping();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void mainHome(FragmentManager fragmentManager, String string) {
        new FragmentSupports().replaceFragments(fragmentManager, string, R.id.frame_main, new HomeFragment());
    }

    public void serviceStoping(){
        if (mBound == true) {
            unbindService(mConnection);
            stopService(intentShowChat.getIntent());
            mBound = false;
        }
    }

}
