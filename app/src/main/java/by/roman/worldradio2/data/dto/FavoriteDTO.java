package by.roman.worldradio2.data.dto;

public class FavoriteDTO {
    private int userId;
    private int stationId;
    public FavoriteDTO(int userId, int stationId) {
        this.userId = userId;
        this.stationId = stationId;
    }

    public int getUserId(){ return userId; }
    public int getStationId(){ return stationId; }
    public void setUserId(int userId){ this.userId = userId; }
    public void setStationId(int stationId){ this.stationId = stationId; }
}
