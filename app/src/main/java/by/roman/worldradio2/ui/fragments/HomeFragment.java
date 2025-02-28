package by.roman.worldradio2.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
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

import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.ui.activities.TimerActivity;
import by.roman.worldradio2.ui.adapters.HomeListAdapter;
import by.roman.worldradio2.R;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<RadioStation> radioStationList;
    private RadioStationRepository radioStationRepository;
    private  int position;
    private ImageView timerButton;
    private final BroadcastReceiver timerFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("by.roman.worldradio2.TIMER_FINISHED".equals(intent.getAction())) {
                Toast.makeText(getContext(), "Таймер завершен через сервис!", Toast.LENGTH_SHORT).show();
                    adapter.offIsPlaying();

                if (radioStationList != null && position >= 0 && position < radioStationList.size()) {
                    radioStationList.get(position).setPlaying(false);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeListAdapter(getContext(), radioStationList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
            this.position = position;
        }, radioStationRepository);
        recyclerView.setAdapter(adapter);
        timerButton.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), TimerActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void getData(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        radioStationList = radioStationRepository.getAllRadioStations();
    }
    private void findAllId(View view){
        timerButton = view.findViewById(R.id.timerButtonView);
        recyclerView = view.findViewById(R.id.cardView);
    }
}
