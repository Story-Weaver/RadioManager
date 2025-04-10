package by.roman.worldradio2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.media3.common.util.UnstableApi;

import java.lang.ref.WeakReference;

import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.ui.activities.MainActivity;

@UnstableApi
public class RadioService {
    private final Context context;
    private final RadioManager radioManager;
    private final DatabaseHelper dbHelper;
    private final Handler handler;
    private String currentStreamUrl = null;
    private final SQLiteDatabase db;
    private final RadioStationRepository radioStationRepository;
    private static RadioService instance;
    private boolean isMonitoring = false;
    private WeakReference<MainActivity> mainActivityRef;

    public static synchronized RadioService getInstance(Context context, MainActivity activity) {
        if (instance == null) {
            instance = new RadioService(context, activity);
        }
        return instance;
    }

    private RadioService(Context context, MainActivity activity) {
        this.context = context.getApplicationContext();
        this.mainActivityRef = new WeakReference<>(activity);  // Используем WeakReference, чтобы избежать утечек памяти
        this.radioManager = RadioManager.getInstance(context);
        this.dbHelper = new DatabaseHelper(context);
        this.handler = new Handler(Looper.getMainLooper());
        this.db = dbHelper.getWritableDatabase();
        this.radioStationRepository = new RadioStationRepository(db);
    }

    private void updateUI() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (radioManager.isPlaying()) {
                    MainActivity mainActivity = mainActivityRef.get();
                    if (mainActivity != null) {
                        mainActivity.showBottomPlayerFragment(radioStationRepository.getActiveStation());
                    }
                } else {
                    handler.postDelayed(this, 500);
                }
            }
        }, 500);
    }

    public void startMonitoring() {
        if (!isMonitoring) {
            isMonitoring = true;
            handler.post(checkDatabaseRunnable);
        }
    }
    public boolean statusPlaying(){
        return radioManager.isPlaying();
    }
    public void checkNow() {
        handler.post(checkDatabaseRunnable);
    }
    public void play(){
        radioManager.play(currentStreamUrl);
    }
    public void pause(){
        radioManager.stop();
    }
    public String getCurrentTrack() {
        if (radioManager != null) {
            return radioManager.getCurrentTrack();
        }
        return null;
    }
    private final Runnable checkDatabaseRunnable = new Runnable() {
        @Override
        public void run() {
            String newStreamUrl = radioStationRepository.getActiveStationUrl();

            if (newStreamUrl != null && !newStreamUrl.equals(currentStreamUrl)) {
                Log.d("RadioService", "Новая станция: " + newStreamUrl);
                currentStreamUrl = newStreamUrl;
                radioManager.stop();
                radioManager.play(currentStreamUrl);
                updateUI();
            } else if(newStreamUrl == null){
                radioManager.stop();
                MainActivity mainActivity = mainActivityRef.get();
                if (mainActivity != null) {
                    mainActivity.hideBottomPlayerFragment();
                }
                currentStreamUrl = null;
            }
        }
    };
}
