package by.roman.worldradio2.data.dto;

public class FavoriteDTO {
    private int userId;
    private String stationUUID;
    public FavoriteDTO(int userId, String stationUUID) {
        this.userId = userId;
        this.stationUUID = stationUUID;
    }

    public int getUserId(){ return userId; }
    public String getStationUUID(){ return stationUUID; }
    public void setUserId(int userId){ this.userId = userId; }
    public void setStationId(String stationUUID){ this.stationUUID = stationUUID; }
}
