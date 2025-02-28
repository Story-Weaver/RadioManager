package by.roman.worldradio2.data.repository;

import android.database.sqlite.SQLiteDatabase;

public class SettingsRepository {
    private SQLiteDatabase db;
    public SettingsRepository(SQLiteDatabase db) {
        this.db = db;
    }
}
