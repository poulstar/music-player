package com.poulstar.musicplayer.ui.services;


import com.poulstar.musicplayer.ui.models.Music;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MusicPlayerService {

    @GET("music/list/")
    public Call<List<Music>> getMusicList(@Query("page") int page);

    @GET("music/list/")
    public Call<List<Music>> getMusicList(@Query("page") int page, @Query("filter") String name);

}
