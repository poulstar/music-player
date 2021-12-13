package com.poulstar.musicplayer;

import com.poulstar.musicplayer.ui.Api;
import com.poulstar.musicplayer.ui.models.Music;
import com.poulstar.musicplayer.ui.services.MusicPlayerService;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

public class MusicApiTest {

    MusicPlayerService musicPlayerService;

    public MusicApiTest() {
        musicPlayerService = Api.self.retrofit.create(MusicPlayerService.class);
    }

    @Test
    public void fetchMusicList() {
        System.out.println("Sending request to music list");
        try {
            Response<List<Music>> musicResponse = musicPlayerService.getMusicList(1, "M").execute();
            if(musicResponse.isSuccessful()) {
                for (Music music: Objects.requireNonNull(musicResponse.body())){
                    System.out.println("Music found: "+music.name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
