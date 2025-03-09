package by.roman.worldradio2.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
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
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.ui.adapters.SaveListAdapter;


public class SaveFragment extends Fragment {
    private FavoriteRepository favoriteRepository;
    private RadioStationRepository radioStationRepository;
    private List<RadioStation> favoriteRadioStationList;
    private RecyclerView recyclerView;
    private SaveListAdapter adapter;
    private ImageView deleteButton;
    private int position;
    private RadioService radioService;

    private final BroadcastReceiver timerFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("by.roman.worldradio2.TIMER_FINISHED".equals(intent.getAction())) {
                Toast.makeText(getContext(), "Таймер завершен через сервис!", Toast.LENGTH_SHORT).show();
                adapter.offIsPlaying();
                radioService.checkNow();

                if (favoriteRadioStationList != null && position >= 0 && position < favoriteRadioStationList.size()) {
                    favoriteRadioStationList.get(position).setIsPlaying(0);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_save, container, false);
       findAllId(view);
       getData();
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter = new SaveListAdapter(getContext(), favoriteRadioStationList, position -> {
           Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
           this.position = position;
       }, radioStationRepository,favoriteRepository);
       recyclerView.setAdapter(adapter);
       deleteButton.setOnClickListener(v -> {
           favoriteRepository.removeAllFavorites(1);
           updateFavoritesList(); // Обновляем UI после удаления
       });
        return view;
    } // TODO: debug
    private void updateFavoritesList(){
        favoriteRadioStationList = favoriteRepository.getFavoriteStationByUser(1);
        adapter.updateList(favoriteRadioStationList);
    }
    private void getData(){
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        favoriteRepository = new FavoriteRepository(db);
        favoriteRadioStationList = favoriteRepository.getFavoriteStationByUser(1);
    }
    private void findAllId(View view){
        recyclerView = view.findViewById(R.id.recyclerView_Save);
        deleteButton = view.findViewById(R.id.deleteButton_Fragment_Save);
    }
}