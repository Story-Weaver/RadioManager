package by.roman.worldradio2.ui.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.api.Model;
import by.roman.worldradio2.data.api.RadioAPI;
import by.roman.worldradio2.data.api.StationsCallback;
import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.RadioStation;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.RadioStationRepository;


public class SettingsFragment extends Fragment {
    private TextView text;
    private RadioAPI radioAPI;
    private RadioStationDTO radioStationDTO;
    private RadioStationRepository radioStationRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        text = view.findViewById(R.id.textJSON);
        radioAPI = new RadioAPI();
        radioStationDTO = null;
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);

//        radioAPI.fetchStations(new StationsCallback() {
//            @Override
//            public void onStationsFetched(List<Model> stations) {
//                getActivity().runOnUiThread(() -> {
//                    StringBuilder sb = new StringBuilder();
//                    for (Model station : stations) {
//                        sb.append(station.getName()).append(" - ").append(station.getCountry()).append("\n");
//                        radioStationDTO = null;
//                        radioStationDTO = new RadioStationDTO(station.getChangeUuid(),station.getStationUuid(),station.getName(),
//                                station.getUrl(),station.getUrlResolved(),station.getHomepage(),station.getFavicon(),station.getTags(),
//                                station.getCountry(),station.getCountryCode(),station.getIso31662(),station.getState(),
//                                station.getLanguage(),station.getLanguageCodes(),station.getVotes(),station.getLastChangeTime(),
//                                station.getLastChangeTimeIso8601(),station.getCodec(),station.getBitrate(),station.getHls(),
//                                station.getLastCheckOk(),station.getLastCheckTime(),station.getLastCheckTimeIso8601(),
//                                station.getLastCheckOkTime(),station.getLastCheckOkTimeIso8601(),station.getLastLocalCheckTime(),
//                                station.getLastLocalCheckTimeIso8601(),station.getClickTimestamp(),station.getClickTimestampIso8601(),
//                                station.getClickCount(),station.getClickTrend(),station.getSslError(),station.getGeoLat(),
//                                station.getGeoLong(),station.getGeoDistance(),station.getIsHasExtendedInfo(),station.getPlay());
//                        radioStationRepository.addRadioStation(radioStationDTO);
//                    }
//                    text.setText(sb.toString());
//                });
//            }
//            @Override
//            public void onError(Exception e) {
//                Log.e("RadioStationError", "Error fetching stations", e);
//            }
//        });

        return view;
    }
}
