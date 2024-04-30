/*  Starter project for Mobile Platform Development in main diet 2023/2024
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Josephine Nation Ekhator
// Student ID           s211100
// Programme of Study   Computing
//
package com.example.ekhator_josephine_nation_s211100;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    TheHomeFragment theHomeFragment = new TheHomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    MapFragment mapFragment = new MapFragment();

    MoreFragment moreFragment = new MoreFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.buttom_navigation);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("JNWeather(s211100)");

       // Set the default selected item in the BottomNavigationView to the home item
        getSupportFragmentManager().beginTransaction().replace(R.id.container,theHomeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                String title = "";

                if (item.getItemId() == R.id.home) {
                    selectedFragment = theHomeFragment;
                    title = "Home(s211100)";
                } else if (item.getItemId() == R.id.search) {
                    selectedFragment = searchFragment;
                    title = "Search(s211100)";
                } else if (item.getItemId() == R.id.map) {
                    selectedFragment = mapFragment;
                    title = "Map(s211100)";
                } else if (item.getItemId() == R.id.more) {
                    selectedFragment = moreFragment;
                    title = "More(s211100)";
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                getSupportActionBar().setTitle(title);
                return true;
            }
        });

        // Handle search icon click separately
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                    getSupportActionBar().setTitle("Search(s211100)");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setVisible(true); // Ensure the search icon is always visible
        return true;
    }

}