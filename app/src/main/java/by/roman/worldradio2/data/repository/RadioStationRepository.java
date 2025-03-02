package by.roman.worldradio2.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.RadioStation;

public class RadioStationRepository {
    private SQLiteDatabase db;

    public RadioStationRepository(SQLiteDatabase db) {
        this.db = db;
    }
    public long addRadioStation(@NonNull RadioStationDTO dto) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME_STATION, dto.getName());
        values.put(DatabaseHelper.COLUMN_COUNTRY_STATION, dto.getCountry());
        values.put(DatabaseHelper.COLUMN_LOGO_URL_STATION, dto.getLogoUrl());
        values.put(DatabaseHelper.COLUMN_URL_STATION, dto.getStreamUrl());
        values.put(DatabaseHelper.COLUMN_STYLE_STATION, dto.getStyle());
        values.put(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION, dto.getLatitude());
        values.put(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION, dto.getLongitude());
        values.put(DatabaseHelper.COLUMN_LANGUAGE_STATION, dto.getLang());
        values.put(DatabaseHelper.COLUMN_VOTES_STATION, dto.getLikes());
        values.put(DatabaseHelper.COLUMN_ISPLAYING_STATION, dto.isPlaying() ? 1 : 0);
        return db.insert(DatabaseHelper.TABLE_RADIO_STATION, null, values);
    }
    public List<RadioStation> getAllRadioStations() {
        List<RadioStation> radioStationsList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_UUID_STATION, DatabaseHelper.COLUMN_NAME_STATION, DatabaseHelper.COLUMN_COUNTRY_STATION,
                             DatabaseHelper.COLUMN_LOGO_URL_STATION, DatabaseHelper.COLUMN_URL_STATION,
                             DatabaseHelper.COLUMN_STYLE_STATION, DatabaseHelper.COLUMN_GEO_LATITUDE_STATION, DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION,
                             DatabaseHelper.COLUMN_LANGUAGE_STATION, DatabaseHelper.COLUMN_VOTES_STATION, DatabaseHelper.COLUMN_ISPLAYING_STATION},
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_UUID_STATION);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_STATION);
                int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_STATION);
                int logoUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOGO_URL_STATION);
                int streamUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_STATION);
                int styleIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STYLE_STATION);
                int latIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION);
                int longIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION);
                int langIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
                int likesIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOTES_STATION);
                int playingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ISPLAYING_STATION);
                if (idIndex != -1 && nameIndex != -1 && countryIndex != -1 && logoUrlIndex != -1 &&
                        streamUrlIndex != -1 && styleIndex != -1 && latIndex != -1 && longIndex != -1 && langIndex != -1 &&
                        likesIndex != -1 && playingIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    String country = cursor.getString(countryIndex);
                    String logoUrl = cursor.getString(logoUrlIndex);
                    String streamUrl = cursor.getString(streamUrlIndex);
                    String style = cursor.getString(styleIndex);
                    double latitude = cursor.getDouble(latIndex);
                    double longitude = cursor.getDouble(longIndex);
                    String lang = cursor.getString(langIndex);
                    int likes = cursor.getInt(likesIndex);
                    boolean isPlaying = cursor.getInt(playingIndex) == 1;
                    RadioStation station = new RadioStation(id,logoUrl, name, streamUrl, country, style, latitude, longitude, lang,likes, isPlaying);
                    radioStationsList.add(station);
                }
            }
            cursor.close();
        }
        return radioStationsList;
    }
    public List<String> getCountry(){
        List<String> countryList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_COUNTRY_STATION},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_STATION);
                if (countryIndex != -1) {
                    countryList.add(cursor.getString(countryIndex));
                }
            }
            cursor.close();
        }
        return countryList;
    }
    public List<String> getLang(){
        List<String> langList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_LANGUAGE_STATION},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
                if (countryIndex != -1) {
                    langList.add(cursor.getString(countryIndex));
                }
            }
            cursor.close();
        }
        return langList;
    }
    public List<String> getStyle(){
        List<String> styleList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_STYLE_STATION},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STYLE_STATION);
                if (countryIndex != -1) {
                    styleList.add(cursor.getString(countryIndex));
                }
            }
            cursor.close();
        }
        return styleList;
    }
    public void setIsPlaying(int id,boolean isPlaying){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ISPLAYING_STATION, isPlaying ? 1 : 0);
        String selection = DatabaseHelper.COLUMN_UUID_STATION + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(DatabaseHelper.TABLE_RADIO_STATION, values, selection, selectionArgs);
    }
    public List<RadioStation> getRadioStationWithFilter(){ // TODO: debug
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String styleF = null;
        int sort = 0;
        String orderBy;
        Cursor cursor1 = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_USER_ID_FILTER, DatabaseHelper.COLUMN_COUNTRY_FILTER,
                             DatabaseHelper.COLUMN_LANG_FILTER, DatabaseHelper.COLUMN_STYLE_FILTER, DatabaseHelper.COLUMN_SORT_FILTER},
                null,
                null,
                null,
                null,
                null);
        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_FILTER);
            int langIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_LANG_FILTER);
            int styleIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_STYLE_FILTER);
            int sortIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_SORT_FILTER);

            if (countryIndex != -1) countryF = cursor1.getString(countryIndex);
            if (langIndex != -1) langF = cursor1.getString(langIndex);
            if (styleIndex != -1) styleF = cursor1.getString(styleIndex);
            if (sortIndex != -1) sort = cursor1.getInt(sortIndex);
        }
        if (countryF != null) {
            selection.append(DatabaseHelper.COLUMN_COUNTRY_STATION).append(" = ?");
            selectionArgsList.add(countryF);
        }
        if (langF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(DatabaseHelper.COLUMN_LANGUAGE_STATION).append(" = ?");
            selectionArgsList.add(langF);
        }
        if (styleF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(DatabaseHelper.COLUMN_STYLE_STATION).append(" = ?");
            selectionArgsList.add(styleF);
        }

        switch (sort) {
            case 1:
                orderBy = DatabaseHelper.COLUMN_VOTES_STATION + " DESC";
                break;
            case 2:
                orderBy = DatabaseHelper.COLUMN_NAME_STATION + " ASC";
                break;
            case 3:
                orderBy = DatabaseHelper.COLUMN_COUNTRY_STATION + " ASC";
                break;
            default:
                orderBy = null;
                break;
        }
        cursor1.close();
        List<RadioStation> radioStationList = new ArrayList<>();
        Cursor cursor2 = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_UUID_STATION, DatabaseHelper.COLUMN_NAME_STATION,
                             DatabaseHelper.COLUMN_COUNTRY_STATION, DatabaseHelper.COLUMN_LOGO_URL_STATION,
                             DatabaseHelper.COLUMN_URL_STATION, DatabaseHelper.COLUMN_STYLE_STATION,
                             DatabaseHelper.COLUMN_GEO_LATITUDE_STATION, DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION,
                             DatabaseHelper.COLUMN_LANGUAGE_STATION, DatabaseHelper.COLUMN_VOTES_STATION,
                             DatabaseHelper.COLUMN_ISPLAYING_STATION},
                selection.length() > 0 ? selection.toString() : null,
                selectionArgsList.isEmpty() ? null : selectionArgsList.toArray(new String[0]),
                null,
                null,
                orderBy);
        if(cursor2 != null){
            while (cursor2.moveToNext()) {
                int idIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_UUID_STATION);
                int nameIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_NAME_STATION);
                int countryIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_STATION);
                int logoUrlIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_LOGO_URL_STATION);
                int streamUrlIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_URL_STATION);
                int styleIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_STYLE_STATION);
                int latIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION);
                int longIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION);
                int langIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
                int likesIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_VOTES_STATION);
                int playingIndex = cursor2.getColumnIndex(DatabaseHelper.COLUMN_ISPLAYING_STATION);
                if (idIndex != -1 && nameIndex != -1 && countryIndex != -1 && logoUrlIndex != -1 &&
                        streamUrlIndex != -1 && styleIndex != -1 && latIndex != -1 && longIndex != -1 && langIndex != -1 &&
                        likesIndex != -1 && playingIndex != -1) {
                    int id = cursor2.getInt(idIndex);
                    String name = cursor2.getString(nameIndex);
                    String country = cursor2.getString(countryIndex);
                    String logoUrl = cursor2.getString(logoUrlIndex);
                    String streamUrl = cursor2.getString(streamUrlIndex);
                    String style = cursor2.getString(styleIndex);
                    double latitude = cursor2.getDouble(latIndex);
                    double longitude = cursor2.getDouble(longIndex);
                    String lang = cursor2.getString(langIndex);
                    int likes = cursor2.getInt(likesIndex);
                    boolean isPlaying = cursor2.getInt(playingIndex) == 1;
                    RadioStation station = new RadioStation(id, logoUrl, name, streamUrl, country, style, latitude, longitude, lang, likes, isPlaying);
                    radioStationList.add(station);
                }
            }
        }
        cursor2.close();
        return radioStationList;
    }
    public int getRadioStationCountWithFilter() {
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String styleF = null;

        Cursor cursor1 = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_USER_ID_FILTER, DatabaseHelper.COLUMN_COUNTRY_FILTER,
                             DatabaseHelper.COLUMN_LANG_FILTER, DatabaseHelper.COLUMN_STYLE_FILTER, DatabaseHelper.COLUMN_SORT_FILTER},
                null,
                null,
                null,
                null,
                null);

        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_FILTER);
            int langIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_LANG_FILTER);
            int styleIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_STYLE_FILTER);

            if (countryIndex != -1) countryF = cursor1.getString(countryIndex);
            if (langIndex != -1) langF = cursor1.getString(langIndex);
            if (styleIndex != -1) styleF = cursor1.getString(styleIndex);
        }
        cursor1.close();

        if (countryF != null) {
            selection.append(DatabaseHelper.COLUMN_COUNTRY_STATION).append(" = ?");
            selectionArgsList.add(countryF);
        }
        if (langF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(DatabaseHelper.COLUMN_LANGUAGE_STATION).append(" = ?");
            selectionArgsList.add(langF);
        }
        if (styleF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(DatabaseHelper.COLUMN_STYLE_STATION).append(" = ?");
            selectionArgsList.add(styleF);
        }

        Cursor cursor2 = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{"COUNT(*)"},
                selection.length() > 0 ? selection.toString() : null,
                selectionArgsList.isEmpty() ? null : selectionArgsList.toArray(new String[0]),
                null,
                null,
                null);

        int count = 0;
        if (cursor2 != null && cursor2.moveToFirst()) {
            count = cursor2.getInt(0);
        }
        cursor2.close();
        return count;
    }
}

