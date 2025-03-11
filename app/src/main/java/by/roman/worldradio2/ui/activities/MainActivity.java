package by.roman.worldradio2.ui.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import by.roman.worldradio2.R;
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.fragments.BottomPlayerFragment;
import by.roman.worldradio2.ui.fragments.FindCountryFragment;
import by.roman.worldradio2.ui.fragments.HomeFragment;
import by.roman.worldradio2.ui.fragments.SaveFragment;
import by.roman.worldradio2.ui.fragments.SettingsFragment;
import by.roman.worldradio2.ui.fragments.TopFragment;

public class MainActivity extends AppCompatActivity implements BottomPlayerFragment.OnFavoriteChangedListener{

    private int frame = 2;
    private ImageView button_country;
    private ImageView button_settings;
    private ImageView button_save;
    private ImageView button_top;
    private ImageView button_home;

    private ImageView logo;
    private ImageView save_unsave;
    private ImageView play_pause;
    private TextView name;
    private FrameLayout bottomPlayerContainer;
    private RadioStationRepository radioStationRepository;
    private BottomPlayerFragment bottomPlayerFragment;
    private SaveFragment saveFragment;
    private boolean save_flag = false;
    private boolean play_flag = false;

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
        bottomPlayerFragment = new BottomPlayerFragment();
        initObjects();
        saveFragment = new SaveFragment();


        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        FavoriteRepository favoriteRepository = new FavoriteRepository(db);


        UserRepository userRepository = new UserRepository(db);


        radioStationRepository = new RadioStationRepository(db);
        radioStationRepository.removeIsPlaying();


        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(intent);


        RadioService radioService = RadioService.getInstance(getApplicationContext(),this);
        radioService.startMonitoring();


        button_country.setOnClickListener(v -> FragmentChange(new FindCountryFragment(), 0));
        button_save.setOnClickListener(v -> FragmentChange(new SaveFragment(), 1));
        button_home.setOnClickListener(v -> FragmentChange(new HomeFragment(), 2));
        button_top.setOnClickListener(v -> FragmentChange(new TopFragment(), 3));
        button_settings.setOnClickListener(v -> FragmentChange(new SettingsFragment(), 4));

        RadioStation station = radioStationRepository.getActiveStation();
        if (station != null && station.getIsPlaying() == 1) {
            showBottomPlayerFragment(station);
        } else {
            hideBottomPlayerFragment();
        }
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

        logo = findViewById(R.id.station_logo_player);
        name = findViewById(R.id.station_name_player);
        save_unsave = findViewById(R.id.save_unsave);
        play_pause = findViewById(R.id.play_pause);
        bottomPlayerContainer = findViewById(R.id.bottomPlayerContainer);
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
    @Override
    public void onFavoriteChanged() {
        reloadFragmentIfNeeded();
    }

    public void change(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_fade_in,R.anim.fragment_fade_out);
        ft.replace(R.id.fragmentMenuView,f);
        ft.commit();
    }
    public void changeSave(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMenuView,f);
        ft.commit();
    }
    public void reloadPlayerFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.bottomPlayerContainer);
        if (currentFragment != null && currentFragment instanceof BottomPlayerFragment) {
            showBottomPlayerFragmentReload(radioStationRepository.getActiveStation());
        }
    }
    public void reloadFragmentIfNeeded() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentMenuView);
        if (currentFragment != null && currentFragment instanceof SaveFragment) {
            changeSave(new SaveFragment());
        }
    }

    public void showBottomPlayerFragment(RadioStation station) {
        if (station == null) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BottomPlayerFragment bottomPlayerFragment = BottomPlayerFragment.newInstance(
                station.getName(),
                station.getFavicon(),
                station.getStationUuid()
        );
        transaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
        transaction.replace(R.id.bottomPlayerContainer, bottomPlayerFragment);
        transaction.commit();
        bottomPlayerContainer.setVisibility(VISIBLE);
    }
    public void showBottomPlayerFragmentReload(RadioStation station) {
        if (station == null) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BottomPlayerFragment bottomPlayerFragment = BottomPlayerFragment.newInstance(
                station.getName(),
                station.getFavicon(),
                station.getStationUuid()
        );
        transaction.replace(R.id.bottomPlayerContainer, bottomPlayerFragment);
        transaction.commit();
        bottomPlayerContainer.setVisibility(VISIBLE);
    }

    public void hideBottomPlayerFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.bottomPlayerContainer);
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
            transaction.remove(fragment);
            transaction.commit();
        }
        bottomPlayerContainer.setVisibility(GONE);
    }
}