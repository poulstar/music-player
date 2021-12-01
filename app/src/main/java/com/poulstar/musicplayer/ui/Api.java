package com.poulstar.musicplayer.ui;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public Retrofit retrofit;

    public Api() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://v2.jokeapi.dev/joke/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
