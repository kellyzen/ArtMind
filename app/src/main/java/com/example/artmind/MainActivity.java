package com.example.artmind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.artmind.model.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TimerFragment timerFragment;
    FindHelpFragment findHelpFragment;
    ScanFragment scanFragment;
    HistoryFragment historyFragment;
    SettingFragment settingFragment;
    SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerFragment = new TimerFragment();
        findHelpFragment = new FindHelpFragment();
        scanFragment = new ScanFragment();
        historyFragment = new HistoryFragment();
        settingFragment = new SettingFragment();
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        //Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_timer) {
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
            if (item.getItemId() == R.id.menu_setting) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, settingFragment).commit();
            }
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_scan);
    }
}