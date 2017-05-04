package application.radio.tesl4.com.application;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class ABroadcastTest extends AppCompatActivity
{
    AudioManager mAudioManager;
    MediaPlayer mMediaPlayer;
    Button mBtnConnect;
    Button mBtnDisconnect;
    SeekBar mSkbVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_broadcast_test);

        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        mBtnConnect = (Button)findViewById(R.id.connect);
        mBtnConnect.setOnClickListener(onClickConnect);

        mBtnDisconnect = (Button)findViewById(R.id.disconnect);
        mBtnDisconnect.setOnClickListener(onClickDisconnect);

        mSkbVolume = (SeekBar)findViewById(R.id.skb_volume);
        mSkbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mSkbVolume.setOnSeekBarChangeListener(onChangeValue);
        mSkbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    View.OnClickListener onClickConnect = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            if(mMediaPlayer.isPlaying() == true) return;

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

    View.OnClickListener onClickDisconnect = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mMediaPlayer.stop();
        }
    };

    SeekBar.OnSeekBarChangeListener onChangeValue = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_PLAY_SOUND);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



}
