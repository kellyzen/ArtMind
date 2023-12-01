package com.example.artmind.component;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.artmind.R;
import com.example.artmind.component.find_help.FindHelpFragment;
import com.example.artmind.component.focus_timer.TimerFragment;
import com.example.artmind.component.history.HistoryFragment;
import com.example.artmind.component.scan.ScanFragment;
import com.example.artmind.component.settings.SettingFragment;
import com.example.artmind.model.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Main page (activity)
 *
 * @author Kelly Tan
 * @version 27 November 2023
 */
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TimerFragment timerFragment;
    FindHelpFragment findHelpFragment;
    ScanFragment scanFragment;
    HistoryFragment historyFragment;
    SettingFragment settingFragment;
    SharedViewModel sharedViewModel;

    /**
     * Create view for Main Activity
     */
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

        // Bottom Navigation View
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load respective fragment based on selected navigation
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Handle the configuration change, if needed.
        // You may want to update your fragment here.
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Do something when the orientation changes to landscape
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Do something when the orientation changes to portrait
        }
    }

}