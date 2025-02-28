package by.roman.worldradio2.data.dto;

public class RadioStationDTO {
    private int id;
    private String name;
    private String country;
    private String logoUrl;
    private String streamUrl;
    private String style;
    private double latitude;
    private double longitude;
    private String lang;
    private int likes;
    private boolean isPlaying;

    public RadioStationDTO(int id, String name, String country, String logoUrl, String streamUrl,
                           String style, double latitude, double longitude, String lang, int likes, boolean isPlaying) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.logoUrl = logoUrl;
        this.streamUrl = streamUrl;
        this.style = style;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lang = lang;
        this.likes = likes;
        this.isPlaying = isPlaying;
    }
    public RadioStationDTO(int id,boolean isPlaying){
        this.id = id;
        this.isPlaying = isPlaying;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
