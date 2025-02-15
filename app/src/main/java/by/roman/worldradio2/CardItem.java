package by.roman.worldradio2;

public class CardItem {
    private String logoURL;
    private String nameStation;
    private String nameSong;
    private int position;

    public CardItem(String logoURL,String nameStation,String nameSong,int position){
        this.logoURL = logoURL;
        this.nameStation = nameStation;
        this.nameSong = nameSong;
        this.position = position;
    }

    public String getLogoURL(){return logoURL;}
    public String getNameStation(){return nameStation;}
    public String getNameSong(){return nameSong;}
    public int getPosition(){return position;}
}
