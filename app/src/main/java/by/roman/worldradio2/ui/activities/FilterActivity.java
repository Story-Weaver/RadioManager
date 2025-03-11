package by.roman.worldradio2.ui.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import java.util.function.Supplier;

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
    private ImageView savedButton;
    private ImageView topButton;
    private ImageView recomendedButton;
    private TextView count;
    private int savedSort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
        initAll();
        loadSavedFilters();
        setupUI();
        backButton.setOnClickListener(v -> finish());
        savedButton.setOnClickListener(v -> {

        });
        topButton.setOnClickListener(v -> {

        });
        recomendedButton.setOnClickListener(v -> {

        });
    }
    private void initAll(){
        findAllId();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        filterRepository = new FilterRepository(db);
        userRepository = new UserRepository(db);
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
        loadFilter(actvCountry, deleteCountry, filterRepository.getCountryFilter(userRepository.getUserIdInSystem()));
        loadFilter(actvTags, deleteTags, filterRepository.getTagsFilter(userRepository.getUserIdInSystem()));
        loadFilter(actvLang, deleteLang, filterRepository.getLangFilter(userRepository.getUserIdInSystem()));
        savedSort = filterRepository.getSortFilter(userRepository.getUserIdInSystem());
    }
    private void loadFilter(MaterialAutoCompleteTextView actv, View deleteButton, String value) {
        if (value != null && !value.isEmpty()) {
            actv.setText(value);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }
    private void setupAutoCompleteAsync(MaterialAutoCompleteTextView actv, View deleteButton, String column, Supplier<List<String>> dataSupplier) {
        new Thread(() -> {
            List<String> items = dataSupplier.get(); // Загружаем данные в фоне
            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(actv.getContext(), android.R.layout.simple_dropdown_item_1line, items);
                actv.setAdapter(adapter);
            });
        }).start();

        actv.setOnItemClickListener((parent, view, position, id) -> {
            String selectedValue = (String) parent.getItemAtPosition(position);
            filterRepository.setFilter(userRepository.getUserIdInSystem(), column, selectedValue);
            hideKeyboard(actv);
            deleteButton.setVisibility(View.VISIBLE);
            updateCount();
            actv.clearFocus();
        });

        deleteButton.setOnClickListener(v -> {
            filterRepository.setFilter(userRepository.getUserIdInSystem(), column, null);
            actv.setText("");
            deleteButton.setVisibility(View.INVISIBLE);
            updateCount();
        });
    }

    private void setupUI(){
        updateCount();
        setupSortOptions();
        setupAutoCompleteAsync(actvCountry, deleteCountry, DatabaseHelper.COLUMN_COUNTRY_FILTER, () -> radioStationRepository.getCountry());
        setupAutoCompleteAsync(actvTags, deleteTags, DatabaseHelper.COLUMN_TAGS_FILTER, () -> radioStationRepository.getTags());
        setupAutoCompleteAsync(actvLang, deleteLang, DatabaseHelper.COLUMN_LANG_FILTER, () -> radioStationRepository.getLang());

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