package by.roman.worldradio2.data.model;

public class Filter {
    private int id;
    private String country;
    private String style;
    private String lang;
    private int sort;

    public Filter(int id,String country,String style,String lang,int sort){
        this.id = id;
        this.country = country;
        this.style = style;
        this.lang = lang;
        this.sort = sort;
    }

    // Getters
    public int getId(){ return id; }
    public String getCountry(){ return country; }
    public String getStyle(){ return style; }
    public String getLang(){ return lang; }
}
