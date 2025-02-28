package by.roman.worldradio2.data.model;

import lombok.Setter;

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

    public int getId(){ return id; }
    public String getCountry(){ return country; }
    public String getStyle(){ return style; }
    public String getLang(){ return lang; }

    public void setCountry(String country){ this.country = country; }
    public void setStyle(String style){ this.style = style; }
    public void setLang(String lang){ this.lang = lang; }
    public void setSort(int sort){ this.sort = sort; }
}
