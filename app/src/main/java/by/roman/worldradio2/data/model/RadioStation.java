package by.roman.worldradio2.data.model;


import com.google.gson.annotations.SerializedName;

public class RadioStation {
    @SerializedName("changeuuid")
    private String changeUuid;
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
    @SerializedName("iso_3166_2")
    private String iso31662;
    @SerializedName("state")
    private String state;
    @SerializedName("language")
    private String language;
    @SerializedName("languagecodes")
    private String languageCodes;
    @SerializedName("votes")
    private int votes;
    @SerializedName("lastchangetime")
    private String lastChangeTime;
    @SerializedName("lastchangetime_iso8601")
    private String lastChangeTimeIso8601;
    @SerializedName("codec")
    private String codec;
    @SerializedName("bitrate")
    private int bitrate;
    @SerializedName("hls")
    private int hls;
    @SerializedName("lastcheckok")
    private int lastCheckOk;
    @SerializedName("lastchecktime")
    private String lastCheckTime;
    @SerializedName("lastchecktime_iso8601")
    private String lastCheckTimeIso8601;
    @SerializedName("lastcheckoktime")
    private String lastCheckOkTime;
    @SerializedName("lastcheckoktime_iso8601")
    private String lastCheckOkTimeIso8601;
    @SerializedName("lastlocalchecktime")
    private String lastLocalCheckTime;
    @SerializedName("lastlocalchecktime_iso8601")
    private String lastLocalCheckTimeIso8601;
    @SerializedName("clicktimestamp")
    private String clickTimestamp;
    @SerializedName("clicktimestamp_iso8601")
    private String clickTimestampIso8601;
    @SerializedName("clickcount")
    private int clickCount;
    @SerializedName("clicktrend")
    private int clickTrend;
    @SerializedName("ssl_error")
    private int sslError;
    @SerializedName("geo_lat")
    private double geoLat;
    @SerializedName("geo_long")
    private double geoLong;
    @SerializedName("geo_distance")
    private double geoDistance;
    @SerializedName("has_extended_info")
    private boolean hasExtendedInfo;
    @SerializedName("isPlaying")
    private int isPlaying;

    public RadioStation (String changeUuid, String stationUuid, String name, String url, String urlResolved,
                           String homepage, String favicon, String tags, String country, String countryCode,
                           String iso31662, String state, String language, String languageCodes, int votes,
                           String lastChangeTime, String lastChangeTimeIso8601, String codec, int bitrate, int hls,
                           int lastCheckOk, String lastCheckTime, String lastCheckTimeIso8601, String lastCheckOkTime,
                           String lastCheckOkTimeIso8601,
                           String lastLocalCheckTime, String lastLocalCheckTimeIso8601, String clickTimestamp,
                           String clickTimestampIso8601, int clickCount,
                           int clickTrend, int sslError, double geoLat, double geoLong, double geoDistance,
                         boolean hasExtendedInfo, int isPlaying) {
        this.changeUuid = changeUuid;
        this.stationUuid = stationUuid;
        this.name = name;
        this.url = url;
        this.urlResolved = urlResolved;
        this.homepage = homepage;
        this.favicon = favicon;
        this.tags = tags;
        this.country = country;
        this.countryCode = countryCode;
        this.iso31662 = iso31662;
        this.state = state;
        this.language = language;
        this.languageCodes = languageCodes;
        this.votes = votes;
        this.lastChangeTime = lastChangeTime;
        this.lastChangeTimeIso8601 = lastChangeTimeIso8601;
        this.codec = codec;
        this.bitrate = bitrate;
        this.hls = hls;
        this.lastCheckOk = lastCheckOk;
        this.lastCheckTime = lastCheckTime;
        this.lastCheckTimeIso8601 = lastCheckTimeIso8601;
        this.lastCheckOkTime = lastCheckOkTime;
        this.lastCheckOkTimeIso8601 = lastCheckOkTimeIso8601;
        this.lastLocalCheckTime = lastLocalCheckTime;
        this.lastLocalCheckTimeIso8601 = lastLocalCheckTimeIso8601;
        this.clickTimestamp = clickTimestamp;
        this.clickTimestampIso8601 = clickTimestampIso8601;
        this.clickCount = clickCount;
        this.clickTrend = clickTrend;
        this.sslError = sslError;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.geoDistance = geoDistance;
        this.hasExtendedInfo = hasExtendedInfo;
        this.isPlaying = isPlaying;
    }

    // Getters
    public String getChangeUuid() { return changeUuid; }
    public String getStationUuid() { return stationUuid; }
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getUrlResolved() { return urlResolved; }
    public String getHomepage() {return homepage; }
    public String getFavicon() { return favicon; }
    public String getTags() { return tags; }
    public String getCountry() { return country; }
    public String getCountryCode() { return countryCode; }
    public String getIso31662() { return iso31662; }
    public String getState() {return state; }
    public String getLanguage() { return language; }
    public String getLanguageCodes() { return languageCodes; }
    public int getVotes() { return votes; }
    public String getLastChangeTime() { return lastChangeTime; }
    public String getLastChangeTimeIso8601() { return lastChangeTimeIso8601; }
    public String getCodec() { return codec; }
    public int getBitrate() { return bitrate; }
    public int getHls() { return hls; }
    public int getLastCheckOk() { return lastCheckOk; }
    public String getLastCheckTime() { return lastCheckTime; }
    public String getLastCheckTimeIso8601() { return lastCheckTimeIso8601; }
    public String getLastCheckOkTime() { return lastCheckOkTime; }
    public String getLastCheckOkTimeIso8601() { return lastCheckOkTimeIso8601; }
    public String getLastLocalCheckTime() { return lastLocalCheckTime; }
    public String getLastLocalCheckTimeIso8601() { return lastLocalCheckTimeIso8601; }
    public String getClickTimestamp() { return clickTimestamp; }
    public String getClickTimestampIso8601() { return clickTimestampIso8601; }
    public int getClickCount() { return clickCount; }
    public int getClickTrend() { return clickTrend; }
    public int getSslError() { return sslError; }
    public double getGeoLat() { return geoLat; }
    public double getGeoLong() { return geoLong; }
    public double getGeoDistance() { return geoDistance; }
    public boolean isHasExtendedInfo() { return hasExtendedInfo; }
    public int getIsPlaying() {
        return isPlaying;
    }

    // Setter
    public void setIsPlaying(int isPlaying){
        this.isPlaying = isPlaying;
    }
}