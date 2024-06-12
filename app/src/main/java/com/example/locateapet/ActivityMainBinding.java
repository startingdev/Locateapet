package com.example.locateapet;

import android.view.LayoutInflater;
import android.view.View;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class ActivityMainBinding {
    public DrawerLayout drawerLayout;
    public NavigationView navView;

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        // Inflate the layout here
        return new ActivityMainBinding();
    }

    public View getRoot() {
        // Return the root view here
        return null;
    }
}
