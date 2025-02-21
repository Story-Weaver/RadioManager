package by.roman.worldradio2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TimerService extends Service {
    private Handler handler = new Handler();
    private Runnable runnable;
    private long timeInMillis = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeInMillis = intent.getLongExtra("time", 0); // Получаем время из Intent

        runnable = new Runnable() {
            @Override
            public void run() {
                if (timeInMillis > 0) {
                    timeInMillis -= 60000; // Уменьшаем на 1 минуту
                    Toast.makeText(getApplicationContext(), "Осталось времени: " + (timeInMillis / 60000) + " минут", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 60000); // Повтор через минуту
                } else {
                    stopSelf(); // Остановить сервис, когда таймер закончится
                    Intent intent = new Intent("by.roman.worldradio2.TIMER_FINISHED");
                    intent.putExtra("time", 0); // Можно передать информацию о завершении
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent); // Отправка локального сообщения о завершении

                }
            }
        };
        handler.post(runnable);

        return START_STICKY; // Перезапускать сервис, если система его остановит
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable); // Остановить таймер при уничтожении сервиса
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
