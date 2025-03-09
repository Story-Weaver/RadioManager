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

    // Getters
    public int getUserId() { return userId; }
    public int getTheme() { return theme; }
    public int getMapEnabled() { return mapEnabled; }
    public int getTimerSeconds() { return timerSeconds; }
    public int getTimerDots() { return timerDots; }
    public int getFilterEnabled() { return filterEnabled; }
}
