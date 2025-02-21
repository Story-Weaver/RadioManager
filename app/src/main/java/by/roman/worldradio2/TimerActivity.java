package by.roman.worldradio2;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import by.roman.worldradio2.adapters.TimerWheelAdapter;
import by.roman.worldradio2.view.CircularTimerView;


public class TimerActivity extends AppCompatActivity {
    private RecyclerView recyclerHour, recyclerMinute, recyclerSecond;
    private TimerWheelAdapter hourAdapter, minuteAdapter, secondAdapter;
    private CircularTimerView circularTimerView;
    private ImageView pauseButton;
    private ImageView playButton;
    private ImageView startButton;
    private ImageView stopButton;
    private ImageView backButton;
    private CountDownTimer countDownTimer;
    private long totalTime; //
    private boolean isStart = false;
    private boolean isPaused = false; // Отслеживание состояния паузы
    private long timeRemaining; // Оставшееся время при паузе
    private boolean useSeconds = true;  //используем столбец с секундами

    private void blinkAnimation() {
        // Аниматор: от 1f до 0.3f и обратно
        ObjectAnimator animator = ObjectAnimator.ofFloat(circularTimerView, "alpha", 1f, 0.3f);
        animator.setDuration(300);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        circularTimerView = findViewById(R.id.circularTimerView);
        pauseButton = findViewById(R.id.pauseButtonView);
        playButton = findViewById(R.id.playButtonView);
        startButton = findViewById(R.id.startButtonView);
        backButton = findViewById(R.id.backButtonView);
        stopButton = findViewById(R.id.stopButtonView);
        recyclerHour = findViewById(R.id.recyclerHour);
        recyclerMinute = findViewById(R.id.recyclerMinute);
        recyclerSecond = findViewById(R.id.recyclerSecond);
        hourAdapter = new TimerWheelAdapter(this, 24);  // 24 часа
        minuteAdapter = new TimerWheelAdapter(this, 60); // 60 минут
        secondAdapter = new TimerWheelAdapter(this, 60); // 60 секунд (или тоже минуты, если нужно)
        setupRecyclerView(recyclerHour, hourAdapter);
        setupRecyclerView(recyclerMinute, minuteAdapter);
        setupRecyclerView(recyclerSecond, secondAdapter);
        hourAdapter.setSelectedPosition(hourAdapter.getItemCount() / 2);
        minuteAdapter.setSelectedPosition(minuteAdapter.getItemCount() / 2);
        secondAdapter.setSelectedPosition(secondAdapter.getItemCount() / 2); // На всякий случай, если секунды будут использоваться
        updateTotalTime();
        totalTime = (hourAdapter.getRealValue() * 3600000L) + (minuteAdapter.getRealValue() * 60000L);
        if (!useSeconds) {
            // Скрываем третью колонку и разделитель между 2 и 3
            recyclerSecond.setVisibility(View.GONE);
            findViewById(R.id.dotDivider2).setVisibility(View.GONE);
        }
        backButton.setOnClickListener(v ->{
            finish();
        });
        startButton.setOnClickListener(v ->{
            startTimer(totalTime);
            countDownTimer.start();
            blinkAnimation();
            startButton.setVisibility(INVISIBLE);
            pauseButton.setVisibility(VISIBLE);
            stopButton.setVisibility(VISIBLE);
            stopButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
            findViewById(R.id.setTime).setVisibility(INVISIBLE);
            isStart = true;
            animateMoveDown(circularTimerView);


        });
        pauseButton.setOnClickListener(v ->{
            pauseTimer();  // Приостановить таймер
            pauseButton.setVisibility(INVISIBLE);
            playButton.setVisibility(VISIBLE);
            stopButton.setEnabled(true);
            stopButton.setAlpha(1f);
            isPaused = true;
        });
        playButton.setOnClickListener(v ->{
            resumeTimer(); // Возобновить таймер
            playButton.setVisibility(INVISIBLE);
            pauseButton.setVisibility(VISIBLE);
            pauseButton.setImageDrawable(getDrawable(R.drawable.pausebutton_timer));
            stopButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
            isPaused = false;
        });
        stopButton.setOnClickListener(v->{
            countDownTimer.cancel(); // Останавливаем таймер
            circularTimerView.setCurrentTimeMillis(totalTime); // Сбрасываем таймер к исходному состоянию
            startButton.setVisibility(VISIBLE); // Показываем кнопку начала
            playButton.setVisibility(INVISIBLE);
            pauseButton.setVisibility(INVISIBLE); // Скрываем кнопку паузы/возобновления
            stopButton.setVisibility(INVISIBLE); // Скрываем кнопку остановки
            fadeInOnResume();
            findViewById(R.id.setTime).setVisibility(VISIBLE);
            isStart = false;
            isPaused = false;
            updateTotalTime();
            animateMoveUp(circularTimerView);
        });
    }
    private void startTimer(long time) {
        if (countDownTimer != null) {
            countDownTimer.cancel();  // Останавливаем предыдущий таймер
        }
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished; // Сохраняем оставшееся время
                circularTimerView.setCurrentTimeMillis(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                circularTimerView.setCurrentTimeMillis(0);
                // Можно добавить уведомление о завершении таймера
            }
        };
    }
    private void setupRecyclerView(RecyclerView recyclerView, TimerWheelAdapter adapter) {
        // Вертикальный список
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Подключаем адаптер
        recyclerView.setAdapter(adapter);

        // Подключаем SnapHelper для центрирования элемента
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        // Скроллим в середину списка после отрисовки
        recyclerView.post(() -> {
            int middlePosition = (adapter.getItemCount() / 2);
            recyclerView.scrollToPosition(middlePosition);
            adapter.setSelectedPosition(middlePosition);
        });

        // Отслеживаем остановку прокрутки, чтобы определить выбранный элемент
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView rv, int newState) {
                super.onScrollStateChanged(rv, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Получаем View, которое зацентрировано SnapHelper-ом
                    View centerView = snapHelper.findSnapView(layoutManager);
                    if (centerView != null) {
                        int position = layoutManager.getPosition(centerView);
                        adapter.setSelectedPosition(position);
                        updateTotalTime();
                    }
                }
            }
        });
    }
    private void updateTotalTime() {
        if(!isStart){
            int hours = hourAdapter.getSelectedPosition() % 24; // Учитываем количество элементов
            int minutes = minuteAdapter.getSelectedPosition() % 60;
            int seconds = secondAdapter.getSelectedPosition() % 60;
            totalTime = (hours * 3600000L) + (minutes * 60000L) + (seconds * 1000L);
            // Обновляем отображение таймера
            circularTimerView.setMaxTimeMillis(totalTime);
            circularTimerView.setCurrentTimeMillis(totalTime);
        }
    }
    private void pauseTimer() {
        isPaused = true;
        countDownTimer.cancel();
        fadeOutOnPause();
    }
    private void resumeTimer() {
        isPaused = false;
        startTimer(timeRemaining);  // Перезапускаем таймер с оставшимся временем
        countDownTimer.start();
        fadeInOnResume();
    }
    private void fadeOutOnPause() {
        ValueAnimator fadeOut = ValueAnimator.ofFloat(1f, 0.5f);
        fadeOut.setDuration(500);
        fadeOut.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
            circularTimerView.setAlpha(alpha);
        });
        fadeOut.start();
    }
    private void fadeInOnResume() {
        ValueAnimator fadeIn = ValueAnimator.ofFloat(0.5f, 1f);
        fadeIn.setDuration(500);
        fadeIn.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
            circularTimerView.setAlpha(alpha);
        });
        fadeIn.start();
    }
    private void animateMoveDown(View view) {
        // Анимация движения вниз (сначала начальная позиция, потом конечная)
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 70f, 270f);
        animator.setDuration(1250); // Устанавливаем продолжительность анимации
        animator.start(); // Запускаем анимацию
    }
    private void animateMoveUp(View view) {
        // Анимация движения вниз (сначала начальная позиция, потом конечная)
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 270f, 70f);
        animator.setDuration(1250); // Устанавливаем продолжительность анимации
        animator.start(); // Запускаем анимацию
    }
}

