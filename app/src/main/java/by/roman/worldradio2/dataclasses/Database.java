package by.roman.worldradio2.dataclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.dataclasses.model.RadioStations;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Radio.db";
    private static final int DATABASE_VERSION = 1;

    // Таблицы и столбцы
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


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_RADIO_STATION_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_SETTINGS_CREATE);
        db.execSQL(TABLE_FAVORITES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RADIO_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
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
    public long addUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_USER, name);
        values.put(COLUMN_EMAIL, email);
        return db.insert(TABLE_USER, null, values);
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
    public void setIsplaying(int id,boolean isPlaying){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isPlaying",isPlaying ? 1:0);
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.update(TABLE_RADIO_STATION,values,selection,selectionArgs);
        db.close();
    }
    public List<RadioStations> getRadioStatonWithFilter(String countryF,String langF, String styleF, int sort){ // TODO: debug
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String orderBy;

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
        List<RadioStations> radioStationsList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RADIO_STATION,
                new String[]{COLUMN_UUID_STATION, COLUMN_NAME_STATION, COLUMN_COUNTRY, COLUMN_LOGO_URL, COLUMN_STREAM_URL,
                        COLUMN_STYLE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_LANG, COLUMN_LIKES, COLUMN_ISPLAYING},
                selection.length() > 0 ? selection.toString() : null,
                selectionArgsList.isEmpty() ? null : selectionArgsList.toArray(new String[0]),
                null,
                null,
                orderBy);
        if(cursor != null){
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
                    RadioStations station = new RadioStations(id, logoUrl, name, streamUrl, country, style, latitude, longitude, lang, likes, isPlaying);
                    radioStationsList.add(station);
                }
            }
        }
        return radioStationsList;
    }
}
