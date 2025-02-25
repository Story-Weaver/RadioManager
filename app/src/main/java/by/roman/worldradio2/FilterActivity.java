package by.roman.worldradio2;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.dataclasses.Database;

public class    FilterActivity extends AppCompatActivity {
    private MaterialAutoCompleteTextView actvCountry;
    private MaterialAutoCompleteTextView actvStyle;
    private MaterialAutoCompleteTextView actvLang;
    private ImageView backButton;
    private ImageView deleteCountry;
    private ImageView deleteStyle;
    private ImageView deleteLang;
    private TextView count;
    private Database database;
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
        database = new Database(getApplicationContext());
        loadSavedFilters();
        updateCount();
        setupAutoComplete();
        setAutoCompleteTextViewFocusListener(actvLang);
        backButton.setOnClickListener(v ->{
            finish();
        });
        deleteCountry.setOnClickListener(v->{
            database.setFilter(1,"country",null);
            updateCount();
            actvCountry.setText("");
            deleteCountry.setVisibility(INVISIBLE);
        });
        deleteStyle.setOnClickListener(v->{
            database.setFilter(1,"style",null);
            updateCount();
            actvStyle.setText("");
            deleteStyle.setVisibility(INVISIBLE);
        });
        deleteLang.setOnClickListener(v->{
            database.setFilter(1,"lang",null);
            updateCount();
            actvLang.setText("");
            deleteLang.setVisibility(INVISIBLE);
        });
    }
    private void updateCount(){
        count.setText("Подходит " + database.getRadioStationCountWithFilter() + " радиостанций");
    }
    private void loadSavedFilters(){
        String savedCountry = database.getCountryFilter(1);
        if (savedCountry != null && !savedCountry.isEmpty()) {
            actvCountry.setText(savedCountry);
            deleteCountry.setVisibility(VISIBLE);
        } else deleteCountry.setVisibility(INVISIBLE);
        String savedStyle = database.getStyleFilter(1);
        if (savedStyle != null && !savedStyle.isEmpty()) {
            actvStyle.setText(savedStyle);
            deleteStyle.setVisibility(VISIBLE);
        } else deleteStyle.setVisibility(INVISIBLE);
        String savedLang = database.getLangFilter(1);
        if (savedLang != null && !savedLang.isEmpty()) {
            actvLang.setText(savedLang);
            deleteLang.setVisibility(VISIBLE);
        } else deleteLang.setVisibility(INVISIBLE);
    }
    private void setupAutoComplete(){
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, database.getCountry());
        actvCountry.setAdapter(countryAdapter);
        actvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = (String) parent.getItemAtPosition(position);
                database.setFilter(1,"country",selectedCountry);
                hideKeyboard(actvCountry);
                deleteCountry.setVisibility(VISIBLE);
                updateCount();
                actvCountry.clearFocus();
            }
        });
        setAutoCompleteTextViewFocusListener(actvCountry);

        ArrayAdapter<String> styleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, database.getStyle());
        actvStyle.setAdapter(styleAdapter);
        actvStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStyle = (String) parent.getItemAtPosition(position);
                database.setFilter(1,"style",selectedStyle);
                hideKeyboard(actvStyle);
                deleteStyle.setVisibility(VISIBLE);
                actvStyle.clearFocus();
            }
        });
        setAutoCompleteTextViewFocusListener(actvStyle);

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, database.getLang());
        actvLang.setAdapter(langAdapter);
        actvLang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = (String) parent.getItemAtPosition(position);
                database.setFilter(1,"lang",selectedLang);
                deleteLang.setVisibility(VISIBLE);
                hideKeyboard(actvLang);
                updateCount();
                actvLang.clearFocus();

            }
        });
    }
    private void findAllId(){
        actvCountry = findViewById(R.id.actvCountry);
        actvStyle = findViewById(R.id.actvStyle);
        actvLang = findViewById(R.id.actvLang);
        backButton = findViewById(R.id.backButtonFilterView);
        count = findViewById(R.id.countStation);
        deleteCountry = findViewById(R.id.deleteCountryFilter);
        deleteStyle = findViewById(R.id.deleteStyleFilter);
        deleteLang = findViewById(R.id.deleteLangFilter);
    }
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // Скрыть клавиатуру
        }
    }
    private void setAutoCompleteTextViewFocusListener(final MaterialAutoCompleteTextView actv) {
        actv.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && !actv.getText().toString().isEmpty()) {
                actv.setText("");
            }
        });
    }
}