package by.roman.worldradio2;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Metadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.extractor.metadata.icy.IcyInfo;

import java.util.Collections;

import by.roman.worldradio2.ui.fragments.BottomPlayerFragment;

@androidx.media3.common.util.UnstableApi
public class RadioManager {
    private final Context context;
    private String currentTrack;
    private TrackUpdateListener trackUpdateListener;
    private static RadioManager instance;

    private ExoPlayer player;
    public interface TrackUpdateListener {
        void onTrackUpdated();
    }
    public void setTrackUpdateListener(TrackUpdateListener listener) {
        this.trackUpdateListener = listener;
    }
    public static synchronized RadioManager getInstance(Context context) {
        if (instance == null) {
            instance = new RadioManager(context.getApplicationContext());
        }
        return instance;
    }
    private RadioManager(Context context) {
        this.context = context.getApplicationContext();
        initializePlayer();
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(context).build();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(@NonNull PlaybackException error) {
                Log.e("RadioManager", "Ошибка воспроизведения: " + error.getMessage());
            }

            @Override
            public void onMetadata(Metadata metadata) {
                for (int i = 0; i < metadata.length(); i++) {
                    Metadata.Entry entry = metadata.get(i);
                    if (entry instanceof IcyInfo) {
                        String title = ((IcyInfo) entry).title;
                        if (title != null) {
                            Log.d("RadioManager","Сейчас играет: " + title);
                            currentTrack = title;
                            updateTrack();
                        }
                    }
                }
            }
        });
    }
    public void updateTrack() {
        Log.d("RadioManager","upd" + trackUpdateListener);
        if (trackUpdateListener != null) {
            trackUpdateListener.onTrackUpdated();
        }
    }
    public void play(String streamUrl) {
        if (player == null) {
            initializePlayer();
        }

        DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent("Mozilla/5.0 (Android)")
                .setDefaultRequestProperties(Collections.singletonMap("Icy-MetaData", "1"));

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(streamUrl));

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem);

        player.setMediaSource(mediaSource);
        player.prepare();
        player.play();
    }

    public void stop() {
        if (player != null) {
            player.stop();
        }
    }

    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
    public String getCurrentTrack(){
       return currentTrack;
    }
    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }
}


