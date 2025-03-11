package by.roman.worldradio2.data.api;

import com.google.gson.annotations.SerializedName;

public class Model {

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
    private String tags;

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
    public boolean getIsHasExtendedInfo() { return hasExtendedInfo; }
    public int getPlay(){ return 0; }



    // Setters
    public void setChangeUuid(String changeUuid) { this.changeUuid = changeUuid; }
    public void setStationUuid(String stationUuid) { this.stationUuid = stationUuid; }
    public void setName(String name) { this.name = name; }
    public void setUrl(String url) { this.url = url; }
    public void setUrlResolved(String urlResolved) { this.urlResolved = urlResolved; }
    public void setHomepage(String homepage) { this.homepage = homepage; }
    public void setFavicon(String favicon) { this.favicon = favicon; }
    public void setTags(String tags) { this.tags = tags; }
    public void setCountry(String country) { this.country = country; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public void setIso31662(String iso31662) { this.iso31662 = iso31662; }
    public void setState(String state) { this.state = state; }
    public void setLanguage(String language) { this.language = language; }
    public void setLanguageCodes(String languageCodes) { this.languageCodes = languageCodes; }
    public void setVotes(int votes) { this.votes = votes; }
    public void setLastChangeTime(String lastChangeTime) { this.lastChangeTime = lastChangeTime; }
    public void setLastChangeTimeIso8601(String lastChangeTimeIso8601) { this.lastChangeTimeIso8601 = lastChangeTimeIso8601; }
    public void setCodec(String codec) { this.codec = codec; }
    public void setBitrate(int bitrate) { this.bitrate = bitrate; }
    public void setHls(int hls) { this.hls = hls; }
    public void setLastCheckOk(int lastCheckOk) { this.lastCheckOk = lastCheckOk; }
    public void setLastCheckTime(String lastCheckTime) { this.lastCheckTime = lastCheckTime; }
    public void setLastCheckTimeIso8601(String lastCheckTimeIso8601) { this.lastCheckTimeIso8601 = lastCheckTimeIso8601; }
    public void setLastCheckOkTime(String lastCheckOkTime) { this.lastCheckOkTime = lastCheckOkTime; }
    public void setLastCheckOkTimeIso8601(String lastCheckOkTimeIso8601) { this.lastCheckOkTimeIso8601 = lastCheckOkTimeIso8601; }
    public void setLastLocalCheckTime(String lastLocalCheckTime) { this.lastLocalCheckTime = lastLocalCheckTime; }
    public void setLastLocalCheckTimeIso8601(String lastLocalCheckTimeIso8601) { this.lastLocalCheckTimeIso8601 = lastLocalCheckTimeIso8601; }
    public void setClickTimestamp(String clickTimestamp) { this.clickTimestamp = clickTimestamp; }
    public void setClickTimestampIso8601(String clickTimestampIso8601) { this.clickTimestampIso8601 = clickTimestampIso8601; }
    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
    public void setClickTrend(int clickTrend) { this.clickTrend = clickTrend; }
    public void setSslError(int sslError) { this.sslError = sslError; }
    public void setGeoLat(double geoLat) { this.geoLat = geoLat; }
    public void setGeoLong(double geoLong) { this.geoLong = geoLong; }
    public void setGeoDistance(double geoDistance) { this.geoDistance = geoDistance; }
    public void setHasExtendedInfo(boolean hasExtendedInfo) { this.hasExtendedInfo = hasExtendedInfo; }
}