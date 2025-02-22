package by.roman.worldradio2.dataclasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

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
    private static final String COLUMN_ISPLAYING = "isPlaying";

    private static final String TABLE_USER = "user";
    private static final String COLUMN_UUID_USER = "id";
    private static final String COLUMN_NAME_USER = "name";
    private static final String COLUMN_EMAIL = "email";

    private static final String TABLE_FAVORITES = "favorites";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_STATION_ID = "station_id";

    private static final String TABLE_SETTINGS = "settings";

    private static final String TABLE_RADIO_STATION_CREATE = "CREATE TABLE "+ TABLE_RADIO_STATION + " ("+
            COLUMN_UUID_STATION + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NAME_STATION + " TEXT, "+
            COLUMN_COUNTRY +      " TEXT, "+
            COLUMN_LOGO_URL +     " TEXT, "+
            COLUMN_STREAM_URL +   " TEXT, "+
            COLUMN_STYLE +        " TEXT, "+
            COLUMN_LATITUDE +     " REAL, "+
            COLUMN_LONGITUDE +    " REAL, "+
            COLUMN_ISPLAYING +    " INTEGER);";

    private static final String TABLE_USER_CREATE = "CREATE TABLE "+ TABLE_USER + " ("+
            COLUMN_UUID_USER +    " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_NAME_USER +    " TEXT, "+
            COLUMN_EMAIL + " TEXT);";

    private static final String TABLE_SETTINGS_CREATE = "CREATE TABLE "+ TABLE_SETTINGS + " ("+
            ");";

    private static final String TABLE_FAVORITES_CREATE = "CREATE TABLE " + TABLE_FAVORITES + " (" +
            COLUMN_USER_ID + " INTEGER, " +
            COLUMN_STATION_ID + " INTEGER, " +
            "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_STATION_ID + "), " +
            "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_UUID_USER + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_STATION_ID + ") REFERENCES " + TABLE_RADIO_STATION + "(" + COLUMN_UUID_STATION + ") ON DELETE CASCADE);";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_RADIO_STATION_CREATE);
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_SETTINGS_CREATE);
        db.execSQL(TABLE_FAVORITES);
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
                                String style, double latitude, double longitude, boolean isPlaying) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STATION, name);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_LOGO_URL, logoUrl);
        values.put(COLUMN_STREAM_URL, streamUrl);
        values.put(COLUMN_STYLE, style);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
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
}
