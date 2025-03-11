package by.roman.worldradio2.data.model;


import com.google.gson.annotations.SerializedName;

public class RadioStation {
    @SerializedName("stationuuid")
    private String stationUuid;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("url_resolved")
    private String urlResolved;
    @SerializedName("homepage")
    private String homepage;
    @SerializedName("favicon")
    private String favicon;
    @SerializedName("tags")
    private String tags; // Строка с тегами, разделенными запятыми
    @SerializedName("country")
    private String country;
    @SerializedName("countrycode")
    private String countryCode;
    @SerializedName("state")
    private String state;
    @SerializedName("language")
    private String language;
    @SerializedName("languagecodes")
    private String languageCodes;
    @SerializedName("votes")
    private int votes;
    @SerializedName("codec")
    private String codec;
    @SerializedName("bitrate")
    private int bitrate;
    @SerializedName("hls")
    private int hls;
    @SerializedName("geo_lat")
    private double geoLat;
    @SerializedName("geo_long")
    private double geoLong;
    @SerializedName("isPlaying")
    private int isPlaying;

    public RadioStation (String stationUuid, String name, String url, String urlResolved,
                           String homepage, String favicon, String tags, String country, String countryCode,
                           String state, String language, String languageCodes, int votes,
                           String codec, int bitrate, int hls,
                           double geoLat, double geoLong, int isPlaying) {
        this.stationUuid = stationUuid;
        this.name = name;
        this.url = url;
        this.urlResolved = urlResolved;
        this.homepage = homepage;
        this.favicon = favicon;
        this.tags = tags;
        this.country = country;
        this.countryCode = countryCode;
        this.state = state;
        this.language = language;
        this.languageCodes = languageCodes;
        this.votes = votes;
        this.codec = codec;
        this.bitrate = bitrate;
        this.hls = hls;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.isPlaying = isPlaying;
    }

    // Getters
    public String getStationUuid() { return stationUuid; }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getUrlResolved() { return urlResolved; }
    public String getHomepage() {return homepage; }
    public String getFavicon() { return favicon; }
    public String getTags() { return tags; }
    public String getCountry() { return country; }
    public String getCountryCode() { return countryCode; }
    public String getState() {return state; }
    public String getLanguage() { return language; }
    public String getLanguageCodes() { return languageCodes; }
    public int getVotes() { return votes; }
    public String getCodec() { return codec; }
    public int getBitrate() { return bitrate; }
    public int getHls() { return hls; }
    public double getGeoLat() { return geoLat; }
    public double getGeoLong() { return geoLong; }
    public int getIsPlaying() {
        return isPlaying;
    }

    // Setter
    public void setIsPlaying(int isPlaying){
        this.isPlaying = isPlaying;
    }
}