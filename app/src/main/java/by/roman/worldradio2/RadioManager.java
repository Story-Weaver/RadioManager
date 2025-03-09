package by.roman.worldradio2;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

public class RadioManager {
    private final Context context;
    private ExoPlayer player;

    public RadioManager(Context context) {
        this.context = context.getApplicationContext();
        initializePlayer();
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(context).build();
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Log.e("RadioManager", "Ошибка воспроизведения: " + error.getMessage());
            }
        });
    }

    public void play(String streamUrl) {
        if (player == null) {
            initializePlayer();
        }

        DefaultHttpDataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory()
                .setUserAgent("Mozilla/5.0 (Android)");

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(streamUrl));
        ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
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
}
