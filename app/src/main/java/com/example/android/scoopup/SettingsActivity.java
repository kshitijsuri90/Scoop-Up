package com.example.android.scoopup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String state = getIntent().getStringExtra("state");
        if(state.equals("false")){
            setTheme(R.style.AppTheme);
        }
        else {
            setTheme(R.style.NightMode);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        View fragment = findViewById(R.id.fragment);
        if(state.equals("false")){
            fragment.getRootView().setBackgroundColor(getResources().getColor(R.color.cardbackgroundDay));
        }
        else {
            fragment.getRootView().setBackgroundColor(getResources().getColor(R.color.cardbackgroundNight));
        }
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference category = findPreference(getString(R.string.settings_category_key));
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
            bindPreferenceSummaryToValue(category);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);

        }
    }
}