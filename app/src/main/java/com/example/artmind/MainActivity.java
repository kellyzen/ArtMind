package com.example.artmind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    TimerFragment timerFragment;
    FindHelpFragment findHelpFragment;
    ScanFragment scanFragment;
    HistoryFragment historyFragment;
    UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerFragment = new TimerFragment();
        findHelpFragment = new FindHelpFragment();
        scanFragment = new ScanFragment();
        historyFragment = new HistoryFragment();
        userFragment = new UserFragment();

        //Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_about) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, timerFragment).commit();
                }
                if (item.getItemId() == R.id.menu_help) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, findHelpFragment).commit();
                }
                if (item.getItemId() == R.id.menu_scan) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, scanFragment).commit();
                }
                if (item.getItemId() == R.id.menu_history) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, historyFragment).commit();
                }
                if (item.getItemId() == R.id.menu_profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, userFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_scan);
    }
}