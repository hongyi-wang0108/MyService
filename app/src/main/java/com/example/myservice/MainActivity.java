package com.example.myservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_service;
    private Button btn_service;
    private Button btn_start_service;
    private Button btn_stop_service;
    private Button btn_bind_service;
    private Button btn_unbind_service;
    private ProgressDialog progressDialog;

    //子线程更新UI:handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    tv_service.setText("nice to meet you");
                    break;
                default:
                    break;
            }

        }
    };
    private ServiceConnection conn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        //3种制造worker线程
        //1.
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        //2.
        //service service = new service();
        //new Thread(service).start();//
        new service().start();
        //3
        service2  service2 = new service2();
        new Thread(service2).start();*/

        //子线程更新UI:handler
        tv_service = findViewById(R.id.tv_service);
        btn_service = findViewById(R.id.btn_service);
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        });

        //子线程更新UI:AsyncTask
        new Asy().execute();

        //基本用法：
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.DownBinder downBinder = (MyService.DownBinder) service;
                downBinder.start();
                downBinder.getprogress();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //
            }
        };
        btn_start_service = findViewById(R.id.btn_start_service);
        btn_stop_service = findViewById(R.id.btn_stop_service);
        btn_bind_service = findViewById(R.id.btn_bind_service);
        btn_unbind_service = findViewById(R.id.btn_unbind_service);

        btn_start_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(MainActivity.this, MyService.class);
                //启动
                startService(i);*/

                //IntentService
                Intent i = new Intent(MainActivity.this, MyIntentService.class);
                startService(i);
            }
        });
        btn_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                //stop
                stopService(i);
            }
        });
        btn_bind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                //bind
                bindService(i,conn,BIND_AUTO_CREATE);
            }
        });
        btn_unbind_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MyService.class);
                //unbindService
                unbindService(conn);
            }
        });
    }
    //子线程更新UI:AsyncTask
    class Asy extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {//ui
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {//worker
            while (true){
                int down = 10;
                publishProgress(10);
                if(down >= 100)
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]+"%");
        }
    }
    /*//2.
    public class service extends Thread{
        @Override
        public void run() {
            super.run();
        }
    }
    //3
    public class service2 implements Runnable{
        @Override
        public void run() {

        }
    }*/
    public class MyIntentService extends IntentService{
        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         *
         * @param name Used to name the worker thread, important only for debugging.
         */
        public MyIntentService(String name) {
            super(name);
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {

        }

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }
}