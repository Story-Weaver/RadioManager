package by.roman.worldradio2.data.dto;

public class SettingsDTO {
    private int userId;
    private int theme;
    private int mapEnabled;
    private int timerSeconds;
    private int timerDots;
    private int filterEnabled;

    public SettingsDTO(int userId, int theme, int mapEnabled, int timerSeconds, int timerDots, int filterEnabled) {
        this.userId = userId;
        this.theme = theme;
        this.mapEnabled = mapEnabled;
        this.timerSeconds = timerSeconds;
        this.timerDots = timerDots;
        this.filterEnabled = filterEnabled;
    }

    // Getters
    public int getUserId() { return userId; }
    public int getTheme() { return theme; }
    public int getMapEnabled() { return mapEnabled; }
    public int getTimerSeconds() { return timerSeconds; }
    public int getTimerDots() { return timerDots; }
    public int getFilterType() { return filterEnabled; }

    // Setters
    public void setFilterEnabled(int filterEnabled) {
        this.filterEnabled = filterEnabled;
    }
    public void setTimerDots(int timerDots) {
        this.timerDots = timerDots;
    }
    public void setTimerSeconds(int timerSeconds) {
        this.timerSeconds = timerSeconds;
    }
    public void setMapEnabled(int mapEnabled) {
        this.mapEnabled = mapEnabled;
    }
    public void setTheme(int theme) {
        this.theme = theme;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
