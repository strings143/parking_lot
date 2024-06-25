package com.example.park;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class report extends AppCompatActivity {
        private Button btn_1;
        private TextView address;
        private TextView new_address;
    private TextView error_address;
    private ImageView over;
    private Button btn2;
        private String oldaddress="",newaddress="done";
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        btn_1=findViewById(R.id.btn_1);
        btn2=findViewById(R.id.btn_2);
        address=findViewById(R.id.text_1);
        new_address=findViewById(R.id.text_2);
        error_address=findViewById(R.id.text_3);
        btn_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                tempaddress();
                check();
            }

        });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(report.this,turn.class);
                        startActivity(intent);
                    }
                });
    }
    public void check()
    {
        final String url = "http://163.23.24.56/report.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    newaddress=object.getString("carid");
                    new_address.setText(newaddress);
                    new_address.setTextColor(Color.parseColor("#D68094"));
                    error_address.setText("車牌輸入錯誤");
                    error_address.setTextColor(Color.parseColor("#F1E1FF"));
                } catch (JSONException e) {
                    error_address.setText("車牌輸入錯誤");
                    error_address.setTextColor(Color.parseColor("#FF5151"));
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
                params.put("address",oldaddress);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    public void  tempaddress()
    {
        oldaddress=address.getText().toString().trim();
    }

}