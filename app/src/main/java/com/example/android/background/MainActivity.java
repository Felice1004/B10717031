
package com.example.android.background;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.background.sync.ReminderUtilities;
import com.example.android.background.utilities.NotificationUtils;
import com.example.android.background.utilities.PreferenceUtilities;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mWaterCountDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaterCountDisplay = (TextView) findViewById(R.id.tv_displayed_internet_content);

        updateDisplayedInternetContent();
       // updateChargingReminderCount();
        // COMPLETED (23) Schedule the charging reminder
        ReminderUtilities.scheduleChargingReminder(this);

        /** Setup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Updates the TextView to display the new water count from SharedPreferences
     */
    private void updateDisplayedInternetContent() {
        String InternetContent = PreferenceUtilities.getInternetContent(this);
        mWaterCountDisplay.setText(InternetContent);
    }




    // COMPLETED (24) Remove the button and testNotification code

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** Cleanup the shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * This is a listener that will update the UI when the water count or charging reminder counts
     * change
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_INTERNET_CONTENT.equals(key)) {
            updateDisplayedInternetContent();
        }
    }

    public void testNotification(View view) {
        NotificationUtils.remindUserBecauseUpdatingEvery1Minute(this);
    }
}

