package com.example.park;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class reserve extends AppCompatActivity   {
    String TAG = "Main";
    private Button rbtn_1;
    private Spinner rview_1;
    String result;//儲存資料字串
    private Button start_btn;
    private TextView car_view1;
    private ImageView qr_code;
    private String park="done";//下拉式選單車位
    private String qrcode="";
    private TextView t1;
    String tempaccount="none";
    private Button tbtn_2;
    private String stop="stop";
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        t1=findViewById(R.id.t1);
        rbtn_1=findViewById(R.id.rbtn_1);
        tbtn_2=findViewById(R.id.tbtn_3);
        rview_1=findViewById(R.id.rview_1);
        start_btn=findViewById(R.id.tbtn_2);
        car_view1=findViewById(R.id.tview_1);
        qr_code=findViewById(R.id.qr_code);
        time=findViewById(R.id.time);
        tbtn_2.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)//按鈕監控
            {

                    Intent intent=new Intent(reserve.this,turn.class);
                    startActivity(intent);

                    /*if(park=="done")
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(reserve.this);
            dialog.setTitle("抱歉暫時無空車位~~");
            dialog.setMessage("");
            dialog.setMessage("");
            dialog.setMessage("請按下面'返回'鍵,謝謝>//<");
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(reserve.this,turn.class);
                    startActivity(intent);
                }
            };
            dialog.setPositiveButton("返回", OkClick);
            dialog.show();
        }*/
            }


        });
        rbtn_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)//按鈕監控
            {   //宣告執行緒
                Thread thread =new Thread(mutiThread);//按鈕"觸發"執行程式碼,讀取車位
                thread.start();//開始執行

            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(reserve.this,park,Toast.LENGTH_LONG);
                toast.show();
                if(park=="done")
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(reserve.this);
                    dialog.setTitle("忘記選車位囉~~ T.T");
                    dialog.setMessage("");
                    dialog.setMessage("");
                    dialog.setMessage("請按下面'確認'鍵,重新點選螢幕按鈕的'搜索'鍵,來選擇車位,謝謝>//<");
                    DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(reserve.this,reserve.class);
                            startActivity(intent);
                        }
                    };
                        dialog.setPositiveButton("確認", OkClick);
                        dialog.show();
                }
                if(park!="done") {
                    getcode();//qr_code生成
                    send();//傳預約好的車位給"資料庫"
                    startTimer();//預約btn,startTimer()函式觸發

                }
            }
        });

    }



    private Runnable mutiThread =new Runnable()
    {
        @Override
        public void run()
        {
            try{
               // URL url=new URL("http://172.20.10.4/rcar_id.php");
                URL url=new URL("http://163.23.24.56/rcar_id.php");
                //開始宣告HTTP連線需要的物件,並建立HttpURLConnection物件
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //設定連線方式"Post"
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);//允許輸出
                connection.setDoInput(true);//允許讀入
                connection.setUseCaches(false);//不使用快取
                connection.connect();//開始連線
                int responseCode = connection.getResponseCode();//建立取得回應的物件
                if(responseCode == HttpURLConnection.HTTP_OK)//回傳OK
                {   //取得輸入串流
                    InputStream inputStream = connection.getInputStream();
                    //取得輸入串流資料
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
                    String box="";//存放字串
                    String line=null;//讀取字串
                    while((line = bufReader.readLine()) != null)
                    {
                        box+=line+"\n";//每讀出一列,就存放字串後面
                    }
                    inputStream.close();//關閉輸入串流
                    result = box; //把box存入全域變數result裡
                }
            }
            catch(Exception e)
            {
                 result=e.toString();//如果錯誤會回傳false,且利用"toString"轉成字串
            }
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    r_id(result);//傳到"sensor_park"的函式裡
                }
            });
        }
    };
    private void r_id(String space)
    {
        char num[] ;
        String num1[] = new String[10];
        int a=0;
        num =space.toCharArray();
        for(int i=0;i<space.length()-1;i++)
        {
            if(num[i]=='e')
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(reserve.this);
                dialog.setTitle("暫時沒車位囉~~");
                dialog.setMessage("");
                dialog.setMessage("");
                dialog.setMessage("請按下面'返回'鍵,謝謝>//<");
                DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(reserve.this,turn.class);
                        startActivity(intent);
                    }
                };
                dialog.setPositiveButton("返回", OkClick);
                dialog.show();
            }
            if(num[i]!='"'&&num[i]!='A')
            {
                num1[a]="A"+num[i];
                a++;
            }
        }
       String tmp[]=new String[a];
        for(int i=0;i<a;i++) {
            tmp[i]=num1[i];
        }
        ArrayAdapter<String> adapSum = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,tmp);
        adapSum.setDropDownViewResource(R.layout.spinner_background);
        rview_1.setAdapter(adapSum);

        rview_1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected
                    (AdapterView<?> adapterView, View view, int position, long l) {
               park = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       /* String c=String.valueOf(a);
         Toast toast=Toast.makeText(reserve.this,park,Toast.LENGTH_LONG);
         toast.show();
        */
    }


    private void startTimer() {   //倒數計時,"倒計時的總時間 millisInFuture","間隔時間 countDownInterval"
        //總共時間(3秒) = millisInFuture(3000) / countDownInterval(1000)
        // 秒 = millisUntilFinished/1000
        //"CountDownTimer" Android封裝好的一套實現計時器函式
        //599000為10分鐘
       /* new CountDownTimer(20000, 1000)
        {
            public void onTick(long millisUntilFinished) {
                int num=(int) millisUntilFinished/1000;
                int min=num/(60);
                int sec=num-(min*60);
            }
            public void onFinish() {
               // time_view1.setText("done");
                send_delete();
                getout();
            }
        }.start();*/
        save();
        Intent intent = new Intent(this,BroadcastService.class);
        startService(intent);
        Log.i(TAG,"Started Service");
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG,"Registered broadcast receiver");

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        Log.i(TAG,"Unregistered broadcast receiver");
    }

    @Override
    protected void onStop() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {

        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,BroadcastService.class));
        Log.i(TAG,"Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown",60000);
            Log.i(TAG,"Countdown seconds remaining:" + millisUntilFinished / 1000);
            int num=(int) millisUntilFinished/1000;
            int min=num/(60);
            int sec=num-(min*60);
            time.setText(min+":"+sec);
            //SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
            //sharedPreferences.edit().putLong("time",millisUntilFinished).apply();
            SharedPreferences sharedPreferences = getSharedPreferences("Data_1", MODE_PRIVATE);
            qrcode=sharedPreferences.getString("qr_id", qrcode);
            park=sharedPreferences.getString("car_id",park);
            car_view1.setText(park);
            BarcodeEncoder encoder = new BarcodeEncoder();
            try {
                Bitmap bit = encoder.encodeBitmap(qrcode, BarcodeFormat.QR_CODE,
                        350, 350);
                qr_code.setImageBitmap(bit);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            if(millisUntilFinished/ 1000==1)
            {
               // send_delete();
                getout();
            }
        }
    }


    private void getout()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(reserve.this);
        dialog.setTitle("警告!時間超過囉~");
        dialog.setMessage("");
        dialog.setMessage("");
        dialog.setMessage("請按'返回'鍵,重新預約,謝謝>//<");
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(reserve.this,turn.class);
                startActivity(intent);
            }
        };
        dialog.setPositiveButton("返回", OkClick);
        dialog.setCancelable(false);
        dialog.show();
    }
    private void getcode()
    {
        getdata();
        qrcode=park+tempaccount;
        BarcodeEncoder encoder = new BarcodeEncoder();
        try {
            Bitmap bit = encoder.encodeBitmap(qrcode, BarcodeFormat.QR_CODE,
                    350, 350);
            qr_code.setImageBitmap(bit);
        } catch (WriterException e) {
            e.printStackTrace();
        }
       car_view1.setText(park);
    }
    private void send()
    {   //用Volley的方式傳送,但缺點不能傳大量的資料
        RequestQueue queue = Volley.newRequestQueue(this);

        //final String url = "http://172.20.10.4/r_code.php";
         final String url = "http://163.23.24.56/r_code.php";
        //log.d視窗 會顯示 "成功"=車位,"失敗"=ERROR
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("PHP Response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON PARSE", "ERROR");
            }
        }){ //"params.put"在php用"car_id"來接收,但無法直接網頁顯示,可以用資料庫來顯示
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("car_id",park);
                params.put("q_id",qrcode);
                params.put("account",tempaccount);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void getdata()
    {
        Intent intent=getIntent();
        tempaccount=intent.getStringExtra("account");
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
    private void save()
    {
        SharedPreferences sharedPreferences= getSharedPreferences("Data_1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("qr_id",qrcode);
        editor.putString("car_id",park);
        editor.commit();
    }

}