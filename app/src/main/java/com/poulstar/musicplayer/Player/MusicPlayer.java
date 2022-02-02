package com.poulstar.musicplayer.Player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;

public class MusicPlayer {

    String mediaUrl;
    MediaPlayer mediaPlayer;
    public MutableLiveData<Boolean> isMusicPlaying;

    public final static MusicPlayer self = new MusicPlayer();
    private MusicPlayer() {
        isMusicPlaying = new MutableLiveData<>();
        isMusicPlaying.setValue(false);
    }

    public void play(Context context, String url) {
        this.mediaUrl = url;
        if(mediaPlayer != null) {
            stop();
        }
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isMusicPlaying.setValue(true);
        }catch (IOException e) {
            Toast.makeText(context, "Media is not accessible", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isPlaying() {
        if(mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public void stop() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                isMusicPlaying.setValue(false);
            }
        }
    }

    public void pause() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                isMusicPlaying.setValue(false);
            }
        }
    }

    public void resume() {
        if(mediaPlayer != null) {
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                isMusicPlaying.setValue(true);
            }
        }
    }

    public void togglePlayback() {
        if(mediaPlayer != null) {
            if(isPlaying()) {
                pause();
            }else {
                resume();
            }
        }
    }
}
