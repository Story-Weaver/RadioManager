package by.roman.worldradio2.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import by.roman.worldradio2.ui.activities.FilterActivity;
import by.roman.worldradio2.R;
import by.roman.worldradio2.ui.adapters.TopListAdapter;
import by.roman.worldradio2.dataclasses.Database;
import by.roman.worldradio2.dataclasses.model.RadioStations;


public class TopFragment extends Fragment {
    private RecyclerView recyclerView;
    private TopListAdapter adapter;
    private List<RadioStations> radioStationsList;
    private  int position;
    private ImageView filterButton;
    @Override
    public void onResume() {
        super.onResume();
        updateDataInAdapter(); // Обновляем данные, когда фрагмент возвращается на экран
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        findAllId(view);
        getData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TopListAdapter(getContext(), radioStationsList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
            this.position = position;
        });
        recyclerView.setAdapter(adapter);
        filterButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), FilterActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void findAllId(View view){
        filterButton = view.findViewById(R.id.filterButtonView);
        recyclerView = view.findViewById(R.id.cardTopView);
    }
    private void getData(){
        Database database = new Database(requireContext());
        radioStationsList = database.getRadioStationWithFilter();
        if (adapter != null) {
            adapter.updateData(radioStationsList); // обновляем данные в адаптере
        }
    }
    public void updateDataInAdapter() {
        getData(); // Перезагружаем данные с учётом фильтров
        if (adapter != null) {
            adapter.notifyDataSetChanged(); // Обновляем адаптер
        }
    }
}