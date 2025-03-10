package by.roman.worldradio2.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import by.roman.worldradio2.data.dto.FilterDTO;

public class FilterRepository {
    private SQLiteDatabase db;
    public FilterRepository(SQLiteDatabase db) {
        this.db = db;
    }
    public long addFilter(@NonNull FilterDTO dto) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID_FILTER, dto.getUserId());
        values.put(DatabaseHelper.COLUMN_COUNTRY_FILTER, dto.getCountryFilter());
        values.put(DatabaseHelper.COLUMN_LANG_FILTER, dto.getLangFilter());
        values.put(DatabaseHelper.COLUMN_TAGS_FILTER, dto.getStyleFilter());
        values.put(DatabaseHelper.COLUMN_SORT_FILTER, dto.getSortFilter());
        return db.insert(DatabaseHelper.TABLE_FILTER, null, values);
    }
    public String getCountryFilter(int userId) {
        String countryFilter = null;
        Cursor cursor = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_COUNTRY_FILTER},
                DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_FILTER);
            if (countryIndex != -1) {
                countryFilter = cursor.getString(countryIndex);
            }
        }
        cursor.close();
        return countryFilter;
    }
    public String getTagsFilter(int userId) {
        String tagsFilter = null;
        Cursor cursor = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_TAGS_FILTER},
                DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int tagsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TAGS_FILTER);
            if (tagsIndex != -1) {
                tagsFilter = cursor.getString(tagsIndex);
            }
        }
        cursor.close();
        return tagsFilter;
    }
    public String getLangFilter(int userId) {
        String langFilter = null;
        Cursor cursor = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_LANG_FILTER},
                DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANG_FILTER);
            if (countryIndex != -1) {
                langFilter = cursor.getString(countryIndex);
            }
        }
        cursor.close();
        return langFilter;
    }
    public int getSortFilter(int userId) {
        int sortFilter = 0;
        Cursor cursor = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_SORT_FILTER},
                DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int sortIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SORT_FILTER);
            if (sortIndex != -1) {
                sortFilter = cursor.getInt(sortIndex);
            }
        }
        cursor.close();
        return sortFilter;
    }
    public void setSort(int id,int sort){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SORT_FILTER,sort);
        String selection = DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(DatabaseHelper.TABLE_FILTER,values,selection,selectionArgs);
    }
    public void setFilter(int userId, String column, String value) {
        ContentValues values = new ContentValues();
        values.put(column, value);
        String selection = DatabaseHelper.COLUMN_USER_ID_FILTER + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        db.update(DatabaseHelper.TABLE_FILTER, values, selection, selectionArgs);
    }

}
