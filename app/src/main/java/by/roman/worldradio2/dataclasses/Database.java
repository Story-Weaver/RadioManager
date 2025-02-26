package by.roman.worldradio2.dataclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.FontsContract;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.dataclasses.model.RadioStations;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Radio.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RADIO_STATION = "radiostation";
    private static final String COLUMN_UUID_STATION= "id";
    private static final String COLUMN_NAME_STATION = "name";
    private static final String COLUMN_LOGO_URL = "logoUrl";
    private static final String COLUMN_STREAM_URL = "streamUrl";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_STYLE = "style";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LIKES = "likes";
    private static final String COLUMN_LANG = "lang";
    private static final String COLUMN_ISPLAYING = "isPlaying";

    private static final String TABLE_USER = "user";
    private static final String COLUMN_UUID_USER = "id";
    private static final String COLUMN_NAME_USER = "name";
    private static final String COLUMN_EMAIL = "email";

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_STATION_ID = "station_id";

    private static final String TABLE_SETTINGS = "settings";
    private static final String COLUMN_THEME = "theme";
    private static final String COLUMN_MAP = "map";
    private static final String COLUMN_GPS = "gps"; // TODO: точно ли оно в бд?
    private static final String COLUMN_TIMER_SECONDS = "seconds";
    private static final String COLUMN_TIMER_DOTS = "dots";
    private static final String COLUMN_FILTER = "filter";

    private static final String TABLE_FILTER = "filter";
    private static final String COLUMN_USER_ID_F = "user_id_f";
    private static final String COLUMN_STYLE_F = "style_f";
    private static final String COLUMN_COUNTRY_F = "country_f";
    private static final String COLUMN_LANG_F = "lang_f";
    private static final String COLUMN_SORT_F = "sort_f";


    private static final String TABLE_RADIO_STATION_CREATE = "CREATE TABLE "+ TABLE_RADIO_STATION + " ("+
            COLUMN_UUID_STATION + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NAME_STATION + " TEXT, "+
            COLUMN_COUNTRY +      " TEXT, "+
            COLUMN_LOGO_URL +     " TEXT, "+
            COLUMN_STREAM_URL +   " TEXT, "+
            COLUMN_STYLE +        " TEXT, "+
            COLUMN_LATITUDE +     " REAL, "+
            COLUMN_LONGITUDE +    " REAL, "+
            COLUMN_LANG +         " TEXT, "+
            COLUMN_LIKES +        " INTEGER, "+
            COLUMN_ISPLAYING +    " INTEGER);";

    private static final String TABLE_USER_CREATE = "CREATE TABLE "+ TABLE_USER + " ("+
            COLUMN_UUID_USER +    " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NAME_USER +    " TEXT, "+
            COLUMN_EMAIL + " TEXT);";

    private static final String TABLE_SETTINGS_CREATE = "CREATE TABLE "+ TABLE_SETTINGS + " ("+
            COLUMN_USER_ID +       " INTEGER, "+
            COLUMN_THEME +         " INTEGER, "+
            COLUMN_MAP +           " INTEGER, "+
            //COLUMN_GPS +         " INTEGER, "+
            COLUMN_TIMER_SECONDS + " INTEGER, "+
            COLUMN_TIMER_DOTS +    " INTEGER, "+
            COLUMN_FILTER +        " INTEGER);";

    private static final String TABLE_FAVORITES_CREATE = "CREATE TABLE " + TABLE_FAVORITES + " (" +
            COLUMN_USER_ID + " INTEGER, " +
            COLUMN_STATION_ID + " INTEGER, " +
            "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_STATION_ID + "), " +
            "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_UUID_USER + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_STATION_ID + ") REFERENCES " + TABLE_RADIO_STATION + "(" + COLUMN_UUID_STATION + ") ON DELETE CASCADE);";

    private static final String TABLE_FILTER_CREATE = "CREATE TABLE " + TABLE_FILTER + " (" +
            COLUMN_USER_ID_F + " INTEGER, "+
            COLUMN_COUNTRY_F + " TEXT, "+
            COLUMN_LANG_F + " TEXT, "+
            COLUMN_STYLE_F + " TEXT, "+
            COLUMN_SORT_F + " INTEGER);";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(TABLE_RADIO_STATION_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_SETTINGS_CREATE);
        db.execSQL(TABLE_FAVORITES_CREATE);
        db.execSQL(TABLE_FILTER_CREATE);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RADIO_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER);
        onCreate(db);
    }

    public long addRadioStation(String name, String country, String logoUrl, String streamUrl,
                                String style, double latitude, double longitude, String lang,int likes, boolean isPlaying) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STATION, name);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_LOGO_URL, logoUrl);
        values.put(COLUMN_STREAM_URL, streamUrl);
        values.put(COLUMN_STYLE, style);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_LANG,lang);
        values.put(COLUMN_LIKES,likes);
        values.put(COLUMN_ISPLAYING, isPlaying ? 1 : 0);
        return db.insert(TABLE_RADIO_STATION, null, values);
    }
    public long addFavorite(int userId, int stationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_STATION_ID, stationId);
        return db.insert(TABLE_FAVORITES, null, values);
    }
    public long addUser(String name, String email) { // TODO: delete email
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER, name);
        values.put(COLUMN_EMAIL, email);
        return db.insert(TABLE_USER, null, values);
    }
    public long addFilter(int id,String country_f,String lang_f,String style_f,int sort_f) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID_F, id);
        values.put(COLUMN_COUNTRY_F, country_f);
        values.put(COLUMN_LANG_F, lang_f);
        values.put(COLUMN_STYLE_F, style_f);
        values.put(COLUMN_SORT_F, sort_f);
        return db.insert(TABLE_FILTER, null, values);
    }
    public List<RadioStations> getAllRadioStations() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<RadioStations> radioStationsList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_UUID_STATION, COLUMN_NAME_STATION, COLUMN_COUNTRY, COLUMN_LOGO_URL, COLUMN_STREAM_URL,
                        COLUMN_STYLE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_LANG, COLUMN_LIKES, COLUMN_ISPLAYING},
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(COLUMN_UUID_STATION);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME_STATION);
                int countryIndex = cursor.getColumnIndex(COLUMN_COUNTRY);
                int logoUrlIndex = cursor.getColumnIndex(COLUMN_LOGO_URL);
                int streamUrlIndex = cursor.getColumnIndex(COLUMN_STREAM_URL);
                int styleIndex = cursor.getColumnIndex(COLUMN_STYLE);
                int latIndex = cursor.getColumnIndex(COLUMN_LATITUDE);
                int longIndex = cursor.getColumnIndex(COLUMN_LONGITUDE);
                int langIndex = cursor.getColumnIndex(COLUMN_LANG);
                int likesIndex = cursor.getColumnIndex(COLUMN_LIKES);
                int playingIndex = cursor.getColumnIndex(COLUMN_ISPLAYING);
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
                    RadioStations station = new RadioStations(id,logoUrl, name, streamUrl, country, style, latitude, longitude, lang,likes, isPlaying);
                    radioStationsList.add(station);
                }
            }
            cursor.close();
        }
        return radioStationsList;
    }
    public List<String> getCountry(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> countryList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_COUNTRY},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(COLUMN_COUNTRY);
                if (countryIndex != -1) {
                    countryList.add(cursor.getString(countryIndex));
                }
            }
        }
        cursor.close();
        return countryList;
    }
    public List<String> getLang(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> langList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_LANG},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(COLUMN_LANG);
                if (countryIndex != -1) {
                    langList.add(cursor.getString(countryIndex));
                }
            }
        }
        cursor.close();
        return langList;
    }
    public List<String> getStyle(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> styleList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_STYLE},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int countryIndex = cursor.getColumnIndex(COLUMN_STYLE);
                if (countryIndex != -1) {
                    styleList.add(cursor.getString(countryIndex));
                }
            }
        }
        cursor.close();
        return styleList;
    }
    public String getCountryFilter(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countryFilter = null;
        Cursor cursor = db.query(
                TABLE_FILTER,
                new String[]{COLUMN_COUNTRY_F},
                COLUMN_USER_ID_F + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int countryIndex = cursor.getColumnIndex(COLUMN_COUNTRY_F);
            if (countryIndex != -1) {
                countryFilter = cursor.getString(countryIndex);
            }
        }
        cursor.close();
        return countryFilter;
    }
    public String getStyleFilter(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String styleFilter = null;
        Cursor cursor = db.query(
                TABLE_FILTER,
                new String[]{COLUMN_STYLE_F},
                COLUMN_USER_ID_F + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int countryIndex = cursor.getColumnIndex(COLUMN_STYLE_F);
            if (countryIndex != -1) {
                styleFilter = cursor.getString(countryIndex);
            }
        }
        cursor.close();
        return styleFilter;
    }
    public String getLangFilter(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String langFilter = null;
        Cursor cursor = db.query(
                TABLE_FILTER,
                new String[]{COLUMN_LANG_F},
                COLUMN_USER_ID_F + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int countryIndex = cursor.getColumnIndex(COLUMN_LANG_F);
            if (countryIndex != -1) {
                langFilter = cursor.getString(countryIndex);
            }
        }
        cursor.close();
        return langFilter;
    }
    public int getSortFilter(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int sortFilter = 0;
        Cursor cursor = db.query(
                TABLE_FILTER,
                new String[]{COLUMN_SORT_F},
                COLUMN_USER_ID_F + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int sortIndex = cursor.getColumnIndex(COLUMN_SORT_F);
            if (sortIndex != -1) {
                sortFilter = cursor.getInt(sortIndex);
            }
        }
        cursor.close();
        return sortFilter;
    }
    public void setIsplaying(int id,boolean isPlaying){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isPlaying",isPlaying ? 1:0);
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_RADIO_STATION,values,selection,selectionArgs);
        db.close();
    }
    public void setFilter(int id, @NonNull String typef, String filter){
        String typefilter = null;
        switch (typef){
            case "country":
                typefilter = COLUMN_COUNTRY_F;
                break;
            case "lang":
                typefilter = COLUMN_LANG_F;
                break;
            case "style":
                typefilter = COLUMN_STYLE_F;
                break;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(typefilter,filter);
        String selection = COLUMN_USER_ID_F + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_FILTER,values,selection,selectionArgs);
    }
    public void setSort(int id,int sort){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SORT_F,sort);
        String selection = COLUMN_USER_ID_F + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_FILTER,values,selection,selectionArgs);
    }
    public List<RadioStations> getRadioStationWithFilter(){ // TODO: debug
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String styleF = null;
        int sort = 0;
        String orderBy;
        Cursor cursor1 = db.query(TABLE_FILTER,
                new String[]{COLUMN_USER_ID_F,COLUMN_COUNTRY_F,COLUMN_LANG_F,COLUMN_STYLE_F,COLUMN_SORT_F},
                null,
                null,
                null,
                null,
                null);
        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(COLUMN_COUNTRY_F);
            int langIndex = cursor1.getColumnIndex(COLUMN_LANG_F);
            int styleIndex = cursor1.getColumnIndex(COLUMN_STYLE_F);
            int sortIndex = cursor1.getColumnIndex(COLUMN_SORT_F);

            if (countryIndex != -1) countryF = cursor1.getString(countryIndex);
            if (langIndex != -1) langF = cursor1.getString(langIndex);
            if (styleIndex != -1) styleF = cursor1.getString(styleIndex);
            if (sortIndex != -1) sort = cursor1.getInt(sortIndex);
        }
        if (countryF != null) {
            selection.append(COLUMN_COUNTRY).append(" = ?");
            selectionArgsList.add(countryF);
        }
        if (langF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COLUMN_LANG).append(" = ?");
            selectionArgsList.add(langF);
        }
        if (styleF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COLUMN_STYLE).append(" = ?");
            selectionArgsList.add(styleF);
        }

        switch (sort) {
            case 1:
                orderBy = COLUMN_LIKES + " DESC";
                break;
            case 2:
                orderBy = COLUMN_NAME_STATION + " ASC";
                break;
            case 3:
                orderBy = COLUMN_COUNTRY + " ASC";
                break;
            default:
                orderBy = null;
                break;
        }
        cursor1.close();
        List<RadioStations> radioStationsList = new ArrayList<>();
        Cursor cursor2 = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_UUID_STATION, COLUMN_NAME_STATION, COLUMN_COUNTRY, COLUMN_LOGO_URL, COLUMN_STREAM_URL,
                        COLUMN_STYLE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_LANG, COLUMN_LIKES, COLUMN_ISPLAYING},
                selection.length() > 0 ? selection.toString() : null,
                selectionArgsList.isEmpty() ? null : selectionArgsList.toArray(new String[0]),
                null,
                null,
                orderBy);
        if(cursor2 != null){
            while (cursor2.moveToNext()) {
                int idIndex = cursor2.getColumnIndex(COLUMN_UUID_STATION);
                int nameIndex = cursor2.getColumnIndex(COLUMN_NAME_STATION);
                int countryIndex = cursor2.getColumnIndex(COLUMN_COUNTRY);
                int logoUrlIndex = cursor2.getColumnIndex(COLUMN_LOGO_URL);
                int streamUrlIndex = cursor2.getColumnIndex(COLUMN_STREAM_URL);
                int styleIndex = cursor2.getColumnIndex(COLUMN_STYLE);
                int latIndex = cursor2.getColumnIndex(COLUMN_LATITUDE);
                int longIndex = cursor2.getColumnIndex(COLUMN_LONGITUDE);
                int langIndex = cursor2.getColumnIndex(COLUMN_LANG);
                int likesIndex = cursor2.getColumnIndex(COLUMN_LIKES);
                int playingIndex = cursor2.getColumnIndex(COLUMN_ISPLAYING);
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
                    RadioStations station = new RadioStations(id, logoUrl, name, streamUrl, country, style, latitude, longitude, lang, likes, isPlaying);
                    radioStationsList.add(station);
                }
            }
        }
        cursor2.close();
        return radioStationsList;
    }
    public int getRadioStationCountWithFilter() {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String styleF = null;

        Cursor cursor1 = db.query(TABLE_FILTER,
                new String[]{COLUMN_USER_ID_F, COLUMN_COUNTRY_F, COLUMN_LANG_F, COLUMN_STYLE_F, COLUMN_SORT_F},
                null,
                null,
                null,
                null,
                null);

        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(COLUMN_COUNTRY_F);
            int langIndex = cursor1.getColumnIndex(COLUMN_LANG_F);
            int styleIndex = cursor1.getColumnIndex(COLUMN_STYLE_F);

            if (countryIndex != -1) countryF = cursor1.getString(countryIndex);
            if (langIndex != -1) langF = cursor1.getString(langIndex);
            if (styleIndex != -1) styleF = cursor1.getString(styleIndex);
        }
        cursor1.close();

        if (countryF != null) {
            selection.append(COLUMN_COUNTRY).append(" = ?");
            selectionArgsList.add(countryF);
        }
        if (langF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COLUMN_LANG).append(" = ?");
            selectionArgsList.add(langF);
        }
        if (styleF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(COLUMN_STYLE).append(" = ?");
            selectionArgsList.add(styleF);
        }

        Cursor cursor2 = db.query(TABLE_RADIO_STATION,
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