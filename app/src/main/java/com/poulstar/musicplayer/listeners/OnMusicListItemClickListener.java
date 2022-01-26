package com.poulstar.musicplayer.listeners;

import com.poulstar.musicplayer.ui.models.Music;

public interface OnMusicListItemClickListener {
    public void onClick(Music music);
    public void onLike();
}
