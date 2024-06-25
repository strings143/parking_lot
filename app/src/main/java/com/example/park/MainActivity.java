package com.example.park;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btn1;//宣告"按鈕"
    private TextView textView1;//宣告"view"
    private TextView textView2;//宣告"view"
    private TextView textView3;//宣告"view"
    private TextView textView4;//宣告"view"
    private TextView textView5;//宣告"view"
    private TextView textView6;//宣告"view"
    private TextView textView7;//宣告"view"
    private TextView textView8;//宣告"view"
    private TextView textView9;//宣告"view"
    private TextView textView10;//宣告"view"
    String result;//儲存資料字串
    private Button btn2;
    private Thread thread;//宣告執行緒
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn_1);//找到"按鈕id"
        textView1 =(TextView) findViewById(R.id.view1);//找到"view1_id"
        textView2 =(TextView) findViewById(R.id.view2);//找到"view2_id"
        textView3 =(TextView) findViewById(R.id.view3);//找到"view1_id"
        textView4 =(TextView) findViewById(R.id.view4);//找到"view2_id"
        textView5 =(TextView) findViewById(R.id.view5);//找到"view1_id"
        textView6 =(TextView) findViewById(R.id.view6);//找到"view2_id"
        textView7 =(TextView) findViewById(R.id.view7);//找到"view1_id"
        textView8 =(TextView) findViewById(R.id.view8);//找到"view2_id"
        textView9 =(TextView) findViewById(R.id.view9);//找到"view1_id"
        textView10 =(TextView) findViewById(R.id.view10);//找到"view2_id"
        btn2=(Button) findViewById(R.id.btn_2);//跳轉到預約介面
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)//按鈕監控
            {
                thread =new Thread(mutiThread);//按鈕"觸發"執行程式碼
                thread.start();//開始執行
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,turn.class);
                startActivity(intent);
            }
        });
    }
    //建立執行緒來取得網路
    //Android規定,連網路不能在"主程式"做執行,必進如果連不上網路系統就一直卡死在那邊
    //"多執行緒"表示能分開做事,類似分類任務一樣
    private Runnable mutiThread =new Runnable()
    {
        @Override
        public void run() {
            while (true) {
                try {
                    URL url = new URL("http://163.23.24.56/android_SQL.php");
                    //開始宣告HTTP連線需要的物件,並建立HttpURLConnection物件
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //設定連線方式"Post"
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);//允許輸出
                    connection.setDoInput(true);//允許讀入
                    connection.setUseCaches(false);//不使用快取
                    connection.connect();//開始連線
                    int responseCode = connection.getResponseCode();//建立取得回應的物件
                    if (responseCode == HttpURLConnection.HTTP_OK)//回傳OK
                    {   //取得輸入串流
                        InputStream inputStream = connection.getInputStream();
                        //取得輸入串流資料
                        BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                        String box = "";//存放字串
                        String line = null;//讀取字串
                        while ((line = bufReader.readLine()) != null) {
                            box += line + "\n";//每讀出一列,就存放字串後面
                        }
                        inputStream.close();//關閉輸入串流
                        result = box; //把box存入全域變數result裡
                    }
                } catch (Exception e) {
                    result = e.toString();//如果錯誤會回傳false,且利用"toString"轉成字串
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sensor_park(result);//傳到"sensor_park"的函式裡
                    }
                });
            }
        }
    };
    private void sensor_park(String progress)
    {
        char num[] ;
        char num1[] = new char[100];
        int a=0;
        num =progress.toCharArray();//用char陣列存"result"的string
        for(int i=0;i<progress.length();i++)
        {
            if(num[i]=='o'||num[i]=='f')
            {
                num1[a]=num[i];//把"o"跟"f"存到新的char陣列
                a++;//每存一次變數加一次
            }
        }
        if (num1[0] == 'f') //代表有車
        {
            textView1.setBackgroundColor(Color.rgb(240, 168, 224));
            textView1.setText("停滿");
        }
        else
        {
            textView1.setBackgroundColor(Color.rgb(165, 201, 243));
            textView1.setText("空的");
        }
        if (num1[1] == 'f')
        {
            textView2.setBackgroundColor(Color.rgb(240, 168, 224));
            textView2.setText("停滿");
        }
        else
        {
            textView2.setBackgroundColor(Color.rgb(165, 201, 243));
            textView2.setText("空的");
        }



        if (num1[2] == 'f') //代表有車
        {
            textView3.setBackgroundColor(Color.rgb(240, 168, 224));
            textView3.setText("停滿");
        }
        else
        {
            textView3.setBackgroundColor(Color.rgb(165, 201, 243));
            textView3.setText("空的");
        }
        if (num1[3] == 'f')
        {
            textView4.setBackgroundColor(Color.rgb(240, 168, 224));
            textView4.setText("停滿");
        }
        else
        {
            textView4.setBackgroundColor(Color.rgb(165, 201, 243));
            textView4.setText("空的");
        }


        if (num1[4] == 'f') //代表有車
        {
            textView5.setBackgroundColor(Color.rgb(240, 168, 224));
            textView5.setText("停滿");
        }
        else
        {
            textView5.setBackgroundColor(Color.rgb(165, 201, 243));
            textView5.setText("空的");
        }
        if (num1[5] == 'f')
        {
            textView6.setBackgroundColor(Color.rgb(240, 168, 224));
            textView6.setText("停滿");
        }
        else
        {
            textView6.setBackgroundColor(Color.rgb(165, 201, 243));
            textView6.setText("空的");
        }


        if (num1[6] == 'f') //代表有車
        {
            textView7.setBackgroundColor(Color.rgb(240, 168, 224));
            textView7.setText("停滿");
        }
        else
        {
            textView7.setBackgroundColor(Color.rgb(165, 201, 243));
            textView7.setText("空的");
        }
        if (num1[7] == 'f')
        {
            textView8.setBackgroundColor(Color.rgb(240, 168, 224));
            textView8.setText("停滿");
        }
        else
        {
            textView8.setBackgroundColor(Color.rgb(165, 201, 243));
            textView8.setText("空的");
        }

        if (num1[8] == 'f') //代表有車
        {
            textView9.setBackgroundColor(Color.rgb(240, 168, 224));
            textView9.setText("停滿");
        }
        else
        {
            textView9.setBackgroundColor(Color.rgb(165, 201, 243));
            textView9.setText("空的");
        }
        if (num1[9] == 'f')
        {
            textView10.setBackgroundColor(Color.rgb(240, 168, 224));
            textView10.setText("停滿");
        }
        else
        {
            textView10.setBackgroundColor(Color.rgb(165, 201, 243));
            textView10.setText("空的");
        }


        /* String c=String.valueOf(num1[1]);
        Toast toast=Toast.makeText(MainActivity.this,c,Toast.LENGTH_LONG);
         toast.show(); */
    }


}
