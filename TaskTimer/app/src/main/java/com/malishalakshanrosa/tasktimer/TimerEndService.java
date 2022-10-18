package com.malishalakshanrosa.tasktimer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class TimerEndService extends Service {

    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.beep);
        player.setLooping(false);
        player. setVolume(100,100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY; // only if have any other service to execute, do it once current service completed.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop(); // To stop the media player song
        player.release(); // To remove the content from the RAM
    }
}