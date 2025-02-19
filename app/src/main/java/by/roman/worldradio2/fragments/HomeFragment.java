package by.roman.worldradio2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.RadioMeneger;
import by.roman.worldradio2.dataclasses.HomeCardItem;
import by.roman.worldradio2.adapters.HomeListAdapter;
import by.roman.worldradio2.R;
import by.roman.worldradio2.dataclasses.RadioStations;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<HomeCardItem> cardList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.cardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<RadioStations> radioStationsList = new ArrayList<>();
        radioStationsList.add(new RadioStations("","-","https://sonic01.instainternet.com/8374/stream","france",null,0,0,"fr"));

        radioStationsList.add(new RadioStations("https://cdn-radiotime-logos.tunein.com/s127108d.png","Radio Country Live New York","https://streaming.radiostreamlive.com/radiocountrylive_devices","usa",null,0,0,"en"));

        cardList = new ArrayList<>();
        for (RadioStations i : radioStationsList){
            if(i.getCountry() == "usa" || i.getCountry() == "france"){
                cardList.add(new HomeCardItem(i.getLogoUrl(),i.getNameStantion(),i.getStreamUrl()));
            }
        }
        adapter = new HomeListAdapter(getContext(), cardList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}
