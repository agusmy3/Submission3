package com.agus.submission3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    AlarmReceiver alarmReceiver;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setTitle(R.string.menu_settings);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new MyPreferenceFragment()).commit();
        alarmReceiver = new AlarmReceiver();
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (key.equals("reminder")) {
            setAlarm(sp.getBoolean("reminder",true));
        }else if(key.equals("lang")){
            String lang = sp.getString("lang","");
            setLocale1(lang);
        }
    }

    private void setAlarm(boolean reminder){
        if(reminder){
            String repeatTime = "09:00";
            String repeatTitle = getResources().getString(R.string.search_activity);
            String repeatMessage = getResources().getString(R.string.message_remind);
            alarmReceiver.setRepeatingAlarm(this, repeatTime, repeatTitle, repeatMessage);
        }
    }

    public void setLocale1(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        recreate();
//        Intent refresh = new Intent(this, SettingActivity.class);
//        finish();
//        startActivity(refresh);
    }

    private void setLocale(String lang){
//        LocaleHelper.setLocale(this,lang);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang",lang);
        editor.apply();
    }

    private void getLocale(){
        SharedPreferences sh = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sh.getString("My_lang", "");
        setLocale(language);
    }

}