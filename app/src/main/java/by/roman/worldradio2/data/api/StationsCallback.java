package by.roman.worldradio2.data.api;

import java.util.List;

public interface StationsCallback {
    // Метод для обработки успешного получения радиостанций
    void onStationsFetched(List<Model> stations);

    // Метод для обработки ошибок
    void onError(Exception e);
}

