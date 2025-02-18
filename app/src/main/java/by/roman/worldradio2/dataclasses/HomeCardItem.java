package by.roman.worldradio2.dataclasses;

public class HomeCardItem {
    private String logoURL;
    private String nameStation;
    private boolean isPlaying;

    public HomeCardItem(String logoURL, String nameStation){
        this.logoURL = logoURL;
        this.nameStation = nameStation;
        this.isPlaying = false;
    }

    public String getLogoURL(){return logoURL;}
    public String getNameStation(){return nameStation;}
    public boolean isPlaying(){return isPlaying;}
    public void setPlaying(boolean playing) { this.isPlaying = playing; }
}
