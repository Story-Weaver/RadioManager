package by.roman.worldradio2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import by.roman.worldradio2.dataclasses.Database;
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
        Database database = new Database(getApplicationContext());
        database.setSort(1,0);
        database.setFilter(1,"country",null);
        database.setFilter(1,"style",null);
        database.setFilter(1,"lang",null);
        //database.addRadioStation("-","mexico","","https://sonic01.instainternet.com/8374/stream",null,0,0,"x3",25,false);
        //database.addRadioStation("Radio Country Live New York","usa","https://cdn-radiotime-logos.tunein.com/s127108d.png","https://streaming.radiostreamlive.com/radiocountrylive_devices",null,0,0,"x3",10,false);
        button_country.setOnClickListener(v -> FragmentChange(new FindCountryFragment(), 0));
        button_save.setOnClickListener(v -> FragmentChange(new SaveFragment(), 1));
        button_home.setOnClickListener(v -> FragmentChange(new HomeFragment(), 2));
        button_top.setOnClickListener(v -> FragmentChange(new TopFragment(), 3));
        button_settings.setOnClickListener(v -> FragmentChange(new SettingsFragment(), 4));
    }

    private void resetIcons(){
        button_country.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.findcountry_navigationbar));
        button_save.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.save_navigationbar));
        button_home.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.home_navigationbar));
        button_top.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.top_navigationbar));
        button_settings.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.settings_navigationbar));
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
            case 0: button_country.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.selectedfindcountry_navigationbar)); break;
            case 1: button_save.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.selectedsave_navigationbar)); break;
            case 2: button_home.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.selectedhome_navigationbar)); break;
            case 3: button_top.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.selectedtop_navigationbar)); break;
            case 4: button_settings.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.selectedsettings_navigationbar)); break;
        }
    }
    public void change(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_fade_in,R.anim.fragment_fade_out);
        ft.replace(R.id.fragmentMenuView,f);
        ft.commit();
    }
}