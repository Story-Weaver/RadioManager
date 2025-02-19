package by.roman.worldradio2.dataclasses;

import by.roman.worldradio2.RadioManager;

public class RadioStations {
    private String logoUrl;
    private String nameStantion;
    private String streamUrl;
    private String country;
    private String style;
    private double lon;
    private double lat;
    private String lang;
    private boolean isPlaying;
    RadioManager radioManager;

    public RadioStations(String logoUrl,String nameStantion, String streamUrl, String country, String style, double lon,double lat, String lang, RadioManager radioManager){
        this.logoUrl = logoUrl;
        this.nameStantion = nameStantion;
        this.streamUrl = streamUrl;
        this.country = country;
        this.style = style;
        this.lon = lon;
        this.lat = lat;
        this.lang = lang;
        this.isPlaying = false;

        this.radioManager = radioManager;
    }

    public void play(String song) {
        radioManager.play(streamUrl);
        isPlaying = true;
    }

    public String getLogoUrl(){
        return logoUrl;
    }
    public String getNameStantion(){
        return nameStantion;
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
    public double getLong(){
        return lon;
    }
    public double getLat(){
        return lat;
    }
    public String getLang(){
        return lang;
    }
    public void setPlaying(boolean flag){
        this.isPlaying = flag;
    }
    public boolean getIsPlaying(){
        return isPlaying;
    }
}
