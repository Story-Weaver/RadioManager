package by.roman.worldradio2.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import by.roman.worldradio2.data.dto.SettingsDTO;

public class SettingsRepository {
    private SQLiteDatabase db;
    public SettingsRepository(SQLiteDatabase db) {
        this.db = db;
    }
    public long addSettings(@NonNull SettingsDTO dto) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID_SETTINGS, dto.getUserId());
        values.put(DatabaseHelper.COLUMN_THEME_SETTINGS,dto.getTheme());
        values.put(DatabaseHelper.COLUMN_MAP_SETTINGS, dto.getMapEnabled());
        values.put(DatabaseHelper.COLUMN_TIMER_SECONDS_SETTINGS, dto.getTimerSeconds());
        values.put(DatabaseHelper.COLUMN_TIMER_DOTS_SETTINGS, dto.getTimerDots());
        values.put(DatabaseHelper.COLUMN_FILTER_SETTINGS, dto.getFilterType());
        return db.insert(DatabaseHelper.TABLE_SETTINGS, null, values);
    }
    public int getThemeSetting(int id) {
        String query = "SELECT " + DatabaseHelper.COLUMN_THEME_SETTINGS + " FROM " + DatabaseHelper.TABLE_SETTINGS +
                " WHERE " + DatabaseHelper.COLUMN_USER_ID_SETTINGS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        int theme = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                theme = cursor.getInt(0);
            }
            cursor.close();
        }

        return theme;
    }
    public int getMapSetting(int id) {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SETTINGS +
                " WHERE " + DatabaseHelper.COLUMN_MAP_SETTINGS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        int map = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                map = cursor.getInt(0);
            }
            cursor.close();
        }

        return map;
    }
    public int getTimerSecSetting(int id) {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SETTINGS +
                " WHERE " + DatabaseHelper.COLUMN_TIMER_SECONDS_SETTINGS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        int sec = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                sec = cursor.getInt(0);
            }
            cursor.close();
        }

        return sec;
    }
    public int getTimerDotsSetting(int id) {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SETTINGS +
                " WHERE " + DatabaseHelper.COLUMN_TIMER_DOTS_SETTINGS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        int dot = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                dot = cursor.getInt(0);
            }
            cursor.close();
        }

        return dot;
    }
    public int getFilterSetting(int id) {
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_SETTINGS +
                " WHERE " + DatabaseHelper.COLUMN_FILTER_SETTINGS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        int filt = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                filt = cursor.getInt(0);
            }
            cursor.close();
        }

        return filt;
    }
    public void setSetting(int id, String column, int value) {
        ContentValues values = new ContentValues();
        values.put(column, value);
        String selection = DatabaseHelper.COLUMN_USER_ID_SETTINGS + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(DatabaseHelper.TABLE_SETTINGS, values, selection, selectionArgs);
    }
}
