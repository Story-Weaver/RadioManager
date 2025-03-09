package by.roman.worldradio2.data.model;

public class Favorite {
    private int userId;
    private String stationUUID;

    public Favorite(int userId, String stationUUID) {
        this.userId = userId;
        this.stationUUID = stationUUID;
    }

    // Getters
    public int getUserId() { return userId; }
    public String getStationUUID() { return stationUUID; }
}
