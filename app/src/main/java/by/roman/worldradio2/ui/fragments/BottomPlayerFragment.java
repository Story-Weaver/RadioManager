package by.roman.worldradio2.ui.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static androidx.core.content.ContextCompat.getSystemService;


import android.animation.ValueAnimator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

import by.roman.worldradio2.R;
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.RadioStationRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.activities.MainActivity;

public class BottomPlayerFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_FAVICON = "favicon";
    private static final String ARG_UUID = "uuid";

    private UserRepository userRepository;
    private FavoriteRepository favoriteRepository;

    private ImageView smallFavorite;
    private ImageView smallStationLogo;
    private TextView smallStationName;
    private ImageView smallStatus;
    private ConstraintLayout smallPlayer;
    private ImageView largeFavorite;
    private ImageView largeStationLogo;
    private ImageView largeBack;
    private ImageView largeVolume;
    private ImageView largeEnternet;
    private TextView largeStationName;
    private ImageView largeStatus;
    private ConstraintLayout largePlayer;
    private ConstraintLayout Player;
    private RadioService radioService;
    private boolean saveFlag = false;
    private boolean saveStatus = false;
    private boolean isExpanded = false;
    private String stationUuid;
    private OnChangedListener mListener;
    private RadioStationRepository radioStationRepository;
    private AudioManager audioManager;
    private int previousVolume = -1;
    private boolean isMuted = false;
    public static BottomPlayerFragment newInstance(String name, String favicon, String uuid) {
        BottomPlayerFragment fragment = new BottomPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_FAVICON, favicon);
        args.putString(ARG_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnChangedListener) {
            mListener = (OnChangedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnChangedListener");
        }
    }

    public interface OnChangedListener {
        void onFavoriteChanged();
        void onPlayerExpanded();
        void onPlayerCollapsed();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioManager = ContextCompat.getSystemService(requireContext(), AudioManager.class);

        if (getArguments() != null) {
            stationUuid = getArguments().getString(ARG_UUID);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        favoriteRepository = new FavoriteRepository(db);
    }

    @Override
    public void onResume(){
        super.onResume();
        largeEnternet.setEnabled(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_player, container, false);
        findById(view);
        updateImageBasedOnVolume();
        radioService = RadioService.getInstance(getContext(), (MainActivity) requireContext());
        radioService.checkNow();
        DatabaseHelper dHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dHelper.getWritableDatabase();
        radioStationRepository = new RadioStationRepository(db);
        if(radioStationRepository.getActiveStation().getHomepage() == null){
            largeEnternet.setEnabled(false);
            largeEnternet.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.internet_explorer_off));
        }
        if (getArguments() != null) {
            smallStationName.setText(getArguments().getString(ARG_NAME));
            smallStationName.setSelected(true);
            String favicon = getArguments().getString(ARG_FAVICON);
            if (favicon != null && !favicon.isEmpty()) {
                Glide.with(requireContext()).load(favicon).into(smallStationLogo);
            } else {
                smallStationLogo.setImageResource(R.drawable.findcountry_navigationbar);
            }
            saveFlag = favoriteRepository.isStationFavorite(userRepository.getUserIdInSystem(), stationUuid);
            updateFavoriteIcon();
            saveStatus = radioService.statusPlaying();
            Log.e("BottomPlayer", "saveStatus = " + saveStatus);
            updateStatusIcon();

        }
        smallFavorite.setOnClickListener(v -> toggleFavorite());
        largeFavorite.setOnClickListener(v -> toggleFavorite());
        smallStatus.setOnClickListener(v -> toggleStatus());
        largeStatus.setOnClickListener(v -> toggleStatus());
        smallPlayer.setOnClickListener(v -> togglePlayer());
        largeBack.setOnClickListener(v -> togglePlayer());
        largeVolume.setOnClickListener(v -> toggleMute());
        largeEnternet.setOnClickListener(v -> {openUrlInBrowser();largeEnternet.setEnabled(false);});

        requireActivity().getContentResolver().registerContentObserver(
                android.provider.Settings.System.CONTENT_URI,
                true,
                new ContentObserver(new Handler(Looper.getMainLooper())) {
                    @Override
                    public void onChange(boolean selfChange) {
                        super.onChange(selfChange);
                        if (!isMuted) {
                            updateImageBasedOnVolume();
                        }
                    }
                }
        );
        largeStationName.setText(radioStationRepository.getActiveStation().getName());
        String largeFavicon = radioStationRepository.getActiveStation().getFavicon();
        Log.d("BottomPlayer", "Loading favicon into largeStationLogo: " + largeFavicon);
        Glide.with(requireContext())
                .load(largeFavicon)
                .override(250, 250)
                .error(R.drawable.findcountry_navigationbar)
                .listener(new com.bumptech.glide.request.RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Ошибка загрузки: " + (e != null ? e.getMessage() : "Неизвестная ошибка"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Изображение загружено, размер: " + resource.getIntrinsicWidth() + "x" + resource.getIntrinsicHeight());
                        return false;
                    }
                })
                .into(largeStationLogo);

        return view;
    }
    private void openUrlInBrowser() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(radioStationRepository.getActiveStation().getHomepage()));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "No browser found to open the URL", Toast.LENGTH_SHORT).show();
            Log.e("BottomPlayer", "Error opening URL: " + e.getMessage());
        }
    }
    private void toggleMute() {
        if (!isMuted) {
            previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            largeVolume.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.mute));
            isMuted = true;
        } else {
            if (previousVolume == -1) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                previousVolume = maxVolume / 2;
            }
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, previousVolume, 0);
            updateImageBasedOnVolume();
            isMuted = false;
        }
    }
    public void setMuted(boolean muted) {
        this.isMuted = muted;
    }
    public void updateImageBasedOnVolume() {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        float volumePercent = (float) currentVolume / maxVolume * 100;
        int volumeLevel;
        if (volumePercent == 0) {
            volumeLevel = 1; // 0%
        } else if (volumePercent <= 45) {
            volumeLevel = 2; // 1-45%
        } else if (volumePercent <= 90) {
            volumeLevel = 3; // 46-90%
        } else {
            volumeLevel = 4; // 90-100%
        }

        switch (volumeLevel) {
            case 1:
                largeVolume.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.mute));
                break;
            case 2:
                largeVolume.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.sound_min));
                break;
            case 3:
                largeVolume.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.sound_avr));
                break;
            case 4:
                largeVolume.setImageDrawable(AppCompatResources.getDrawable(requireContext(),R.drawable.sound_full));
                break;
        }
    }
private void findById(View view) {
        smallFavorite = view.findViewById(R.id.save_unsave);
        smallStationLogo = view.findViewById(R.id.station_logo_player);
        smallStationName = view.findViewById(R.id.station_name_player);
        smallStatus = view.findViewById(R.id.play_pause);
        smallPlayer = view.findViewById(R.id.small_player);
        smallPlayer.setVisibility(VISIBLE);

        largeStationLogo = view.findViewById(R.id.large_station_logo);
        largeStationName = view.findViewById(R.id.large_station_name);
        largeStatus = view.findViewById(R.id.large_play_payse);
        largePlayer = view.findViewById(R.id.large_player);
        largeBack = view.findViewById(R.id.large_back);
        largeVolume = view.findViewById(R.id.large_volume);
        largeEnternet = view.findViewById(R.id.large_enternet);
        largeFavorite = view.findViewById(R.id.large_save_unsave);
        Player = view.findViewById(R.id.player_player);
        largeStationLogo.setVisibility(INVISIBLE);
        largePlayer.setVisibility(INVISIBLE);
}
    private void toggleFavorite() {
        if (!saveFlag) {
            favoriteRepository.addFavorite(userRepository.getUserIdInSystem(), stationUuid);
        } else {
            favoriteRepository.removeFavorite(userRepository.getUserIdInSystem(), stationUuid);
        }
        if (mListener != null) {
            mListener.onFavoriteChanged();
        }
        saveFlag = !saveFlag;
        updateFavoriteIcon();
    }
    private void toggleStatus() {
        if (saveStatus) {
            radioService.pause();
        } else {
            radioService.play();
        }
        saveStatus = !saveStatus;
        updateStatusIcon();
    }
    private void togglePlayer() {
        smallPlayer.setVisibility(VISIBLE);
        if (isExpanded) {
            if (mListener != null) {
                mListener.onPlayerCollapsed();
            }
            largePlayer.setVisibility(INVISIBLE);
            largeStationLogo.setVisibility(INVISIBLE);
        } else {
            smallPlayer.setVisibility(INVISIBLE);
            if (mListener != null) {
                mListener.onPlayerExpanded();
            }
            largePlayer.setVisibility(VISIBLE);
            largeStationLogo.setVisibility(VISIBLE);
        }
        isExpanded = !isExpanded;
    }
    private void updateFavoriteIcon() {
        smallFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveFlag ? R.drawable.saved_player : R.drawable.unsaved_player));
        largeFavorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveFlag ? R.drawable.saved_player : R.drawable.unsaved_player));
    }
    private void updateStatusIcon() {
        smallStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveStatus ? R.drawable.pause_player : R.drawable.play_player));
        largeStatus.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveStatus ? R.drawable.pausebutton_timer : R.drawable.playbutton_timer));
    }

}
