package com.example.ewgengabruskiy.geekinst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ThemeChoiceActivity extends AppCompatActivity implements View.OnClickListener{


    private Button brightButton;
    private Button darkButton;
    private Button limeButton;
    private SharedPreferences sharedPreferences;
    private int theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AppTheme", MODE_PRIVATE);
        theme = sharedPreferences.getInt("theme",R.style.BrightTheme);
        setTheme(theme);
        setContentView(R.layout.activity_theme_choise);

        init();
    }




    @Override
    public void onClick(View view) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (view.getId()){

            case (R.id.bright_button):
                editor.putInt("theme", R.style.BrightTheme);
                editor.apply();
                recreate();
                break;

            case (R.id.dark_button):
                editor.putInt("theme", R.style.DarkTheme);
                editor.apply();
                recreate();
                break;

            case (R.id.lime_button):
                editor.putInt("theme", R.style.LimeTheme);
                editor.apply();
                recreate();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void init(){
        Toolbar toolbar =  findViewById(R.id.toolbar_theme_choice);
        setSupportActionBar(toolbar);

        limeButton = findViewById(R.id.lime_button);
        brightButton = findViewById(R.id.bright_button);
        darkButton = findViewById(R.id.dark_button);
        brightButton.setOnClickListener(this);
        darkButton.setOnClickListener(this);
        limeButton.setOnClickListener(this);
    }
}
