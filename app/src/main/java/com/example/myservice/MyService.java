package com.example.myservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private static final int REQUEST_CODE = 0;
    private static final int FLAG = 0;
    private static final int ID = 1;
    private DownBinder mbinder;

    @Override
    public void onCreate() {//foreground
        super.onCreate();
        Intent i = new Intent(MyService.this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MyService.this,REQUEST_CODE,i,FLAG);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("title")
                .setContentText("text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round))
                .setContentIntent(pi)
                .build();
        startForeground(ID,notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }
    class DownBinder extends Binder{
        public void start(){
            Log.d("DownBinder", "start:DownBinder ");
        }
        public int getprogress(){
            Log.d("DownBinder", "start:stop ");
            return 0;
        }
    }
}
