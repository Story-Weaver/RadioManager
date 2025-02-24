package by.roman.worldradio2.dataclasses.model;

public class RadioStations {
    private int id;
    private String logoUrl;
    private String nameStation;
    private String streamUrl;
    private String country;
    private String style;
    private double lon;
    private double lat;
    private String lang;
    private int likes;
    private boolean isPlaying;

    public RadioStations(int id,String logoUrl, String nameStation, String streamUrl, String country, String style, double lon, double lat, String lang, int likes, boolean isPlaying){
        this.logoUrl = logoUrl;
        this.nameStation = nameStation;
        this.streamUrl = streamUrl;
        this.country = country;
        this.style = style;
        this.lon = lon;
        this.lat = lat;
        this.lang = lang;
        this.likes = likes;
        this.isPlaying = isPlaying;
        this.id = id;
    }
    public String getLogoUrl(){
        return logoUrl;
    }
    public String getNameStation(){
        return nameStation;
    }
    public String getStreamUrl(){
        return streamUrl;
    }
    public String getCountry(){
        return country;
    }
    public String getStyle(){
        return style;
    }
    public double getLon(){
        return lon;
    }
    public double getLat(){
        return lat;
    }
    public String getLang(){
        return lang;
    }
    public int getLikes(){return likes;}
    public int getId(){return id;}
    public void setPlaying(boolean flag){
        this.isPlaying = flag;
    }
    public boolean getIsPlaying(){
        return isPlaying;
    }
}
