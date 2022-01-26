package com.poulstar.musicplayer.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poulstar.musicplayer.R;
import com.poulstar.musicplayer.listeners.OnMusicListItemClickListener;
import com.poulstar.musicplayer.ui.models.Music;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    List<Music> data;
    OnMusicListItemClickListener listener;

    public MusicListAdapter(List<Music> musics) {
        this.data = musics;
    }

    public void setListener(OnMusicListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Music music = data.get(position);
        holder.txtTitle.setText(music.name);
        holder.txtArtist.setText(music.artist.get(0));
        Picasso.get().load(music.cover).into(holder.imgView);
        holder.container.setOnClickListener(v -> {
            if(listener != null) {
                listener.onClick(music);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtArtist;
        ImageView imgView;
        ViewGroup container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtArtist = itemView.findViewById(R.id.txtArtist);
            imgView = itemView.findViewById(R.id.imgView);
            container = itemView.findViewById(R.id.music_item_container);
        }
    }

}
