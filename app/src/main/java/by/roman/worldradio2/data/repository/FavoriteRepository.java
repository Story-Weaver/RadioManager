package by.roman.worldradio2.data.repository;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import by.roman.worldradio2.data.dto.FavoriteDTO;
import by.roman.worldradio2.data.model.RadioStation;
import lombok.NonNull;

public class FavoriteRepository {
    private SQLiteDatabase db;
   public FavoriteRepository(SQLiteDatabase db) {this.db = db;}
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
   public List<RadioStation> getFavoriteStationByUser(int userId){
       List<RadioStation> listFavoriteStation = new ArrayList<>();
       Cursor cursor = db.query(DatabaseHelper.TABLE_FAVORITE,
               new String[]{DatabaseHelper.COLUMN_USER_ID_FAVORITE, DatabaseHelper.COLUMN_STATION_ID_FAVORITE},
               DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ?",
               new String[]{String.valueOf(userId)},
               null,
               null,
               null);
       if(cursor != null){
           while (cursor.moveToNext()) {
               int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STATION_ID_FAVORITE);
               if (idIndex != -1 ) {
                   int id = cursor.getInt(idIndex);
                   RadioStation radioStation = getRadioStationById(id);
                   if(radioStation != null){
                       listFavoriteStation.add(radioStation);
                   }
               }
           }
           cursor.close();
       }
       return listFavoriteStation;
   }
    @Nullable
    private RadioStation getRadioStationById(int stationId) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                null,
                DatabaseHelper.COLUMN_UUID_STATION + " = ?",
                new String[]{String.valueOf(stationId)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_UUID_STATION);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_STATION);
            int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_STATION);
            int logoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LOGO_URL_STATION);
            int streamIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STREAM_URL_STATION);
            int styleIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STYLE_STATION);
            int latIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE_STATION);
            int longIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE_STATION);
            int langIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANG_STATION);
            int likesIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LIKES_STATION);
            int isPlayingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ISPLAYING_STATION);
            if (idIndex != -1 && nameIndex != -1 && countryIndex != -1 &&
                    logoIndex != -1 && streamIndex != -1 && styleIndex != -1 &&
                    latIndex != -1 && longIndex != -1 && langIndex != -1 &&
                    likesIndex != -1 && isPlayingIndex != -1) {
                RadioStation station = new RadioStation(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(countryIndex),
                        cursor.getString(logoIndex),
                        cursor.getString(streamIndex),
                        cursor.getString(styleIndex),
                        cursor.getDouble(latIndex),
                        cursor.getDouble(longIndex),
                        cursor.getString(langIndex),
                        cursor.getInt(likesIndex),
                        cursor.getInt(isPlayingIndex) == 1
                );
                cursor.close();
                return station;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }
    public void removeAllFavorites(int userId) {
        db.delete(DatabaseHelper.TABLE_FAVORITE,
                DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ?",
                new String[]{String.valueOf(userId)});
    }

}
