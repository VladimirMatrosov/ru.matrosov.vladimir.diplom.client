package ru.matrosov.vladimir.diplom.client;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentSupports {
    public void replaceFragments(FragmentManager fragmentManager, String string, int view_id, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(view_id, fragment);
        fragmentTransaction.addToBackStack(string);
        fragmentTransaction.commit();
    }
}
