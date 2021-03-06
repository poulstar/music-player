package com.poulstar.musicplayer.ui.library;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poulstar.musicplayer.Player.MusicPlayer;
import com.poulstar.musicplayer.R;
import com.poulstar.musicplayer.databinding.FragmentDashboardBinding;
import com.poulstar.musicplayer.listeners.OnMusicListItemClickListener;
import com.poulstar.musicplayer.ui.Api;
import com.poulstar.musicplayer.ui.list.MusicListAdapter;
import com.poulstar.musicplayer.ui.models.Music;
import com.poulstar.musicplayer.ui.services.MusicPlayerService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {

    private final String TAG = "MusicPlayerList";
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        ViewGroup root = binding.getRoot();

        addMusicListToRecyclerView(root, MusicPlayer.self.playlist);

        return root;
    }

    public void addMusicListToRecyclerView(ViewGroup root, List<Music> musics) {
        if(getContext() == null) return;
        RecyclerView recyclerView = new RecyclerView(getContext());

        MusicListAdapter adapter = new MusicListAdapter(musics);
        adapter.setListener(new OnMusicListItemClickListener() {
            @Override
            public void onClick(Music music) {
                MusicPlayer.self.play(root.getContext(), music);
            }

            @Override
            public void onLike() {

            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        root.addView(recyclerView);
        recyclerView.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
    }

    public void addMusicListToScrollView(ViewGroup root, List<Music> musics) {
        ScrollView scrollView = new ScrollView(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);

        for (Music music : musics) {
            View musicItem = LayoutInflater.from(getContext()).inflate(R.layout.music_item, null);
            TextView txtTitle = musicItem.findViewById(R.id.txtTitle);
            TextView txtArtist = musicItem.findViewById(R.id.txtArtist);
            ImageView imgView = musicItem.findViewById(R.id.imgView);
            txtTitle.setText(music.name);
            if (music.artist.size() > 0) {
                txtArtist.setText(music.artist.get(0));
            }
            linearLayout.addView(musicItem);
            musicItem.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            Picasso.get().load(music.cover).into(imgView);
        }
        root.addView(scrollView);
        scrollView.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        scrollView.getLayoutParams().height = ConstraintLayout.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}