package by.roman.worldradio2.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.ui.adapters.ExpandableListAdapter;

public class SettingsFragment extends Fragment {
    private ExpandableListView expandableListViewTheme;
    private ExpandableListView expandableListViewLang;
    private ExpandableListView expandableListViewTimer;
    private ExpandableListView expandableListViewFilter;
    private ExpandableListView expandableListViewTP;
    private ExpandableListAdapter listAdapterTheme;
    private ExpandableListAdapter listAdapterLang;
    private ExpandableListAdapter listAdapterTimer;
    private ExpandableListAdapter listAdapterFilter;
    private ExpandableListAdapter listAdapterTP;
    private List<String> listDataHeaderTheme;
    private List<String> listDataHeaderLang;
    private List<String> listDataHeaderTimer;
    private List<String> listDataHeaderFilter;
    private List<String> listDataHeaderTP;
    private List<String> themeOptions;
    private List<String> langOptions;
    private List<String> timerOptions;
    private List<String> filterOptions;
    private List<String> tpOptions;
    private HashMap<String, List<String>> listDataChild;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        findAllId(view);
        prepareListData();
        listAdapterTheme = new ExpandableListAdapter(getContext(),listDataHeaderTheme,themeOptions);
        expandableListViewTheme.setAdapter(listAdapterTheme);
        listAdapterLang = new ExpandableListAdapter(getContext(),listDataHeaderLang,langOptions);
        expandableListViewLang.setAdapter(listAdapterLang);
        listAdapterTimer = new ExpandableListAdapter(getContext(),listDataHeaderTimer,timerOptions);
        expandableListViewTimer.setAdapter(listAdapterTimer);
        listAdapterFilter = new ExpandableListAdapter(getContext(),listDataHeaderFilter,filterOptions);
        expandableListViewFilter.setAdapter(listAdapterFilter);
        listAdapterTP = new ExpandableListAdapter(getContext(),listDataHeaderTP,tpOptions);
        expandableListViewTP.setAdapter(listAdapterTP);
        return view;
    }
    private void findAllId(View view){
        expandableListViewTheme = view.findViewById(R.id.expandableListViewTheme);
        expandableListViewLang = view.findViewById(R.id.expandableListViewLang);
        expandableListViewTimer = view.findViewById(R.id.expandableListViewTimer);
        expandableListViewFilter = view.findViewById(R.id.expandableListViewFilter);
        expandableListViewTP = view.findViewById(R.id.expandableListViewTP);
    }
    private void prepareListData() {
        listDataHeaderTheme = new ArrayList<>();
        listDataHeaderLang = new ArrayList<>();
        listDataHeaderTimer = new ArrayList<>();
        listDataHeaderFilter = new ArrayList<>();
        listDataHeaderTP = new ArrayList<>();

        listDataHeaderTheme.add("Тема");
        listDataHeaderLang.add("Язык");
        listDataHeaderTimer.add("Таймер");
        listDataHeaderFilter.add("Фильтр");
        listDataHeaderTP.add("Тех. поддержка");

        themeOptions = new ArrayList<>();
        themeOptions.add("Светлая");
        themeOptions.add("Тёмная");

        langOptions = new ArrayList<>();
        langOptions.add("Русский");
        langOptions.add("Английский");

        timerOptions = new ArrayList<>();
        timerOptions.add("Отображать секунды");
        timerOptions.add("Звук таймера");

        filterOptions = new ArrayList<>();
        filterOptions.add("Вид фильтра");

        tpOptions = new ArrayList<>();
        tpOptions.add("Политика конфиденциальности");
        tpOptions.add("Нашли ошибку?");
    }
}
