package com.poulstar.musicplayer.ui;

import com.poulstar.musicplayer.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static Api self = new Api();

    public Retrofit retrofit;
    private Api() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
