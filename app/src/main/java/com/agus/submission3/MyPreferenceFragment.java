package com.agus.submission3;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String REMIND, LANG ;
    private SwitchPreference isRemind;
    private ListPreference isLang;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }

    private void init() {
        REMIND = getResources().getString(R.string.key_remind);
        LANG = getResources().getString(R.string.key_lang);
        isRemind = (SwitchPreference) findPreference(REMIND);
        isLang = (ListPreference) findPreference(LANG);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(REMIND)) {
            isRemind.setChecked(sharedPreferences.getBoolean(REMIND, false));
        }else if(key.equals(LANG)){
            String value = sharedPreferences.getString(LANG, "in");
            int prefIndex = isLang.findIndexOfValue(value);
            if (prefIndex >= 0) {
                isLang.setSummary(isLang.getEntries()[prefIndex]);
            }
        }

    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        isRemind.setChecked(sh.getBoolean(REMIND, false));
        int prefIndex = isLang.findIndexOfValue(sh.getString(LANG,"in"));
        if (prefIndex >= 0) {
            isLang.setSummary(isLang.getEntries()[prefIndex]);
        }

    }

}