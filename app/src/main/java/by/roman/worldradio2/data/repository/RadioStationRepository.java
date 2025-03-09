package by.roman.worldradio2.data.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import by.roman.worldradio2.data.dto.RadioStationDTO;
import by.roman.worldradio2.data.model.RadioStation;

public class RadioStationRepository {
    private final SQLiteDatabase db;

    public RadioStationRepository(SQLiteDatabase db) {
        this.db = db;
    }
    public long addRadioStation(@NonNull RadioStationDTO dto) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CHANGE_UUID_STATION, dto.getChangeUuid());
        values.put(DatabaseHelper.COLUMN_UUID_STATION, dto.getStationUuid());
        values.put(DatabaseHelper.COLUMN_NAME_STATION, dto.getName());
        values.put(DatabaseHelper.COLUMN_URL_STATION, dto.getUrl());
        values.put(DatabaseHelper.COLUMN_URL_RESOLVED_STATION, dto.getUrlResolved());
        values.put(DatabaseHelper.COLUMN_HOMEPAGE_STATION, dto.getHomepage());
        values.put(DatabaseHelper.COLUMN_FAVICON_STATION, dto.getFavicon());
        values.put(DatabaseHelper.COLUMN_TAGS_STATION, dto.getTags());
        values.put(DatabaseHelper.COLUMN_COUNTRY_STATION, dto.getCountry());
        values.put(DatabaseHelper.COLUMN_COUNTRY_CODE_STATION, dto.getCountryCode());
        values.put(DatabaseHelper.COLUMN_STATE_STATION, dto.getState());
        values.put(DatabaseHelper.COLUMN_iso_3166_2_STATION, dto.getIso31662());
        values.put(DatabaseHelper.COLUMN_LANGUAGE_STATION, dto.getLanguage());
        values.put(DatabaseHelper.COLUMN_LANGUAGE_CODE_STATION, dto.getLanguageCode());
        values.put(DatabaseHelper.COLUMN_VOTES_STATION, dto.getVotes());
        values.put(DatabaseHelper.COLUMN_LAST_CHANGE_TIME_STATION, dto.getLastChangeTime());
        values.put(DatabaseHelper.COLUMN_LAST_CHANGE_TIME_iso8601_STATION, dto.getLastChangeTimeIso8601());
        values.put(DatabaseHelper.COLUMN_CODEC_STATION, dto.getCodec());
        values.put(DatabaseHelper.COLUMN_BITRATE_STATION, dto.getBitrate());
        values.put(DatabaseHelper.COLUMN_HLS_STATION, dto.getHls());
        values.put(DatabaseHelper.COLUMN_LAST_CHECK_OK_STATION, dto.getLastCheckOk());
        values.put(DatabaseHelper.COLUMN_LAST_CHECK_TIME_STATION, dto.getLastCheckTime());
        values.put(DatabaseHelper.COLUMN_LAST_CHECK_TIME_iso8601_STATION, dto.getLastCheckTimeIso8601());
        values.put(DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_STATION, dto.getLastCheckOkTime());
        values.put(DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_iso8601_STATION, dto.getLastCheckOkTimeIso8601());
        values.put(DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_STATION, dto.getLastLocalCheckTime());
        values.put(DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_iso8601_STATION, dto.getLastLocalCheckTimeIso8601());
        values.put(DatabaseHelper.COLUMN_CLICK_TIME_STAMP_STATION, dto.getClickTimestamp());
        values.put(DatabaseHelper.COLUMN_CLICK_TIME_STAMP_iso8601_STATION, dto.getClickTimestampIso8601());
        values.put(DatabaseHelper.COLUMN_CLICK_COUNT_STATION, dto.getClickCount());
        values.put(DatabaseHelper.COLUMN_CLICK_TREND_STATION, dto.getClickTrend());
        values.put(DatabaseHelper.COLUMN_SSL_ERROR_STATION, dto.getSslError());
        values.put(DatabaseHelper.COLUMN_GEO_LATITUDE_STATION, dto.getGeoLat());
        values.put(DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION, dto.getGeoLong());
        values.put(DatabaseHelper.COLUMN_GEO_DISTANCE_STATION, dto.getGeoDistance());
        values.put(DatabaseHelper.COLUMN_HAS_EXTENDED_INFO_STATION, dto.getIsHasExtendedInfo());
        values.put(DatabaseHelper.COLUMN_ISPLAYING_STATION, dto.getIsPlaying());
        return db.insert(DatabaseHelper.TABLE_RADIO_STATION, null, values);
    }
    public List<RadioStation> getAllRadioStations() {
        List<RadioStation> radioStationsList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{
                        DatabaseHelper.COLUMN_CHANGE_UUID_STATION, DatabaseHelper.COLUMN_UUID_STATION, DatabaseHelper.COLUMN_NAME_STATION,
                        DatabaseHelper.COLUMN_URL_STATION, DatabaseHelper.COLUMN_URL_RESOLVED_STATION, DatabaseHelper.COLUMN_HOMEPAGE_STATION,
                        DatabaseHelper.COLUMN_FAVICON_STATION, DatabaseHelper.COLUMN_TAGS_STATION, DatabaseHelper.COLUMN_COUNTRY_STATION,
                        DatabaseHelper.COLUMN_COUNTRY_CODE_STATION, DatabaseHelper.COLUMN_STATE_STATION, DatabaseHelper.COLUMN_iso_3166_2_STATION,
                        DatabaseHelper.COLUMN_LANGUAGE_STATION, DatabaseHelper.COLUMN_LANGUAGE_CODE_STATION, DatabaseHelper.COLUMN_VOTES_STATION,
                        DatabaseHelper.COLUMN_LAST_CHANGE_TIME_STATION, DatabaseHelper.COLUMN_LAST_CHANGE_TIME_iso8601_STATION, DatabaseHelper.COLUMN_CODEC_STATION,
                        DatabaseHelper.COLUMN_BITRATE_STATION, DatabaseHelper.COLUMN_HLS_STATION, DatabaseHelper.COLUMN_LAST_CHECK_OK_STATION,
                        DatabaseHelper.COLUMN_LAST_CHECK_TIME_STATION, DatabaseHelper.COLUMN_LAST_CHECK_TIME_iso8601_STATION, DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_STATION,
                        DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_iso8601_STATION, DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_STATION, DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_iso8601_STATION,
                        DatabaseHelper.COLUMN_CLICK_TIME_STAMP_STATION, DatabaseHelper.COLUMN_CLICK_TIME_STAMP_iso8601_STATION, DatabaseHelper.COLUMN_CLICK_COUNT_STATION,
                        DatabaseHelper.COLUMN_CLICK_TREND_STATION, DatabaseHelper.COLUMN_SSL_ERROR_STATION, DatabaseHelper.COLUMN_GEO_LATITUDE_STATION,
                        DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION, DatabaseHelper.COLUMN_GEO_DISTANCE_STATION, DatabaseHelper.COLUMN_HAS_EXTENDED_INFO_STATION,
                        DatabaseHelper.COLUMN_ISPLAYING_STATION
                },
                null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
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
                    if(radioStationsList.size() <=100){ // TODO: убрать
                        radioStationsList.add(station);
                    }
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
        Set<String> uniqueTags = new HashSet<>();
        for (String tags : countryList) {
            if (tags != null && !tags.isEmpty()) {
                String[] splitTags = tags.split(",");
                for (String tag : splitTags) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        uniqueTags.add(trimmedTag);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueTags);
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
                int langIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_LANGUAGE_STATION);
                if (langIndex != -1) {
                    langList.add(cursor.getString(langIndex));
                }
            }
            cursor.close();
        }
        Set<String> uniqueTags = new HashSet<>();
        for (String tags : langList) {
            if (tags != null && !tags.isEmpty()) {
                String[] splitTags = tags.split(",");
                for (String tag : splitTags) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        uniqueTags.add(trimmedTag);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueTags);
    }
    public List<String> getTags(){
        List<String> tagsList = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_TAGS_STATION},
                null,
                null,
                null,
                null,
                null);
        if(cursor != null){
            while (cursor.moveToNext()) {
                int styleIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TAGS_STATION);
                if (styleIndex != -1) {
                    tagsList.add(cursor.getString(styleIndex));
                }
            }
            cursor.close();
        }
        Set<String> uniqueTags = new HashSet<>();
        for (String tags : tagsList) {
            if (tags != null && !tags.isEmpty()) {
                String[] splitTags = tags.split(",");
                for (String tag : splitTags) {
                    String trimmedTag = tag.trim();
                    if (!trimmedTag.isEmpty()) {
                        uniqueTags.add(trimmedTag);
                    }
                }
            }
        }
        return new ArrayList<>(uniqueTags);
    }
    public void setIsPlaying(String uuid, boolean isPlaying){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ISPLAYING_STATION, isPlaying ? 1 : 0);
        String selection = DatabaseHelper.COLUMN_UUID_STATION + " = ?";
        String[] selectionArgs = {uuid};
        db.update(DatabaseHelper.TABLE_RADIO_STATION, values, selection, selectionArgs);
    }
    public void removeIsPlaying(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ISPLAYING_STATION, 0);
        String selection = DatabaseHelper.COLUMN_ISPLAYING_STATION + " = ?";
        String[] selectionArgs = {"1"};
        db.update(DatabaseHelper.TABLE_RADIO_STATION, values, selection, selectionArgs);
    }
    public List<RadioStation> getRadioStationWithFilter(){
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String styleF = null;
        int sort = 0;
        String orderBy;
        Cursor cursor1 = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_USER_ID_FILTER, DatabaseHelper.COLUMN_COUNTRY_FILTER,
                             DatabaseHelper.COLUMN_LANG_FILTER, DatabaseHelper.COLUMN_TAGS_FILTER, DatabaseHelper.COLUMN_SORT_FILTER},
                null,
                null,
                null,
                null,
                null);
        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_FILTER);
            int langIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_LANG_FILTER);
            int styleIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_TAGS_FILTER);
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
            selection.append(DatabaseHelper.COLUMN_TAGS_STATION).append(" = ?");
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
        Cursor cursor = db.query(DatabaseHelper.TABLE_RADIO_STATION,
                new String[]{DatabaseHelper.COLUMN_CHANGE_UUID_STATION,DatabaseHelper.COLUMN_UUID_STATION,
                        DatabaseHelper.COLUMN_NAME_STATION,DatabaseHelper.COLUMN_URL_STATION,
                        DatabaseHelper.COLUMN_URL_RESOLVED_STATION,DatabaseHelper.COLUMN_HOMEPAGE_STATION,
                        DatabaseHelper.COLUMN_FAVICON_STATION,DatabaseHelper.COLUMN_TAGS_STATION,
                        DatabaseHelper.COLUMN_COUNTRY_STATION,DatabaseHelper.COLUMN_COUNTRY_CODE_STATION,
                        DatabaseHelper.COLUMN_STATE_STATION,DatabaseHelper.COLUMN_iso_3166_2_STATION,
                        DatabaseHelper.COLUMN_LANGUAGE_STATION,DatabaseHelper.COLUMN_LANGUAGE_CODE_STATION,
                        DatabaseHelper.COLUMN_VOTES_STATION,DatabaseHelper.COLUMN_LAST_CHANGE_TIME_STATION,
                        DatabaseHelper.COLUMN_LAST_CHANGE_TIME_iso8601_STATION,DatabaseHelper.COLUMN_CODEC_STATION,
                        DatabaseHelper.COLUMN_BITRATE_STATION,DatabaseHelper.COLUMN_HLS_STATION,
                        DatabaseHelper.COLUMN_LAST_CHECK_OK_STATION,DatabaseHelper.COLUMN_LAST_CHECK_TIME_STATION,
                        DatabaseHelper.COLUMN_LAST_CHECK_TIME_iso8601_STATION,DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_STATION,
                        DatabaseHelper.COLUMN_LAST_CHECK_OK_TIME_iso8601_STATION,DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_STATION,
                        DatabaseHelper.COLUMN_LAST_LOCAL_CHECK_TIME_iso8601_STATION,DatabaseHelper.COLUMN_CLICK_TIME_STAMP_STATION,
                        DatabaseHelper.COLUMN_CLICK_TIME_STAMP_iso8601_STATION,DatabaseHelper.COLUMN_CLICK_COUNT_STATION,
                        DatabaseHelper.COLUMN_CLICK_TREND_STATION,DatabaseHelper.COLUMN_SSL_ERROR_STATION,DatabaseHelper.COLUMN_GEO_LATITUDE_STATION,
                        DatabaseHelper.COLUMN_GEO_LONGITUDE_STATION,DatabaseHelper.COLUMN_GEO_DISTANCE_STATION,
                        DatabaseHelper.COLUMN_HAS_EXTENDED_INFO_STATION,DatabaseHelper.COLUMN_ISPLAYING_STATION},
                selection.length() > 0 ? selection.toString() : null,
                selectionArgsList.isEmpty() ? null : selectionArgsList.toArray(new String[0]),
                null,
                null,
                orderBy);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int changeUuidIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CHANGE_UUID_STATION);
                int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_UUID_STATION);
                int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME_STATION);
                int urlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL_STATION);
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
                        urlIndex != -1 && urlResolvedIndex != -1 && homepageIndex != -1 && tagsIndex != -1 &&
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
                    String url = cursor.getString(urlIndex);
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
                            changeUuid, id, name, url, urlResolved, homepage, logoUrl, tags,
                            country, countryCode, iso31662, state, lang, langCode, likes,
                            lastChangeTime, lastChangeTimeIso, codec, bitrate,
                            hls, lastCheckOk, lastCheckTime,
                            lastCheckTimeIso, lastCheckOkTime, lastCheckOkTimeIso,
                            lastLocalCheckTime, lastLocalCheckTimeIso,
                            clickTimestamp, clickTimestampIso, clickCount,
                            clickTrend, sslError, latitude, longitude, geoDistance,
                            hasExtendedInfo, isPlaying
                    );
                    if(radioStationList.size() <= 100){
                        radioStationList.add(station);
                    } else {
                        cursor.close();
                        return radioStationList;
                    }
                }
            }

        }
        return radioStationList;
    } // TODO: теги
    public int getRadioStationCountWithFilter() {
        StringBuilder selection = new StringBuilder();
        List<String> selectionArgsList = new ArrayList<>();
        String countryF = null;
        String langF = null;
        String tagsF = null;

        Cursor cursor1 = db.query(DatabaseHelper.TABLE_FILTER,
                new String[]{DatabaseHelper.COLUMN_USER_ID_FILTER, DatabaseHelper.COLUMN_COUNTRY_FILTER,
                             DatabaseHelper.COLUMN_LANG_FILTER, DatabaseHelper.COLUMN_TAGS_FILTER, DatabaseHelper.COLUMN_SORT_FILTER},
                null,
                null,
                null,
                null,
                null);

        if (cursor1 != null && cursor1.moveToFirst()) {
            int countryIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY_FILTER);
            int langIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_LANG_FILTER);
            int tagsIndex = cursor1.getColumnIndex(DatabaseHelper.COLUMN_TAGS_FILTER);

            if (countryIndex != -1) countryF = cursor1.getString(countryIndex);
            if (langIndex != -1) langF = cursor1.getString(langIndex);
            if (tagsIndex != -1) tagsF = cursor1.getString(tagsIndex);
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
        if (tagsF != null) {
            if (selection.length() > 0) selection.append(" AND ");
            selection.append(DatabaseHelper.COLUMN_TAGS_STATION).append(" = ?");
            selectionArgsList.add(tagsF);
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
    } // TODO: теги
    public String getActiveStationUrl() {
        String streamUrl = null;
        String query = "SELECT " + DatabaseHelper.COLUMN_URL_STATION + " FROM " + DatabaseHelper.TABLE_RADIO_STATION
                + " WHERE " + DatabaseHelper.COLUMN_ISPLAYING_STATION + " = 1 LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            streamUrl = cursor.getString(0);
        }
        cursor.close();
        return streamUrl;
    }
}

