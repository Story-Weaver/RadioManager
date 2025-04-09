package by.roman.worldradio2.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.data.repository.SettingsRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.activities.MainActivity;
import by.roman.worldradio2.ui.activities.TimerActivity;
import by.roman.worldradio2.ui.adapters.HomeListAdapter;
import by.roman.worldradio2.R;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeListAdapter adapter;
    private List<RadioStation> radioStationList;
    private RadioStationRepository radioStationRepository;
    private SettingsRepository settingsRepository;
    private UserRepository userRepository;
    private  int position;
    private final static int limit = 20;
    private int offset = 0;
    private ImageView timerButton;
    private ImageView map;
    private RadioService radioService;
    private ConstraintLayout mapView;
    private final BroadcastReceiver timerFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("by.roman.worldradio2.TIMER_FINISHED".equals(intent.getAction())) {
                Toast.makeText(getContext(), "Таймер завершен через сервис!", Toast.LENGTH_SHORT).show();
                    adapter.offIsPlaying();
                    radioService.checkNow();

                if (radioStationList != null && position >= 0 && position < radioStationList.size()) {
                    radioStationList.get(position).setIsPlaying(0);
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
    public void onResume() {
        super.onResume();
        timerButton.setEnabled(true);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        radioService = RadioService.getInstance(getContext(),(MainActivity) requireContext());
        findAllId(view);
        getData();

        if(settingsRepository.getMapSetting(userRepository.getUserIdInSystem()) == 1){
            mapView.setVisibility(GONE);
            map.setVisibility(GONE);
        } else {
            mapView.setVisibility(VISIBLE);
            map.setVisibility(VISIBLE);
            map.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.frame4));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeListAdapter(getContext(), radioStationList, position -> {
            Toast.makeText(getContext(), "Нажат элемент " + position, Toast.LENGTH_SHORT).show();
            this.position = position;
        }, radioStationRepository);
        recyclerView.setAdapter(adapter);
        timerButton.setOnClickListener(v->{
            timerButton.setEnabled(false);
            Intent intent = new Intent(getContext(), TimerActivity.class);
            startActivity(intent);
        });

            map.setOnClickListener(v -> openGoogleMaps(radioStationRepository.getActiveStation().getGeoLat(),radioStationRepository.getActiveStation().getGeoLong()));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        getData();
                    }
                }
            }
        });
        return view;
    }
    private void openGoogleMaps(double latitude, double longitude) {
        // Формируем URL для Google Maps с маркером
        String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude + "&zoom=13";
        // zoom=13 — уровень масштабирования, можно изменить

        try {
            // Создаем Intent для открытия URL в браузере или приложении Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Unable to open Google Maps", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void getData() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        userRepository = new UserRepository(db);
        settingsRepository = new SettingsRepository(db);
        radioStationList = radioStationRepository.getAllRadioStations(limit, offset);
        offset += limit;

        if (adapter != null) {
            adapter.loadMoreData(radioStationList);
        }
    }
    private void findAllId(View view){
        map = view.findViewById(R.id.map);
        mapView = view.findViewById(R.id.mapView);
        timerButton = view.findViewById(R.id.timerButtonView);
        recyclerView = view.findViewById(R.id.cardView);
    }
}
