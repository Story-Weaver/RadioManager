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
    private final SQLiteDatabase db;
   public FavoriteRepository(SQLiteDatabase db) {this.db = db;}
   public long addFavorite(int userId, String stationUUID) {
       ContentValues values = new ContentValues();
       values.put(DatabaseHelper.COLUMN_USER_ID_FAVORITE, userId);
       values.put(DatabaseHelper.COLUMN_STATION_ID_FAVORITE, stationUUID);
       return db.insert(DatabaseHelper.TABLE_FAVORITE, null, values);
   }
   public int removeFavorite(int userid,String stationUUID) {
       String selection = DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ? AND " +
                   DatabaseHelper.COLUMN_STATION_ID_FAVORITE + " = ?";
       String[] selectionArgs = {String.valueOf(userid), String.valueOf(stationUUID)};
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
                   String uuid = cursor.getString(idIndex);
                   RadioStation radioStation = getRadioStationById(uuid);
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
    private RadioStation getRadioStationById(String stationUUID) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                null,
                DatabaseHelper.COLUMN_UUID_STATION + " = ?",
                new String[]{String.valueOf(stationUUID)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_UUID_STATION);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_STATION);
            int streamUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_STATION);
            int urlResolvedIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_RESOLVED_STATION);
            int homepageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HOMEPAGE_STATION);
            int logoUrlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_FAVICON_STATION);
            int tagsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TAGS_STATION);
            int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_STATION);
            int countryCodeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_CODE_STATION);
            int stateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_STATE_STATION);
            int langIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
            int langCodeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_CODE_STATION);
            int likesIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOTES_STATION);
            int codecIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CODEC_STATION);
            int bitrateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BITRATE_STATION);
            int hlsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HLS_STATION);
            int latIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION);
            int longIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION);
            int playingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ISPLAYING_STATION);
            if (idIndex != -1 && nameIndex != -1 && countryIndex != -1 && logoUrlIndex != -1 &&
                    streamUrlIndex != -1 && urlResolvedIndex != -1 && homepageIndex != -1 && tagsIndex != -1 &&
                    countryCodeIndex != -1 && stateIndex != -1 && langIndex != -1 &&
                    langCodeIndex != -1 && likesIndex != -1 && codecIndex != -1 && bitrateIndex != -1 &&
                    hlsIndex != -1 && latIndex != -1 && longIndex != -1 && playingIndex != -1) {

                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String streamUrl = cursor.getString(streamUrlIndex);
                String urlResolved = cursor.getString(urlResolvedIndex);
                String homepage = cursor.getString(homepageIndex);
                String logoUrl = cursor.getString(logoUrlIndex);
                String tags = cursor.getString(tagsIndex);
                String country = cursor.getString(countryIndex);
                String countryCode = cursor.getString(countryCodeIndex);
                String state = cursor.getString(stateIndex);
                String lang = cursor.getString(langIndex);
                String langCode = cursor.getString(langCodeIndex);
                int likes = cursor.getInt(likesIndex);
                String codec = cursor.getString(codecIndex);
                int bitrate = cursor.getInt(bitrateIndex);
                int hls = cursor.getInt(hlsIndex);

                double latitude = cursor.getDouble(latIndex);
                double longitude = cursor.getDouble(longIndex);
                int isPlaying = cursor.getInt(playingIndex);

                RadioStation station = new RadioStation(
                        id, name, streamUrl, urlResolved, homepage, logoUrl, tags,
                        country, countryCode, state, lang, langCode, likes,
                        codec, bitrate,
                        hls,  latitude, longitude, isPlaying
                );
                return station;
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    public boolean isStationFavorite(int userId,String stationUuid) {
        boolean isFavorite = false;
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_FAVORITE,
                new String[]{DatabaseHelper.COLUMN_STATION_ID_FAVORITE},
                DatabaseHelper.COLUMN_STATION_ID_FAVORITE + " = ? AND " +
                        DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ?",
                new String[]{stationUuid, String.valueOf(userId)},
                null, null, null
        );
        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
        return isFavorite;
    }


}

