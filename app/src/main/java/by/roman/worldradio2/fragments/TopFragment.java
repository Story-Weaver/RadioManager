package by.roman.worldradio2.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.RadioManager;
import by.roman.worldradio2.adapters.HomeListAdapter;
import by.roman.worldradio2.adapters.TopListAdapter;
import by.roman.worldradio2.dataclasses.Database;
import by.roman.worldradio2.dataclasses.model.RadioStations;


public class TopFragment extends Fragment {
    private RecyclerView recyclerView;
    private TopListAdapter adapter;
    RadioManager radioManager;
    private List<RadioStations> radioStationsList;
    private  int position;
    private ImageView filterButton;

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
        return view;
    }
    private void findAllId(View view){
        filterButton = view.findViewById(R.id.filterButtonView);
        recyclerView = view.findViewById(R.id.cardTopView);
    }
    private void getData(){
        Database database = new Database(requireContext());
        //radioStationsList = database.getAllRadioStations();
        radioStationsList = database.getRadioStatonWithFilter(null,null,null,1);
    }
}