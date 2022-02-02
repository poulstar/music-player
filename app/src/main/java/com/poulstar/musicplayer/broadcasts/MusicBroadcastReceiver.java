package com.poulstar.musicplayer.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.poulstar.musicplayer.Player.MusicPlayer;

public class MusicBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MusicBR", intent.getAction());
        if(intent.getAction().equals("PLAY_PAUSE")) {
            Log.e("Toggle music playback", intent.getAction());
            MusicPlayer.self.togglePlayback();
        }
    }
}
