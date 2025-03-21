package by.roman.worldradio2.ui.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import by.roman.worldradio2.R;
import by.roman.worldradio2.RadioService;
import by.roman.worldradio2.data.repository.DatabaseHelper;
import by.roman.worldradio2.data.repository.FavoriteRepository;
import by.roman.worldradio2.data.repository.UserRepository;
import by.roman.worldradio2.ui.activities.MainActivity;

public class BottomPlayerFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_FAVICON = "favicon";
    private static final String ARG_UUID = "uuid";

    private UserRepository userRepository;
    private FavoriteRepository favoriteRepository;

    private ImageView favorite;
    private ImageView stationLogo;
    private TextView stationName;
    private ImageView status;
    private ConstraintLayout player;
    private RadioService radioService;
    private boolean saveFlag = false;
    private boolean saveStatus;
    private String stationUuid;
    private OnChangedListener mListener;
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
        void onPlayerOpen();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stationUuid = getArguments().getString(ARG_UUID);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userRepository = new UserRepository(db);
        favoriteRepository = new FavoriteRepository(db);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_player, container, false);
        findById(view);
        radioService = RadioService.getInstance(getContext(),(MainActivity) requireContext());
        saveStatus = radioService.statusPlaying();
        Log.e("BottomPlayer","saveStatus = " + saveStatus);
        updateStatusIcon();
        if (getArguments() != null) {
            stationName.setText(getArguments().getString(ARG_NAME));
            stationName.setSelected(true);
            String favicon = getArguments().getString(ARG_FAVICON);
            if (favicon != null && !favicon.isEmpty()) {
                Glide.with(requireContext()).load(favicon).into(stationLogo);
            } else {
                stationLogo.setImageResource(R.drawable.findcountry_navigationbar);
            }
            saveFlag = favoriteRepository.isStationFavorite(userRepository.getUserIdInSystem(), stationUuid);
            updateFavoriteIcon();
            favorite.setOnClickListener(v -> toggleFavorite());
            status.setOnClickListener(v -> toggleStatus());
            player.setOnClickListener(v -> togglePlayer());
        }
        return view;
    }
    private void findById(View view){
        favorite = view.findViewById(R.id.save_unsave);
        stationLogo = view.findViewById(R.id.station_logo_player);
        stationName = view.findViewById(R.id.station_name_player);
        status = view.findViewById(R.id.play_pause);
        player = view.findViewById(R.id.player_player);
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
    private void togglePlayer(){
        if (mListener != null) {
            mListener.onPlayerOpen();
        }
    }
    private void updateFavoriteIcon() {
        favorite.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveFlag ? R.drawable.saved_player : R.drawable.unsaved_player));
    }
    private void updateStatusIcon() {
        status.setImageDrawable(AppCompatResources.getDrawable(requireContext(),
                saveStatus ? R.drawable.pause_player : R.drawable.play_player));
    }

}
