package by.roman.worldradio2.data.api;

import android.util.Log;

import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.repository.RadioStationRepository;

import java.util.List;

public class LoadDataFromAPI {

    private RadioAPI radioAPI;
    private RadioStationRepository radioStationRepository;

    public LoadDataFromAPI(RadioStationRepository radioStationRepository) {
        this.radioAPI = new RadioAPI();
        this.radioStationRepository = radioStationRepository;
    }

    public void loadStations() {

        long startTime = System.currentTimeMillis();

        radioAPI.fetchStations(new StationsCallback() {
            @Override
            public void onStationsFetched(List<Model> stations) {
                for (Model station : stations) {
                    RadioStationDTO radioStationDTO = new RadioStationDTO(
                            station.getChangeUuid(), station.getStationUuid(), station.getName(),
                            station.getUrl(), station.getUrlResolved(), station.getHomepage(),
                            station.getFavicon(), station.getTags(), station.getCountry(),
                            station.getCountryCode(), station.getIso31662(), station.getState(),
                            station.getLanguage(), station.getLanguageCodes(), station.getVotes(),
                            station.getLastChangeTime(), station.getLastChangeTimeIso8601(),
                            station.getCodec(), station.getBitrate(), station.getHls(),
                            station.getLastCheckOk(), station.getLastCheckTime(),
                            station.getLastCheckTimeIso8601(), station.getLastCheckOkTime(),
                            station.getLastCheckOkTimeIso8601(), station.getLastLocalCheckTime(),
                            station.getLastLocalCheckTimeIso8601(), station.getClickTimestamp(),
                            station.getClickTimestampIso8601(), station.getClickCount(),
                            station.getClickTrend(), station.getSslError(), station.getGeoLat(),
                            station.getGeoLong(), station.getGeoDistance(),
                            station.getIsHasExtendedInfo(), station.getPlay());
// TODO: валидация станций
                    radioStationRepository.addRadioStation(radioStationDTO);
                }

                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                Log.d("LoadDataFromAPI", "Data loaded in: " + duration + " ms");
            }

            @Override
            public void onError(Exception e) {
                Log.e("RadioStationError", "Error fetching stations", e);
            }
        });
    }
}
