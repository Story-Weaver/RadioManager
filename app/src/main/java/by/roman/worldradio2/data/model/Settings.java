package by.roman.worldradio2.data.model;

public class Settings {
    private int userId;
    private int theme;
    private int mapEnabled;
    private int timerSeconds;
    private int timerDots;
    private int filterEnabled;

    public Settings(int userId, int theme, int mapEnabled, int timerSeconds, int timerDots, int filterEnabled) {
        this.userId = userId;
        this.theme = theme;
        this.mapEnabled = mapEnabled;
        this.timerSeconds = timerSeconds;
        this.timerDots = timerDots;
        this.filterEnabled = filterEnabled;
    }

    // Геттеры
    public int getUserId() { return userId; }
    public int getTheme() { return theme; }
    public int getMapEnabled() { return mapEnabled; }
    public int getTimerSeconds() { return timerSeconds; }
    public int getTimerDots() { return timerDots; }
    public int getFilterEnabled() { return filterEnabled; }

    // Сеттеры
    public void setTheme(int theme) { this.theme = theme; }
    public void setMapEnabled(int mapEnabled) { this.mapEnabled = mapEnabled; }
    public void setTimerSeconds(int timerSeconds) { this.timerSeconds = timerSeconds; }
    public void setTimerDots(int timerDots) { this.timerDots = timerDots; }
    public void setFilterEnabled(int filterEnabled) { this.filterEnabled = filterEnabled; }
}
