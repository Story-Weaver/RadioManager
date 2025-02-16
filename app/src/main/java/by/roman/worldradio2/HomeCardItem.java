package by.roman.worldradio2;

public class HomeCardItem {
    private String logoURL;
    private String nameStation;
    private String nameSong;
    private boolean isPlaying;

    public HomeCardItem(String logoURL, String nameStation, String nameSong){
        this.logoURL = logoURL;
        this.nameStation = nameStation;
        this.nameSong = nameSong;
        this.isPlaying = false;
    }

    public String getLogoURL(){return logoURL;}
    public String getNameStation(){return nameStation;}
    public String getNameSong(){return nameSong;}
    public boolean isPlaying(){return isPlaying;}
    public void setPlaying(boolean playing) { this.isPlaying = playing; }
}
