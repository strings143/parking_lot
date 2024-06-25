package com.example.park;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BroadcastService extends Service {
    private String TAG = "BroadcastService";
    public static final String COUNTDOWN_BR = "com.example.park";
    Intent intent = new Intent(COUNTDOWN_BR);
    CountDownTimer countDownTimer = null;
    String qrcode;
  //  SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        get();
        Log.i(TAG,"Starting timer...");
        /*sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
        long millis = sharedPreferences.getLong("time",3000);
        if (millis / 1000 == 0) {
            millis = 30000;
        }*/
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG,"Countdown seconds remaining:" + millisUntilFinished / 1000);
                intent.putExtra("countdown",millisUntilFinished);
                sendBroadcast(intent);
            }
            @Override
            public void onFinish() {
               // send_delete();
            }
        };
        countDownTimer.start();
    }
    public void get()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("Data_1", MODE_PRIVATE);
        qrcode=sharedPreferences.getString("qr_id", qrcode);
    }
    public void  send_delete()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
       // final String url = "http://172.20.10.4/delete_qid.php";
        final String url = "http://163.23.24.56/delete_qid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("PHP Response", response);
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("JSON PARSE", "ERROR");
            }
        })
        { //"params.put"在php用"car_id"來接收,但無法直接網頁顯示,可以用資料庫來顯示
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("q_id",qrcode);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
