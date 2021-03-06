package com.poulstar.musicplayer.ui.home;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poulstar.musicplayer.Player.MusicPlayer;
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
    private ImageView imgDisk;
    private ImageButton btnPrevious, btnNext, btnPlay;
    private SeekBar seekBar;
    ObjectAnimator animator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        ViewGroup root = binding.getRoot();

        imgDisk = root.findViewById(R.id.imgMusicVisualizer);
        btnPlay = root.findViewById(R.id.btnPlay);
        btnPrevious = root.findViewById(R.id.btnPrevious);
        btnNext = root.findViewById(R.id.btnNext);
        seekBar = root.findViewById(R.id.seekBar);

        animator = ObjectAnimator.ofFloat(imgDisk, "rotation", 360);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();

        MusicPlayer.self.isMusicPlaying.observe(this, aBoolean -> {
            setPlayIcon();
        });

        setPlayIcon();
        btnPlay.setOnClickListener(v -> {
            if(MusicPlayer.self.isPlaying()) {
                MusicPlayer.self.pause();
            }else {
                MusicPlayer.self.resume();
            }
//            setPlayIcon();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MusicPlayer.self.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return root;
    }

    public void setPlayIcon() {
        if(MusicPlayer.self.isPlaying()) {
            btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
            animator.resume();
        }else {
            btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            animator.pause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getJokeWithEnqueue() {

    }
}