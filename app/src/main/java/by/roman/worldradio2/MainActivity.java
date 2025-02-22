package by.roman.worldradio2;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.dataclasses.RadioStations;
import by.roman.worldradio2.fragments.FindCountryFragment;
import by.roman.worldradio2.fragments.HomeFragment;
import by.roman.worldradio2.fragments.SaveFragment;
import by.roman.worldradio2.fragments.SettingsFragment;
import by.roman.worldradio2.fragments.TopFragment;

public class MainActivity extends AppCompatActivity {

    private int frame = 2;
    private ImageView button_country;
    private ImageView button_settings;
    private ImageView button_save;
    private ImageView button_top;
    private ImageView button_home;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initObjects();
        button_country.setOnClickListener(v -> FragmentChange(new FindCountryFragment(), 0));
        button_save.setOnClickListener(v -> FragmentChange(new SaveFragment(), 1));
        button_home.setOnClickListener(v -> FragmentChange(new HomeFragment(), 2));
        button_top.setOnClickListener(v -> FragmentChange(new TopFragment(), 3));
        button_settings.setOnClickListener(v -> FragmentChange(new SettingsFragment(), 4));

    }

    private void resetIcons(){
        button_country.setImageDrawable(getDrawable(R.drawable.findcountry_navigationbar));
        button_save.setImageDrawable(getDrawable(R.drawable.save_navigationbar));
        button_home.setImageDrawable(getDrawable(R.drawable.home_navigationbar));
        button_top.setImageDrawable(getDrawable(R.drawable.top_navigationbar));
        button_settings.setImageDrawable(getDrawable(R.drawable.settings_navigationbar));
    }

    private void initObjects(){
        button_country = findViewById(R.id.findCountryButton);
        button_settings = findViewById(R.id.settingsButton);
        button_save = findViewById(R.id.saveButton);
        button_top = findViewById(R.id.topButton);
        button_home = findViewById(R.id.homeButton);
    }
    private void FragmentChange(Fragment fragment, int fragmentId) {
        if (frame != fragmentId) {
            change(fragment);
            resetIcons();
            frame = fragmentId;
            setSelectedButtonIcon(fragmentId);
        }
    }
    private void setSelectedButtonIcon(int fragmentId) {
        switch (fragmentId) {
            case 0: button_country.setImageDrawable(getDrawable(R.drawable.selectedfindcountry_navigationbar)); break;
            case 1: button_save.setImageDrawable(getDrawable(R.drawable.selectedsave_navigationbar)); break;
            case 2: button_home.setImageDrawable(getDrawable(R.drawable.selectedhome_navigationbar)); break;
            case 3: button_top.setImageDrawable(getDrawable(R.drawable.selectedtop_navigationbar)); break;
            case 4: button_settings.setImageDrawable(getDrawable(R.drawable.selectedsettings_navigationbar)); break;
        }
    }
    public void change(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_fade_in,R.anim.fragment_fade_out);
        ft.replace(R.id.fragmentMenuView,f);
        ft.commit();
    }
}