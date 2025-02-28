package by.roman.worldradio2.data.repository;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.data.dto.FavoriteDTO;
import by.roman.worldradio2.data.model.RadioStation;
import lombok.NonNull;

public class FavoriteRepository {
    private SQLiteDatabase db;
   public FavoriteRepository(SQLiteDatabase db) {
        this.db = db;
    }
       public long addFavorite(FavoriteDTO dto) {
           ContentValues values = new ContentValues();
           values.put(DatabaseHelper.COLUMN_USER_ID_FAVORITE, dto.getUserId());
           values.put(DatabaseHelper.COLUMN_STATION_ID_FAVORITE, dto.getStationId());
           return db.insert(DatabaseHelper.TABLE_FAVORITE, null, values);
       }
       public int removeFavorite(@NonNull FavoriteDTO dto) {
           String selection = DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ? AND " +
                   DatabaseHelper.COLUMN_STATION_ID_FAVORITE + " = ?";
           String[] selectionArgs = {String.valueOf(dto.getUserId()), String.valueOf(dto.getStationId())};
           return db.delete(DatabaseHelper.TABLE_FAVORITE, selection, selectionArgs);
       }
       public List<RadioStation> getFavoritesByUser(int userId) {
           List<RadioStation> favoriteStations = new ArrayList<>();
           String query = "SELECT rs.* FROM " + DatabaseHelper.TABLE_RADIO_STATION + " rs " +
                   "INNER JOIN " + DatabaseHelper.TABLE_FAVORITE + " f " +
                   "ON rs." + DatabaseHelper.COLUMN_UUID_STATION + " = f." + DatabaseHelper.COLUMN_STATION_ID_FAVORITE +
                   " WHERE f." + DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ?";

           try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
               while (cursor.moveToNext()) {
                   int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_UUID_STATION));
                   String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_STATION));
                   String country = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNTRY_STATION));
                   String logoUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOGO_URL_STATION));
                   String streamUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STREAM_URL_STATION));
                   String style = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STYLE_STATION));
                   double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LATITUDE_STATION));
                   double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LONGITUDE_STATION));
                   String lang = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LANG_STATION));
                   int likes = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LIKES_STATION));
                   boolean isPlaying = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ISPLAYING_STATION)) == 1;

                   favoriteStations.add(new RadioStation(id, logoUrl, name, streamUrl, country, style, latitude, longitude, lang, likes, isPlaying));
               }
           }
           return favoriteStations;
       }
}
