package by.roman.worldradio2.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "Radio.db";
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_RADIO_STATION = "radiostation";
    protected static final String COLUMN_UUID_STATION= "id";
    protected static final String COLUMN_NAME_STATION = "name";
    protected static final String COLUMN_LOGO_URL_STATION = "logoUrl";
    protected static final String COLUMN_STREAM_URL_STATION = "streamUrl";
    protected static final String COLUMN_COUNTRY_STATION = "country";
    protected static final String COLUMN_STYLE_STATION = "style";
    protected static final String COLUMN_LONGITUDE_STATION = "longitude";
    protected static final String COLUMN_LATITUDE_STATION = "latitude";
    protected static final String COLUMN_LIKES_STATION = "likes";
    protected static final String COLUMN_LANG_STATION = "lang";
    protected static final String COLUMN_ISPLAYING_STATION = "isPlaying";
    protected static final String CREATE_TABLE_RADIO_STATION = "CREATE TABLE "+ TABLE_RADIO_STATION + " ("+
            COLUMN_UUID_STATION +         " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NAME_STATION +         " TEXT, "+
            COLUMN_COUNTRY_STATION +      " TEXT, "+
            COLUMN_LOGO_URL_STATION +     " TEXT, "+
            COLUMN_STREAM_URL_STATION +   " TEXT, "+
            COLUMN_STYLE_STATION +        " TEXT, "+
            COLUMN_LATITUDE_STATION +     " REAL, "+
            COLUMN_LONGITUDE_STATION +    " REAL, "+
            COLUMN_LANG_STATION +         " TEXT, "+
            COLUMN_LIKES_STATION +        " INTEGER, "+
            COLUMN_ISPLAYING_STATION +    " INTEGER);";

    protected static final String TABLE_USER = "user";
    protected static final String COLUMN_UUID_USER = "id";
    protected static final String COLUMN_LOGIN_USER = "login";
    protected static final String COLUMN_PASSWORD_USER = "password";
    protected static final String COLUMN_IN_SYSTEM_USER = "in_system";

    protected static final String CREATE_TABLE_USER = "CREATE TABLE "+ TABLE_USER + " ("+
            COLUMN_UUID_USER +     " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_LOGIN_USER +    " TEXT, "+
            COLUMN_PASSWORD_USER + " TEXT, "+
            COLUMN_IN_SYSTEM_USER + " TEXT);";
    protected static final String TABLE_FILTER = "filter";
    protected static final String COLUMN_USER_ID_FILTER = "user_id_f";
    public static final String COLUMN_STYLE_FILTER = "style_f";
    public static final String COLUMN_COUNTRY_FILTER = "country_f";
    public static final String COLUMN_LANG_FILTER = "lang_f";
    protected static final String COLUMN_SORT_FILTER = "sort_f";
    protected static final String CREATE_TABLE_FILTER = "CREATE TABLE " + TABLE_FILTER + " (" +
            COLUMN_USER_ID_FILTER + " INTEGER, "+
            COLUMN_COUNTRY_FILTER + " TEXT, "+
            COLUMN_LANG_FILTER +    " TEXT, "+
            COLUMN_STYLE_FILTER +   " TEXT, "+
            COLUMN_SORT_FILTER +    " INTEGER);";
    protected static final String TABLE_SETTINGS = "settings";
    protected static final String COLUMN_USER_ID_SETTINGS = "id";
    protected static final String COLUMN_THEME_SETTINGS = "theme";
    protected static final String COLUMN_MAP_SETTINGS = "map";
    protected static final String COLUMN_TIMER_SECONDS_SETTINGS = "seconds";
    protected static final String COLUMN_TIMER_DOTS_SETTINGS = "dots";
    protected static final String COLUMN_FILTER_SETTINGS = "filter";

    protected static final String CREATE_TABLE_SETTINGS = "CREATE TABLE " + TABLE_SETTINGS + " (" +
            COLUMN_USER_ID_SETTINGS +       " INTEGER, " +
            COLUMN_THEME_SETTINGS +         " INTEGER, " +
            COLUMN_MAP_SETTINGS +           " INTEGER, " +
            COLUMN_TIMER_SECONDS_SETTINGS + " INTEGER, " +
            COLUMN_TIMER_DOTS_SETTINGS +    " INTEGER, " +
            COLUMN_FILTER_SETTINGS +        " INTEGER, " + // TODO: какая-то фигатень с формой записи
            "FOREIGN KEY (" + COLUMN_USER_ID_SETTINGS + ") REFERENCES " + TABLE_USER + "(" + COLUMN_UUID_USER + ") ON DELETE CASCADE"
            + ");";

    protected static final String TABLE_FAVORITE = "favorites";
    protected static final String COLUMN_USER_ID_FAVORITE = "user_id";
    protected static final String COLUMN_STATION_ID_FAVORITE = "station_id";
    protected static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + " (" +
            COLUMN_USER_ID_FAVORITE +    " INTEGER, " +
            COLUMN_STATION_ID_FAVORITE + " INTEGER, " +
            "PRIMARY KEY (" + COLUMN_USER_ID_FAVORITE + ", " + COLUMN_STATION_ID_FAVORITE + "), " +
            "FOREIGN KEY (" + COLUMN_USER_ID_FAVORITE + ") REFERENCES " + TABLE_USER + "(" + COLUMN_UUID_USER + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_STATION_ID_FAVORITE + ") REFERENCES " + TABLE_RADIO_STATION + "(" + COLUMN_UUID_STATION + ") ON DELETE CASCADE);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RADIO_STATION);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FILTER);
        db.execSQL(CREATE_TABLE_SETTINGS);
        db.execSQL(CREATE_TABLE_FAVORITE);
    }
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RADIO_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        onCreate(db);
    }
}
