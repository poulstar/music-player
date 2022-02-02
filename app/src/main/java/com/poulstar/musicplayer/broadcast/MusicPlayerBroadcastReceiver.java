package com.poulstar.musicplayer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.poulstar.musicplayer.Player.MusicPlayer;

public class MusicPlayerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("PLAY/PAUSE")) {
            MusicPlayer.self.togglePlayback();
        }
    }
}
