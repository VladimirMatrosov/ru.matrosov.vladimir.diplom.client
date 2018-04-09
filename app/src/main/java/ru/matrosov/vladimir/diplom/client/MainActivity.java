package ru.matrosov.vladimir.diplom.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static constants.IntentParameters.EMAIL;
import static constants.IntentParameters.FIRST_NAME;
import static constants.IntentParameters.LAST_NAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intObj = getIntent();
        String emailOutput = intObj.getStringExtra(EMAIL);
        String firstNameOutput = intObj.getStringExtra(FIRST_NAME);
        String lastNameOutput = intObj.getStringExtra(LAST_NAME);

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView textViewEmailOutput = headerLayout.findViewById(R.id.textViewEmailOutput);
        textViewEmailOutput.setText(emailOutput);

        TextView textViewNameUser = headerLayout.findViewById(R.id.textViewNameUser);
        textViewNameUser.setText(firstNameOutput + " " + lastNameOutput);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.frame_main, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_users) {
           new FragmentSupports().replaceFragments(fragmentManager,"main to users", R.id.frame_main,
                   new UsersFragment());
        } else if (id == R.id.nav_chatrooms) {
            new FragmentSupports().replaceFragments(fragmentManager, "main to chatrooms", R.id.frame_main,
                    new ChatroomsFragment());
        } else if (id == R.id.nav_settings) {
           new FragmentSupports().replaceFragments(fragmentManager, "main to settings", R.id.frame_main,
                   new SettingsFragment());
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
