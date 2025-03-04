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
       values.put(DatabaseHelper.COLUMN_STATION_ID_FAVORITE, dto.getStationUUID());
       return db.insert(DatabaseHelper.TABLE_FAVORITE, null, values);
   }
   public int removeFavorite(@NonNull FavoriteDTO dto) {
       String selection = DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ? AND " +
                   DatabaseHelper.COLUMN_STATION_ID_FAVORITE + " = ?";
       String[] selectionArgs = {String.valueOf(dto.getUserId()), String.valueOf(dto.getStationUUID())};
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
            int changeUuidIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHANGE_UUID_STATION);
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
            int iso31662Index = cursor.getColumnIndex(DatabaseHelper.COLUMN_iso_3166_2_STATION);
            int langIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
            int langCodeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_CODE_STATION);
            int likesIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_VOTES_STATION);
            int lastChangeTimeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHANGE_TIME_STATION);
            int lastChangeTimeIsoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHANGE_TIME_iso8601_STATION);
            int codecIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CODEC_STATION);
            int bitrateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_BITRATE_STATION);
            int hlsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HLS_STATION);
            int lastCheckOkIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHECK_OK_STATION);
            int lastCheckTimeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHECK_TIME_STATION);
            int lastCheckTimeIsoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHECK_TIME_iso8601_STATION);
            int lastCheckOkTimeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_STATION);
            int lastCheckOkTimeIsoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_iso8601_STATION);
            int lastLocalCheckTimeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_STATION);
            int lastLocalCheckTimeIsoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_iso8601_STATION);
            int clickTimestampIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLICK_TIME_STAMP_STATION);
            int clickTimestampIsoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLICK_TIME_STAMP_iso8601_STATION);
            int clickCountIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLICK_COUNT_STATION);
            int clickTrendIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CLICK_TREND_STATION);
            int sslErrorIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_SSL_ERROR_STATION);
            int latIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION);
            int longIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION);
            int geoDistanceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_GEO_DISTANCE_STATION);
            int hasExtendedInfoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_HAS_EXTENDED_INFO_STATION);
            int playingIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ISPLAYING_STATION);
            if (idIndex != -1 && nameIndex != -1 && countryIndex != -1 && logoUrlIndex != -1 &&
                    streamUrlIndex != -1 && urlResolvedIndex != -1 && homepageIndex != -1 && tagsIndex != -1 &&
                    countryCodeIndex != -1 && stateIndex != -1 && iso31662Index != -1 && langIndex != -1 &&
                    langCodeIndex != -1 && likesIndex != -1 && lastChangeTimeIndex != -1 && lastChangeTimeIsoIndex != -1 &&
                    codecIndex != -1 && bitrateIndex != -1 && hlsIndex != -1 && lastCheckOkIndex != -1 &&
                    lastCheckTimeIndex != -1 && lastCheckTimeIsoIndex != -1 && lastCheckOkTimeIndex != -1 &&
                    lastCheckOkTimeIsoIndex != -1 && lastLocalCheckTimeIndex != -1 && lastLocalCheckTimeIsoIndex != -1 &&
                    clickTimestampIndex != -1 && clickTimestampIsoIndex != -1 && clickCountIndex != -1 &&
                    clickTrendIndex != -1 && sslErrorIndex != -1 && latIndex != -1 && longIndex != -1 &&
                    geoDistanceIndex != -1 && hasExtendedInfoIndex != -1 && playingIndex != -1) {

                String changeUuid = cursor.getString(changeUuidIndex);
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
                String iso31662 = cursor.getString(iso31662Index);
                String lang = cursor.getString(langIndex);
                String langCode = cursor.getString(langCodeIndex);
                int likes = cursor.getInt(likesIndex);
                String lastChangeTime = cursor.getString(lastChangeTimeIndex);
                String lastChangeTimeIso = cursor.getString(lastChangeTimeIsoIndex);
                String codec = cursor.getString(codecIndex);
                int bitrate = cursor.getInt(bitrateIndex);
                int hls = cursor.getInt(hlsIndex);
                int lastCheckOk = cursor.getInt(lastCheckOkIndex);
                String lastCheckTime = cursor.getString(lastCheckTimeIndex);
                String lastCheckTimeIso = cursor.getString(lastCheckTimeIsoIndex);
                String lastCheckOkTime = cursor.getString(lastCheckOkTimeIndex);
                String lastCheckOkTimeIso = cursor.getString(lastCheckOkTimeIsoIndex);
                String lastLocalCheckTime = cursor.getString(lastLocalCheckTimeIndex);
                String lastLocalCheckTimeIso = cursor.getString(lastLocalCheckTimeIsoIndex);
                String clickTimestamp = cursor.getString(clickTimestampIndex);
                String clickTimestampIso = cursor.getString(clickTimestampIsoIndex);
                int clickCount = cursor.getInt(clickCountIndex);
                int clickTrend = cursor.getInt(clickTrendIndex);
                int sslError = cursor.getInt(sslErrorIndex);
                double latitude = cursor.getDouble(latIndex);
                double longitude = cursor.getDouble(longIndex);
                double geoDistance = cursor.getDouble(geoDistanceIndex);
                boolean hasExtendedInfo = cursor.getInt(hasExtendedInfoIndex) == 1;
                int isPlaying = cursor.getInt(playingIndex);

                RadioStation station = new RadioStation(
                        changeUuid, id, name, streamUrl, urlResolved, homepage, logoUrl, tags,
                        country, countryCode, iso31662, state, lang, langCode, likes,
                        lastChangeTime, lastChangeTimeIso, codec, bitrate,
                        hls, lastCheckOk, lastCheckTime,
                        lastCheckTimeIso, lastCheckOkTime, lastCheckOkTimeIso,
                        lastLocalCheckTime, lastLocalCheckTimeIso,
                        clickTimestamp, clickTimestampIso, clickCount,
                        clickTrend, sslError, latitude, longitude, geoDistance,
                        hasExtendedInfo, isPlaying
                );
                return station;
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
    public void removeAllFavorites(int userId) {
        db.delete(DatabaseHelper.TABLE_FAVORITE,
                DatabaseHelper.COLUMN_USER_ID_FAVORITE + " = ?",
                new String[]{String.valueOf(userId)});
    }

}

