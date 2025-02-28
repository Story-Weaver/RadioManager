package by.roman.worldradio2.data.model;

public class RadioStation {
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

    public RadioStation(int id, String name, String country, String logoUrl, String streamUrl,
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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getLogoUrl() { return logoUrl; }
    public String getStreamUrl() { return streamUrl; }
    public String getStyle() { return style; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLang() { return lang; }
    public int getLikes() { return likes; }
    public boolean isPlaying() { return isPlaying; }

    public void setPlaying(boolean playing) { isPlaying = playing; }
}
