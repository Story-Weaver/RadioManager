package by.roman.worldradio2.data.api;

import java.util.List;

import by.roman.worldradio2.data.model.RadioStation;

public interface StationsCallback {
    void onStationsFetched(List<RadioStation> stations);
    void onError(Exception e);
}

