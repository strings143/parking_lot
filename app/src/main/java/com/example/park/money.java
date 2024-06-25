package com.example.park;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class money extends AppCompatActivity {

    private Button btn_1;
    private  Button back;
    private ImageView qr_code;
    String plate,account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        btn_1=findViewById(R.id.btn_1);
        back=findViewById(R.id.btn_2);
        qr_code=findViewById(R.id.qr_code);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(money.this,turn.class);
                startActivity(intent);
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();
                get();
            }
        });
    }
    public void data()
    {
        Intent intent=getIntent();
        account=intent.getStringExtra("account");
    }
    public void get()
    {

        final String url = "http://163.23.24.56/plate.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    plate =object.getString("plate");
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    try {
                        Bitmap bit = encoder.encodeBitmap(plate, BarcodeFormat.QR_CODE,
                                400, 400);
                        qr_code.setImageBitmap(bit);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("account",account);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}