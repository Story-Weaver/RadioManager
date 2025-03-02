package by.roman.worldradio2.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DatabaseHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "Radio.db";
    protected static final int DATABASE_VERSION = 1;
    protected static final String TABLE_RADIO_STATION = "radiostation";
    protected static final String COLUMN_CHANGEUUID_STATION = "changeUUID";
    protected static final String COLUMN_UUID_STATION= "stationUUID";
    protected static final String COLUMN_NAME_STATION = "name";
    protected static final String COLUMN_URL_STATION = "url";
    protected static final String COLUMN_URL_RESOLVED_STATION = "url_resolved";
    protected static final String COLUMN_HOMEPAGE_STATION = "homepage";
    protected static final String COLUMN_FAVICON_STATION = "favicon";
    protected static final String COLUMN_TAGS_STATION = "tags";
    protected static final String COLUMN_COUNTRY_STATION = "country";
    protected static final String COLUMN_COUNTRY_CODE_STATION = "countryCode";
    protected static final String COLUMN_STATE_STATION = "state";
    protected static final String COLUMN_iso_3166_2_STATION = "iso_3166_2";
    protected static final String COLUMN_LANGUAGE_STATION = "language";
    protected static final String COLUMN_LANGUAGE_CODES_STATION = "languageCodes";
    protected static final String COLUMN_VOTES_STATION = "votes";
    protected static final String COLUMN_LAST_CHANGE_TIME_STATION = "lastChangeTime";
    protected static final String COLUMN_LAST_CHANGE_TIME_iso8601_STATION = "lastChangeTime_iso8601";
    protected static final String COLUMN_CODEC_STATION = "codec";
    protected static final String COLUMN_BITRATE_STATION = "bitrate";
    protected static final String COLUMN_HLS_STATION = "hls";
    protected static final String COLUMN_LAST_CHECK_OK_STATION = "lastCheckOk";
    protected static final String COLUMN_LAST_CHECK_TIME_STATION = "lastCheckTime";
    protected static final String COLUMN_LAST_CHECK_TIME_iso8601_STATION = "lastCheckTime_iso8601";
    protected static final String COLUMN_LAST_CHECK_OK_TIME_STATION = "lastCheckOkTime";
    protected static final String COLUMN_LAST_CHECK_OK_TIME_iso8601_STATION = "lastCheckOkTime_iso8601";
    protected static final String COLUMN_LAST_LOCAL_CHECK_TIME_STATION = "lastLocalCheckTime";
    protected static final String COLUMN_LAST_LOCAL_CHECK_TIME_iso8601_STATION = "lastLocalCheckTime_iso8601";
    protected static final String COLUMN_CLICK_TIME_STAMP_STATION = "clickTimeStamp";
    protected static final String COLUMN_CLICK_TIME_STAMP_iso8601_STATION = "clickTimeStamp_iso8601";
    protected static final String COLUMN_CLICK_COUNT_STATION = "clickCount";
    protected static final String COLUMN_CLICK_TREND_STATION = "clickTrend";
    protected static final String COLUMN_SSL_ERROR_STATION = "ssl_error";
    protected static final String COLUMN_GEO_LATITUDE_STATION = "geo_lat";
    protected static final String COLUMN_GEO_LONGITUDE_STATION = "geo_long";
    protected static final String COLUMN_GEO_DISTANCE_STATION = "geo_distance";
    protected static final String COLUMN_HAS_EXTENDED_INFO_STATION = "has_extended_info";
    protected static final String COLUMN_STYLE_STATION = "style";// TODO remove to tags




    protected static final String COLUMN_ISPLAYING_STATION = "isPlaying";
    protected static final String CREATE_TABLE_RADIO_STATION = "CREATE TABLE "+ TABLE_RADIO_STATION + " ("+
            COLUMN_UUID_STATION +         " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_NAME_STATION +         " TEXT, "+
            COLUMN_COUNTRY_STATION +      " TEXT, "+
            COLUMN_FAVICON_STATION +     " TEXT, "+
            COLUMN_URL_STATION +   " TEXT, "+
            COLUMN_STYLE_STATION +        " TEXT, "+
            COLUMN_GEO_LATITUDE_STATION +     " REAL, "+
            COLUMN_GEO_LONGITUDE_STATION +    " REAL, "+
            COLUMN_LANGUAGE_STATION +         " TEXT, "+
            COLUMN_VOTES_STATION +        " INTEGER, "+
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
