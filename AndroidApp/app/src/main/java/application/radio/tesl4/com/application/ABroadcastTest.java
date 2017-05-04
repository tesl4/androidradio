package application.radio.tesl4.com.application;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class ABroadcastTest extends AppCompatActivity {
    AudioManager mAudioManager;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_broadcast_test);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    View.OnClickListener onClickConnect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying() == true) return;

            String uri = "http://54.202.122.200:8000";
            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(uri);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
