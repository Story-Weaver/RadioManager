package by.roman.worldradio2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.RadioStationRepository;

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

    public static synchronized RadioService getInstance(Context context) {
        if (instance == null) {
            instance = new RadioService(context);
        }
        return instance;
    }
    private RadioService(Context context) {
        this.context = context.getApplicationContext();
        this.radioManager = new RadioManager(context);
        this.dbHelper = new DatabaseHelper(context);
        this.handler = new Handler(Looper.getMainLooper());
        db = dbHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
    }

    public void startMonitoring() {
        if (!isMonitoring) {
            isMonitoring = true;
            handler.post(checkDatabaseRunnable);
        }
    }

    public void stopMonitoring() {
        isMonitoring = false;
        handler.removeCallbacks(checkDatabaseRunnable);
        radioManager.stop();
    }

    public void checkNow() {
        handler.post(checkDatabaseRunnable);
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
            }
        }
    };
}
