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

import java.util.List;

import by.roman.worldradio2.RadioManager;
import by.roman.worldradio2.TimerActivity;
import by.roman.worldradio2.adapters.HomeListAdapter;
import by.roman.worldradio2.R;
import by.roman.worldradio2.dataclasses.Database;
import by.roman.worldradio2.dataclasses.model.RadioStations;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<RadioStations> radioStationsList;
    RadioManager radioManager;
    private  int position;
    private ImageView timerButton;
    private BroadcastReceiver timerFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("by.roman.worldradio2.TIMER_FINISHED".equals(intent.getAction())) {
                Toast.makeText(getContext(), "Таймер завершен через сервис!", Toast.LENGTH_SHORT).show();
                if (radioManager != null) {
                    adapter.offIsPlaying();
                    radioManager.stop();

                }
                if (radioStationsList != null && position >= 0 && position < radioStationsList.size()) {
                    radioStationsList.get(position).setPlaying(false);
                    adapter.notifyItemChanged(position);
                }
            }
        }
    };
    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(timerFinishedReceiver, new IntentFilter("by.roman.worldradio2.TIMER_FINISHED"));
    }
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(timerFinishedReceiver);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findAllId(view);
        getData();
        radioManager = new RadioManager(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeListAdapter(getContext(), radioStationsList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
            this.position = position;
        },radioManager);
        recyclerView.setAdapter(adapter);
        timerButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), TimerActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void getData(){
        Database database = new Database(requireContext());
        //radioStationsList = database.getAllRadioStations();
        radioStationsList = database.getRadioStatonWithFilter(null,null,null,1);
    }
    private void findAllId(View view){
        timerButton = view.findViewById(R.id.timerButtonView);
        recyclerView = view.findViewById(R.id.cardView);
    }
    public List<RadioStations> getRadioStations() {
        return radioStationsList;
    }
    public RadioManager getRadioManager(){
        return radioManager;
    }
}
