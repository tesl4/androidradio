package application.radio.tesl4.com.application;

import android.app.DialogFragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by tesl4 on 17-5-4.
 */

public class DPlayCast extends DialogFragment
{
    AudioManager mAudioMgr;
    MediaPlayer mPlayer;

    ImageButton mBtnPlayStop;
    TextView    mTxtTitle;
    SeekBar     mSkbVolume;
    ImageView   mImgVolume;

    public static DPlayCast Instance()
    {
        return new DPlayCast();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAudioMgr = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.d_broadcast_test, null, false);
        mBtnPlayStop   = (ImageButton) v.findViewById(R.id.btn_play_stop);

        mTxtTitle      = (TextView)    v.findViewById(R.id.txt_title);

        mSkbVolume     = (SeekBar)     v.findViewById(R.id.skb_volume);
        if(mSkbVolume     != null)
        {
            mSkbVolume.setMax(mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            mSkbVolume.setOnSeekBarChangeListener(onVolumeControl);
        }

        mImgVolume     = (ImageView)   v.findViewById(R.id.img_volume);

        if(mPlayer != null)
        {
        }

       return v;
    }

    public void setEnvironment(MediaPlayer _player, AudioManager _manager)
    {
        mPlayer = _player;
        mAudioMgr = _manager;
    }

    View.OnClickListener onClickPlayStop = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            if(mPlayer != null) return;;

            if(mPlayer.isPlaying())
            {
                mPlayer.pause();
            }
            else
            {
                mPlayer.start();
            }
            setPlayStopSrc();
        }
    };

    SeekBar.OnSeekBarChangeListener onVolumeControl = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            mAudioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_PLAY_SOUND);
            setVolumePhaseSrc();
        }

        @Override public void onStartTrackingTouch(SeekBar seekBar){}
        @Override public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    void setPlayStopSrc()
    {
        if(mPlayer != null)
        {
            if(mPlayer.isPlaying() == true)
            {
                mBtnPlayStop.setImageResource(R.drawable.ic_pause_black_48dp);
            }
            else
            {
                mBtnPlayStop.setImageResource(R.drawable.ic_play_arrow_black_48dp);
            }
        }
    }

    void setVolumePhaseSrc()
    {
        int v = mSkbVolume.getProgress() / mSkbVolume.getMax();

        if(v <= 0)
        {
            mImgVolume.setImageResource(R.drawable.ic_volume_mute_black_24dp);
        }
        else if (v < mSkbVolume.getMax() * 0.5)
        {
            mImgVolume.setImageResource(R.drawable.ic_volume_down_black_24dp);

        }
        else
        {
            mImgVolume.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
    }



}
