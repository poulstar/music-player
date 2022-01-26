package com.poulstar.musicplayer.Player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

public class MusicPlayer {

    String mediaUrl;
    MediaPlayer mediaPlayer;

    public final static MusicPlayer self = new MusicPlayer();
    private MusicPlayer() { }

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
            }
        }
    }

    public void pause() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
    }

    public void resume() {
        if(mediaPlayer != null) {
            if(!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }
}
