package by.roman.worldradio2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

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

        button_country.setOnClickListener(v ->{
            change(new FindCountryFragment());
            resetIcons();
            button_country.setImageDrawable(getDrawable(R.drawable.selectedfindcountry_navigationbar));
        });
        button_save.setOnClickListener(v ->{
            change(new SaveFragment());
            resetIcons();
            button_save.setImageDrawable(getDrawable(R.drawable.selectedsave_navigationbar));
        });
        button_home.setOnClickListener(v ->{
            change(new HomeFragment());
            resetIcons();
            button_home.setImageDrawable(getDrawable(R.drawable.selectedhome_navigationbar));
        });
        button_top.setOnClickListener(v ->{
            change(new TopFragment());
            resetIcons();
            button_top.setImageDrawable(getDrawable(R.drawable.selectedtop_navigationbar));
        });
        button_settings.setOnClickListener(v ->{
            change(new SettingsFragment());
            resetIcons();
            button_settings.setImageDrawable(getDrawable(R.drawable.selectedsettings_navigationbar));
        });

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

    public void change(Fragment f){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentMenuView,f);
        ft.commit();
    }

}