package by.roman.worldradio2.ui.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FilterRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.data.repository.UserRepository;

public class    FilterActivity extends AppCompatActivity {
    private MaterialAutoCompleteTextView actvCountry;
    private MaterialAutoCompleteTextView actvTags;
    private MaterialAutoCompleteTextView actvLang;
    private RadioStationRepository radioStationRepository;
    private FilterRepository filterRepository;
    private UserRepository userRepository;
    private Spinner spinnerSortBy;
    private ImageView backButton;
    private ImageView deleteCountry;
    private ImageView deleteTags;
    private ImageView deleteLang;
    private ImageView confirmButton;
    private ImageView savedButton;
    private ImageView topButton;
    private ImageView recomendedButton;
    private TextView count;
    private int savedSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        findAllId();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        filterRepository = new FilterRepository(db);
        userRepository = new UserRepository(db);
        loadSavedFilters();
        updateCount();
        setupSortOptions();
        setupAutoComplete();
        setAutoCompleteTextViewFocusListener(actvLang);
        backButton.setOnClickListener(v ->{
            finish();
        });
        confirmButton.setOnTouchListener((v,event) ->{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", 0.5f);
                fadeOut.setDuration(100);
                fadeOut.start();
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", 1.0f);
                fadeIn.setDuration(100);
                fadeIn.start();
            }
            finish();
            return false;
        });
        deleteCountry.setOnClickListener(v->{
            filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_COUNTRY_FILTER,null);
            updateCount();
            actvCountry.setText("");
            deleteCountry.setVisibility(INVISIBLE);
        });
        deleteTags.setOnClickListener(v->{
            filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_TAGS_FILTER,null);
            updateCount();
            actvTags.setText("");
            deleteTags.setVisibility(INVISIBLE);
        });
        deleteLang.setOnClickListener(v->{
            filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_LANG_FILTER,null);
            updateCount();
            actvLang.setText("");
            deleteLang.setVisibility(INVISIBLE);
        });
        savedButton.setOnClickListener(v -> {

        });
        topButton.setOnClickListener(v -> {

        });
        recomendedButton.setOnClickListener(v -> {

        });
    }
    private void setupSortOptions() {
        List<String> sortOptions = new ArrayList<>();
        sortOptions.add("Никак");
        sortOptions.add("По имени");
        sortOptions.add("По популярности");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSortBy.setAdapter(adapter);
        spinnerSortBy.setSelection(savedSort);
        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterRepository.setSort(userRepository.getUserIdInSystem(),position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
    private void updateCount(){
        count.setText("Подходит " + radioStationRepository.getRadioStationCountWithFilter() + " радиостанций");
    }
    private void loadSavedFilters(){
        String savedCountry = filterRepository.getCountryFilter(userRepository.getUserIdInSystem());
        if (savedCountry != null && !savedCountry.isEmpty()) {
            actvCountry.setText(savedCountry);
            deleteCountry.setVisibility(VISIBLE);
        } else deleteCountry.setVisibility(INVISIBLE);
        String savedStyle = filterRepository.getTagsFilter(userRepository.getUserIdInSystem());
        if (savedStyle != null && !savedStyle.isEmpty()) {
            actvTags.setText(savedStyle);
            deleteTags.setVisibility(VISIBLE);
        } else deleteTags.setVisibility(INVISIBLE);
        String savedLang = filterRepository.getLangFilter(userRepository.getUserIdInSystem());
        if (savedLang != null && !savedLang.isEmpty()) {
            actvLang.setText(savedLang);
            deleteLang.setVisibility(VISIBLE);
        } else deleteLang.setVisibility(INVISIBLE);
        savedSort = filterRepository.getSortFilter(userRepository.getUserIdInSystem());
    }
    private void setupAutoComplete(){
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, radioStationRepository.getCountry());
        actvCountry.setAdapter(countryAdapter);
        actvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = (String) parent.getItemAtPosition(position);
                filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_COUNTRY_FILTER,selectedCountry);
                hideKeyboard(actvCountry);
                deleteCountry.setVisibility(VISIBLE);
                updateCount();
                actvCountry.clearFocus();
            }
        });
        setAutoCompleteTextViewFocusListener(actvCountry);

        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, radioStationRepository.getTags());
        actvTags.setAdapter(styleAdapter);
        actvTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStyle = (String) parent.getItemAtPosition(position);
                filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_TAGS_FILTER,selectedStyle);
                hideKeyboard(actvTags);
                deleteTags.setVisibility(VISIBLE);
                actvTags.clearFocus();
            }
        });
        setAutoCompleteTextViewFocusListener(actvTags);

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, radioStationRepository.getLang());
        actvLang.setAdapter(langAdapter);
        actvLang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = (String) parent.getItemAtPosition(position);
                filterRepository.setFilter(userRepository.getUserIdInSystem(),DatabaseHelper.COLUMN_LANG_FILTER,selectedLang);
                deleteLang.setVisibility(VISIBLE);
                hideKeyboard(actvLang);
                updateCount();
                actvLang.clearFocus();

            }
        });
    }
    private void findAllId(){
        actvCountry = findViewById(R.id.actvCountry);
        actvTags = findViewById(R.id.actvStyle);
        actvLang = findViewById(R.id.actvLang);
        backButton = findViewById(R.id.backButtonFilterView);
        count = findViewById(R.id.countStation);
        deleteCountry = findViewById(R.id.deleteCountryFilter);
        deleteTags = findViewById(R.id.deleteStyleFilter);
        deleteLang = findViewById(R.id.deleteLangFilter);
        confirmButton = findViewById(R.id.confirmButtonView);
        savedButton = findViewById(R.id.savedButtonFilter);
        topButton = findViewById(R.id.topButtonFilter);
        recomendedButton = findViewById(R.id.recomendedButtonFilter);
        spinnerSortBy = findViewById(R.id.sortType);
    }
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void setAutoCompleteTextViewFocusListener(@NonNull final MaterialAutoCompleteTextView actv) {
        actv.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !actv.getText().toString().isEmpty()) {
                actv.setText("");
            }
        });
    }
}