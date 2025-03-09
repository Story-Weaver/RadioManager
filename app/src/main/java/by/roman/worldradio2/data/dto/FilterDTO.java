package by.roman.worldradio2.data.dto;

public class FilterDTO {
    private int userId;
    private String countryFilter;
    private String langFilter;
    private String styleFilter;
    private int sortFilter;

    public FilterDTO(int userId, String countryFilter, String langFilter, String styleFilter, int sortFilter) {
        this.userId = userId;
        this.countryFilter = countryFilter;
        this.langFilter = langFilter;
        this.styleFilter = styleFilter;
        this.sortFilter = sortFilter;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) { this.userId = userId; }

    public String getCountryFilter() {
        return countryFilter;
    }

    public void setCountryFilter(String countryFilter) {
        this.countryFilter = countryFilter;
    }

    public String getLangFilter() {
        return langFilter;
    }

    public void setLangFilter(String langFilter) {
        this.langFilter = langFilter;
    }

    public String getStyleFilter() {
        return styleFilter;
    }

    public void setStyleFilter(String styleFilter) {
        this.styleFilter = styleFilter;
    }

    public int getSortFilter() {
        return sortFilter;
    }

    public void setSortFilter(int sortFilter) {
        this.sortFilter = sortFilter;
    }
}
