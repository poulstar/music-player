package com.poulstar.musicplayer;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.poulstar.musicplayer.ui.Api;
import com.poulstar.musicplayer.ui.models.Joke;
import com.poulstar.musicplayer.ui.services.JokeService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test_joke_api() {
        Api api = new Api();
        JokeService jokeService = api.retrofit.create(JokeService.class);

        System.out.println("Sending request");
        Call<Joke> jokeRequest = jokeService.getJokes();
        try {
            Response<Joke> jokeResponse = jokeRequest.execute();
            System.out.println("Code: "+jokeResponse.code());
            if(jokeResponse.isSuccessful()) {
                Joke joke = jokeResponse.body();
                System.out.println(String.format("Setup: %s \n Delivery: %s", joke.setup, joke.delivery));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}