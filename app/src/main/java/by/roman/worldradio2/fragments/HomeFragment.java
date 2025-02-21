package by.roman.worldradio2.fragments;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.MainActivity;
import by.roman.worldradio2.RadioManager;
import by.roman.worldradio2.TimerActivity;
import by.roman.worldradio2.adapters.HomeListAdapter;
import by.roman.worldradio2.R;
import by.roman.worldradio2.dataclasses.RadioStations;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<RadioStations> radioStationsList;
    RadioManager radioManager;
    private ImageView timerButton;
    private BroadcastReceiver timerFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("by.roman.worldradio2.TIMER_FINISHED".equals(intent.getAction())) {
                Toast.makeText(getContext(), "Таймер завершен!", Toast.LENGTH_SHORT).show();
                radioManager.stop();

            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // Регистрация приёмника с использованием LocalBroadcastManager
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(timerFinishedReceiver, new IntentFilter("by.roman.worldradio2.TIMER_FINISHED"));
    }

    @Override
    public void onStop() {
        super.onStop();
        // Отмена регистрации приёмника
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(timerFinishedReceiver);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        timerButton = view.findViewById(R.id.timerButtonView);
        recyclerView = view.findViewById(R.id.cardView);
        radioManager = new RadioManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        radioStationsList = new ArrayList<>();
        radioStationsList.add(new RadioStations("","-","https://sonic01.instainternet.com/8374/stream","france",null,0,0,"fr",radioManager));
        radioStationsList.add(new RadioStations("https://cdn-radiotime-logos.tunein.com/s127108d.png","Radio Country Live New York","https://streaming.radiostreamlive.com/radiocountrylive_devices","usa",null,0,0,"en",radioManager));
        adapter = new HomeListAdapter(getContext(), radioStationsList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
        },radioManager);
        recyclerView.setAdapter(adapter);
        timerButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), TimerActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public List<RadioStations> getRadioStations() {
        return radioStationsList;
    }
}
