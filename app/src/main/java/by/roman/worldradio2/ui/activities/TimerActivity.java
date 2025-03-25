package by.roman.worldradio2.ui.activities;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import by.roman.worldradio2.R;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.SettingsRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.adapters.TimerWheelAdapter;
import by.roman.worldradio2.ui.elements.view.CircularTimerView;



public class TimerActivity extends AppCompatActivity {
    private RecyclerView recyclerHour, recyclerMinute, recyclerSecond;
    private TimerWheelAdapter hourAdapter, minuteAdapter, secondAdapter;
    private CircularTimerView circularTimerView;
    private CountDownTimer countDownTimer;
    private UserRepository userRepository;
    private SettingsRepository settingsRepository;

    private ImageView pauseButton;
    private ImageView playButton;
    private ImageView startButton;
    private ImageView stopButton;
    private ImageView backButton;
    private ImageView divider1;
    private ImageView divider2;

    private long totalTime;
    private boolean isStart = false;
    private long timeRemaining;

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
        findAllId();
        initDatabase();
        adapterInit();
        updateTotalTime();
        backButton.setOnClickListener(v ->{finish();});
        startButton.setOnClickListener(v ->{
            startTimer(totalTime);
            countDownTimer.start();
            startVisible();
            stopButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
            isStart = true;
            blinkAnimation();
            animateMoveDown(circularTimerView);
        });
        pauseButton.setOnClickListener(v ->{
            pauseTimer();
            pauseButton.setVisibility(INVISIBLE);
            playButton.setVisibility(VISIBLE);
            stopButton.setEnabled(true);
            stopButton.setAlpha(1f);
        });
        playButton.setOnClickListener(v ->{
            resumeTimer();
            playButton.setVisibility(INVISIBLE);
            pauseButton.setVisibility(VISIBLE);
            pauseButton.setImageDrawable(getDrawable(R.drawable.pausebutton_timer));
            stopButton.setEnabled(false);
            stopButton.setAlpha(0.5f);
        });
        stopButton.setOnClickListener(v->{
            countDownTimer.cancel();
            circularTimerView.setCurrentTimeMillis(totalTime);
            isStart = false;
            stopVisible();
            updateTotalTime();
            fadeInOnResume();
            animateMoveUp(circularTimerView);
        });
    }
    private void initDatabase(){
        DatabaseHelper dHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        settingsRepository = new SettingsRepository(db);
    }
    private void findAllId(){
        circularTimerView = findViewById(R.id.circularTimerView);
        pauseButton = findViewById(R.id.pauseButtonView);
        playButton = findViewById(R.id.playButtonView);
        startButton = findViewById(R.id.startButtonView);
        backButton = findViewById(R.id.backButtonTimerView);
        stopButton = findViewById(R.id.stopButtonView);
        recyclerHour = findViewById(R.id.recyclerHour);
        recyclerMinute = findViewById(R.id.recyclerMinute);
        recyclerSecond = findViewById(R.id.recyclerSecond);
        divider1 = findViewById(R.id.dotDivider1);
        divider2 = findViewById(R.id.dotDivider2);
    }
    private void adapterInit(){
        hourAdapter = new TimerWheelAdapter(this, 24);
        setupRecyclerView(recyclerHour, hourAdapter);
        hourAdapter.setSelectedPosition(hourAdapter.getItemCount() / 2);

        minuteAdapter = new TimerWheelAdapter(this, 60);
        setupRecyclerView(recyclerMinute, minuteAdapter);
        minuteAdapter.setSelectedPosition(minuteAdapter.getItemCount() / 2);

        recyclerSecond.setVisibility(GONE);
        divider2.setVisibility(GONE);
        if(settingsRepository.getTimerDotsSetting(userRepository.getUserIdInSystem()) == 1){
            divider1.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.romb));
            divider2.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.romb));
        } else {
            divider1.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.circle));
            divider2.setImageDrawable(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.circle));
        }

        if(settingsRepository.getTimerSecSetting(userRepository.getUserIdInSystem()) == 1){
            recyclerSecond.setVisibility(VISIBLE);
            divider2.setVisibility(VISIBLE);
            secondAdapter = new TimerWheelAdapter(this, 60);
            setupRecyclerView(recyclerSecond, secondAdapter);
            secondAdapter.setSelectedPosition(secondAdapter.getItemCount() / 2);
        }

    }
    private void startVisible(){
        startButton.setVisibility(INVISIBLE);
        pauseButton.setVisibility(VISIBLE);
        stopButton.setVisibility(VISIBLE);
        findViewById(R.id.setTime).setVisibility(INVISIBLE);
    }
    private void stopVisible(){
        startButton.setVisibility(VISIBLE);
        playButton.setVisibility(INVISIBLE);
        pauseButton.setVisibility(INVISIBLE);
        stopButton.setVisibility(INVISIBLE);
        findViewById(R.id.setTime).setVisibility(VISIBLE);
    }
    private void startTimer(long time) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.edit().putBoolean("timer_finished", false).apply();
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                circularTimerView.setCurrentTimeMillis(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                circularTimerView.setCurrentTimeMillis(0);
                if (timeRemaining > 0) {
                    sendTimeToService();
                } else {
                    // TODO: доделать
                }
                finish();
            }
        };
    }
    private void sendTimeToService() {
        Intent intent = new Intent("by.roman.worldradio2.TIMER_FINISHED");
        intent.putExtra("time", timeRemaining);
        LocalBroadcastManager.getInstance(TimerActivity.this).sendBroadcast(intent);
    }
    private void setupRecyclerView(RecyclerView recyclerView, TimerWheelAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.post(() -> {
            int middlePosition = (adapter.getItemCount() / 2);
            recyclerView.scrollToPosition(middlePosition);
            adapter.setSelectedPosition(middlePosition);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView rv, int newState) {
                super.onScrollStateChanged(rv, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
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
            int hours = hourAdapter.getSelectedPosition() % 24;
            int minutes = minuteAdapter.getSelectedPosition() % 60;
            totalTime = (hours * 3600000L) + (minutes * 60000L);
            if(settingsRepository.getTimerSecSetting(userRepository.getUserIdInSystem()) == 1){
                int seconds = secondAdapter.getSelectedPosition() % 60;
                totalTime = totalTime + (seconds * 1000L);
            }
            circularTimerView.setMaxTimeMillis(totalTime);
            circularTimerView.setCurrentTimeMillis(totalTime);
        }
    }
    private void pauseTimer() {
        countDownTimer.cancel();
        fadeOutOnPause();
    }
    private void resumeTimer() {
        startTimer(timeRemaining);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 70f, 270f);
        animator.setDuration(1250);
        animator.start();
    }
    private void animateMoveUp(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 270f, 70f);
        animator.setDuration(1250);
        animator.start();
    }
    private void blinkAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(circularTimerView, "alpha", 1f, 0.3f);
        animator.setDuration(300);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(1);
        animator.start();
    }
}