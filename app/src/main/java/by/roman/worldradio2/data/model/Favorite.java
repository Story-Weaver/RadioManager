package by.roman.worldradio2.data.model;

public class Favorite {
    private int userId;
    private int stationId;

    public Favorite(int userId, int stationId) {
        this.userId = userId;
        this.stationId = stationId;
    }

    // Геттеры
    public int getUserId() { return userId; }
    public int getStationId() { return stationId; }

    // Сеттеры
    public void setStationId(int stationId) { this.stationId = stationId; }
}
