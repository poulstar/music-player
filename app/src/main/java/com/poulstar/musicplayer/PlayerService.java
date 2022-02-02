package com.poulstar.musicplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleService;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.poulstar.musicplayer.Player.MusicPlayer;
import com.poulstar.musicplayer.broadcast.MusicPlayerBroadcastReceiver;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PlayerService extends LifecycleService {

    public final String TAG = "Player Service";
    public final String CHANNEL_ID = "player_service";
    static int count = 0;
    Notification notification;
    RemoteViews notificationView;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "Player service started");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        createChannel();
        PendingIntent openAppIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent playIntent = new Intent(this, MusicPlayerBroadcastReceiver.class);
        playIntent.setAction("PLAY/PAUSE");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 20, playIntent, 0);

        notificationView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        notificationView.setOnClickPendingIntent(R.id.btnPlay, playPendingIntent);

        notification =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Music Player")
                        .setContentText("Music player running")
                        .setSmallIcon(R.drawable.ic_baseline_library_music_24)
                        .setContentIntent(pendingIntent)
                        .setCustomContentView(notificationView)
                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                        .addAction(R.drawable.ic_baseline_library_music_24, "Open App", openAppIntent)
//                        .addAction(R.drawable.ic_baseline_library_music_24, "Exit", openAppIntent)
                        .build();

        startForeground(4, notification);

        MusicPlayer.self.music.observe(this, music -> {
            updateNotificationTitle(music.name);
            updateNotificationArtist(music.artist.get(0));
        });
        MusicPlayer.self.isMusicPlaying.observe(this, aBoolean -> {
            updateNotificationPlaybackStatus(aBoolean);
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i("Work", "Running in background " + (count++));
            }
        }, 0, 2000);

        return START_NOT_STICKY;
    }

    public void updateNotificationTitle(String title) {
        notificationView.setTextViewText(R.id.txtTitle, title);
        updateNotification(notificationView);
    }

    public void updateNotificationArtist(String artist) {
        notificationView.setTextViewText(R.id.txtArtist, artist);
        updateNotification(notificationView);
    }

    public void updateNotificationPlaybackStatus(boolean isPlaying) {
        notificationView.setTextViewText(R.id.btnPlay, isPlaying ? "Pause" : "Play");
        updateNotification(notificationView);
    }

    public void updateNotification(RemoteViews view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification.contentView = view;
        notificationManager.notify(4, notification);
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(CHANNEL_ID, "Player Service", NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            service.createNotificationChannel(chan);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Player service stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
}
