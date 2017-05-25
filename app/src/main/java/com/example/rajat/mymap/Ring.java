package com.example.rajat.mymap;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Ring extends Service {

public Ringtone ring;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(uri==null) {
            uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
ring=RingtoneManager.getRingtone(this,uri);
        ring.play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ring.stop();
    }
}
