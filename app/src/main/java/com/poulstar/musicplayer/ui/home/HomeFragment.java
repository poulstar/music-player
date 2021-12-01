package com.poulstar.musicplayer.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poulstar.musicplayer.R;
import com.poulstar.musicplayer.databinding.FragmentHomeBinding;
import com.poulstar.musicplayer.ui.Api;
import com.poulstar.musicplayer.ui.models.Joke;
import com.poulstar.musicplayer.ui.services.JokeService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ViewGroup root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button btn = new Button(getContext());
        btn.setText("Get a Joke");
        btn.setOnClickListener(v -> {
            getJokeWithEnqueue();
//            Thread requestThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    getJokeWithThread();
//                }
//            });
//            requestThread.start();
        });
        root.addView(btn);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getJokeWithEnqueue() {
        Api api = new Api();
        JokeService service = api.retrofit.create(JokeService.class);
        Call<Joke> jokeRequest = service.getJokes();
        jokeRequest.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> jokeResponse) {
                if(jokeResponse.isSuccessful()) {
                    Joke joke = jokeResponse.body();
                    Toast.makeText(getContext(),
                            String.format("Setup: %s\n Delivery: %s", joke.setup, joke.delivery),
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.e("Request", "Error: " + t.getMessage());
            }
        });
    }

    public void getJokeWithThread() {
        Api api = new Api();
        JokeService service = api.retrofit.create(JokeService.class);
        Call<Joke> jokeRequest = service.getJokes();
        try {
            Response<Joke> jokeResponse = jokeRequest.execute();
            if(jokeResponse.isSuccessful()) {
                Joke joke = jokeResponse.body();
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(),
                            String.format("Setup: %s\n Delivery: %s", joke.setup, joke.delivery),
                            Toast.LENGTH_LONG).show();
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}