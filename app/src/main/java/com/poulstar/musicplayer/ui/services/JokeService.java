package com.poulstar.musicplayer.ui.services;

import com.poulstar.musicplayer.ui.models.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokeService {

    @GET("Any")
    Call<Joke> getJokes();
}
