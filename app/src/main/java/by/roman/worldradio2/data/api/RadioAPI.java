package by.roman.worldradio2.data.api;

import android.util.Log;

import by.roman.worldradio2.data.model.RadioStation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class RadioAPI {

    private static final String API_URL = "http://at1.api.radio-browser.info/json/stations";

    public static void fetchStations(final StationsCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponse = response.body().string();
                        Log.d("RadioAPI", "Response: " + jsonResponse);

                        if(response.code() != 200){
                            Log.e("RadioAPI","Response code isn't 200");
                            return;
                        }
                        if (jsonResponse.isEmpty()) {
                            Log.e("RadioAPI", "Empty response body.");
                            callback.onError(new Exception("Empty response body"));
                            return;
                        }

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(new TypeToken<List<String>>(){}.getType(), new TagsAdapter())
                                .create();

                        List<Model> stationList = new ArrayList<>();

                        try {
                            if (jsonResponse.startsWith("[")) {
                                Model[] stations = gson.fromJson(jsonResponse, Model[].class);
                                stationList.addAll(Arrays.asList(stations));
                                callback.onStationsFetched(stationList);
                            } else {
                                Log.e("RadioAPI", "Unexpected response: " + jsonResponse);
                                callback.onError(new Exception("Unexpected response: " + jsonResponse));
                            }
                        } catch (JsonSyntaxException e) {
                            Log.e("RadioAPI", "JSON parsing error", e);
                            callback.onError(e);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError(e);
                    } finally {
                        response.close();
                    }
                } else {
                    Log.e("RadioAPI", "Request failed with code: " + response.code());
                    callback.onError(new Exception("Request failed with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                callback.onError(e);
            }
        });
    }
}
