package by.roman.worldradio2.data.api;

import java.util.List;

import by.roman.worldradio2.data.model.RadioStation;

public interface StationsCallback {
    // Метод для обработки успешного получения радиостанций
    void onStationsFetched(List<RadioStation> stations);

    // Метод для обработки ошибок
    void onError(Exception e);
}

