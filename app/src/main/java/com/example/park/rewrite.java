package com.example.park;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class rewrite extends AppCompatActivity {
    private TextView oldpassword;
    private TextView newpassword;
    private TextView oldaddress;
    private TextView newaddress;
    private TextView errorpassword;
    private TextView erroraddress;
    private Button btn_password;
    private Button btn_address;
    private TextView show1;
    private TextView show2;
    private Button btn3;
    private Button btn4;
    String tempaddress="",temppassword="",password="",address="",tempaccount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewrite);
        SharedPreferences sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        tempaccount=sharedPreferences.getString("Saved", "tempaccount");
        btn3=findViewById(R.id.btn_3);
        btn4=findViewById(R.id.btn_4);
        oldpassword=findViewById(R.id.mtext_1);
        newpassword=findViewById(R.id.mtext_2);
        oldaddress=findViewById(R.id.mtext_3);
        newaddress=findViewById(R.id.mtext_4);
        errorpassword=findViewById(R.id.tmp1);
        erroraddress=findViewById(R.id.tmp2);
        btn_password=findViewById(R.id.btn_1);
        btn_address=findViewById(R.id.btn_2);
        show1=findViewById(R.id.tmp1);
        show2=findViewById(R.id.tmp2);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(rewrite.this,turn.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(rewrite.this,turn.class);
                startActivity(intent);
            }
        });
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata_1();
                sendpassword();
            }
        });
        btn_address.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getdata_2();
                sendaddress();
            }
        });
    }
    public void sendpassword()
    {
        final String url = "http://163.23.24.56/rewrite_1.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    if(password=="done"||temppassword=="done")
                    {
                        Toast.makeText(rewrite.this,"不得為空值",Toast.LENGTH_LONG).show();
                    }
                   else if(object.getBoolean("error"))
                    {
                        show1.setText("舊密碼輸入錯誤或新密碼重複");
                        show1.setTextColor(Color.parseColor("#FF5151"));
                    }
                    else
                    {
                         show();
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
                params.put("oldpassword",temppassword);
                params.put("newpassword",password);
                params.put("account",tempaccount);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    public void sendaddress()
    {
        final String url = "http://163.23.24.56/rewrite_2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    if(address=="done"||tempaddress=="done")
                    {
                        Toast.makeText(rewrite.this,"不得為空值",Toast.LENGTH_LONG).show();
                    }
                   else if(object.getBoolean("error"))
                    {

                       show2.setText("舊車牌輸入錯誤或新車牌重複");
                       show2.setTextColor(Color.parseColor("#FF5151"));
                    }
                    else
                    {
                        show();
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
                params.put("oldaddress",tempaddress);
                params.put("newaddress",address);
                params.put("account",tempaccount);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    public void show()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(rewrite.this);
        dialog.setTitle("修改成功!!");
        dialog.setMessage("");
        dialog.setMessage("");
        dialog.setMessage("修改完成囉,請按'返回'鍵,謝謝~");
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(rewrite.this,rewrite.class);
                startActivity(intent);
            }
        };
        dialog.setPositiveButton("返回", OkClick);
        dialog.setCancelable(false);
        dialog.show();

    }
    public void getdata_1()
    {
        temppassword=oldpassword.getText().toString().trim();
        password=newpassword.getText().toString().trim();
        if(newpassword.getText().toString().trim().matches("")||oldpassword.getText().toString().trim().matches(""))
        {
            password="done";
            temppassword="done";
        }
    }
    public void getdata_2()
    {
        tempaddress=oldaddress.getText().toString().trim();
        address=newaddress.getText().toString().trim();
        if(oldaddress.getText().toString().trim().matches("")||newaddress.getText().toString().trim().matches(""))
        {
            tempaddress="done";
            address="done";
        }
    }
}