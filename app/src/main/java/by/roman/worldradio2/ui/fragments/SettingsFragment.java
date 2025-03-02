package by.roman.worldradio2.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.api.Model;
import by.roman.worldradio2.data.api.RadioAPI;
import by.roman.worldradio2.data.api.StationsCallback;


public class SettingsFragment extends Fragment {
    private TextView text;
    private RadioAPI radioAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        text = view.findViewById(R.id.textJSON);
        radioAPI = new RadioAPI();

        radioAPI.fetchStations(new StationsCallback() {
            @Override
            public void onStationsFetched(List<Model> stations) {
                // Обновляем UI в основном потоке
                getActivity().runOnUiThread(() -> {
                    StringBuilder sb = new StringBuilder();
                    for (Model station : stations) {
                        sb.append(station.getName()).append(" - ").append(station.getCountry()).append("\n");
                    }
                    text.setText(sb.toString());
                });
            }

            @Override
            public void onError(Exception e) {
                // Обработка ошибки
                Log.e("RadioStationError", "Error fetching stations", e);
            }
        });

        return view;
    }
}
