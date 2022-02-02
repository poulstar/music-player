package com.poulstar.musicplayer.Player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.poulstar.musicplayer.ui.Api;
import com.poulstar.musicplayer.ui.models.Music;
import com.poulstar.musicplayer.ui.services.MusicPlayerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPlayer {

    String mediaUrl;
    MediaPlayer mediaPlayer;
    public MutableLiveData<Boolean> isMusicPlaying;
    public MutableLiveData<Music> music;
    public List<Music> playlist;

    public final static MusicPlayer self = new MusicPlayer();
    private MusicPlayer() {
        playlist = new ArrayList<>();
        isMusicPlaying = new MutableLiveData<>();
        music = new MutableLiveData<>();
        isMusicPlaying.setValue(false);
    }

    public void next(Context context) {
        int index = 0;
        for (int i = 0; i <= playlist.size(); i++) {
            if(playlist.get(i).id == music.getValue().id) {
                index = i + 1;
                break;
            }
        }
        if(index >= playlist.size()) {
            index = 0;
        }
        stop();
        play(context, playlist.get(index));
    }

    public void previous(Context context) {
        int index = 0;
        for (int i = 0; i <= playlist.size(); i++) {
            if(playlist.get(i).id == music.getValue().id) {
                index = i - 1;
                break;
            }
        }
        if(index < 0) {
            index = playlist.size() - 1;
        }
        stop();
        play(context, playlist.get(index));
    }

    public void play(Context context, Music music) {
        this.mediaUrl = music.file;
        this.music.setValue(music);
        if(mediaPlayer != null) {
            stop();
        }
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(music.file);
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

    public void seekTo(int progress) {
        if(mediaPlayer != null) {
            mediaPlayer.seekTo((mediaPlayer.getDuration()/100) * progress);
        }
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

    public void fetchPlayList() {
        MusicPlayerService musicPlayerService = Api.self.retrofit.create(MusicPlayerService.class);
        musicPlayerService.getMusicList(1).enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if (response.isSuccessful()) {
                    for (Music music : Objects.requireNonNull(response.body())) {
                        playlist.add(music);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                Log.e("Playlist", "Cannot fetch music list. " + t.getMessage());
            }
        });
    }
}
