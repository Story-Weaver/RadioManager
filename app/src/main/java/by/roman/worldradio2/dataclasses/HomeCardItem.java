package by.roman.worldradio2.dataclasses;

public class HomeCardItem {
    private String logoURL;
    private String nameStation;
    private boolean isPlaying;
    private String streamUrl;

    public HomeCardItem(String logoURL, String nameStation,String streamUrl){
        this.logoURL = logoURL;
        this.nameStation = nameStation;
        this.streamUrl = streamUrl;
        this.isPlaying = false;
    }

    public String getLogoURL(){return logoURL;}
    public String getNameStation(){return nameStation;}
    public String getStreamUrl(){return streamUrl;}
    public boolean isPlaying(){return isPlaying;}
    public void setPlaying(boolean playing) { this.isPlaying = playing; }
}
