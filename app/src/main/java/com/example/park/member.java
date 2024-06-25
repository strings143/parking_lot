package com.example.park;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class member extends AppCompatActivity {
    private Button btn_return;
    private EditText Name;
    private EditText Address;
    private EditText Account;
    private EditText Password;
    private Button btn_send;
    String tempname,tempaddress,tempaccount,temppassword,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Name=(EditText)findViewById(R.id.mtext_4);
        Address=(EditText)findViewById(R.id.mtext_3);
        Account=(EditText)findViewById(R.id.mtext_1);
        Password=(EditText)findViewById(R.id.mtext_2);
        btn_send=(Button)findViewById(R.id.btn_1);
        btn_return=(Button)findViewById(R.id.btn_2);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(member.this,join.class);
                startActivity(intent);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                check();
                if(tempname=="done"||tempaddress=="done"||temppassword=="done"|| tempaccount=="done")
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(member.this);
                    dialog.setTitle("忘記輸入資料囉~請重新輸入一次");
                    dialog.setMessage("");
                    dialog.setMessage("");
                    dialog.setMessage("請點選'確認'鍵");
                    DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                         {
                             Intent intent = new Intent(member.this, member.class);
                             startActivity(intent);
                         }
                    };
                    dialog.setPositiveButton("確認", OkClick);
                    dialog.setCancelable(false);
                    dialog.show();
                }
                else
                {
                    senddata();
                    ok();
                }
            }
        });
    }
    public void getdata()
    {
        tempname=Name.getText().toString().trim();
        tempaddress=Address.getText().toString().trim();
        tempaccount=Account.getText().toString().trim();
        temppassword=Password.getText().toString().trim();
    }
    public void check() {
        if (Name.getText().toString().matches("")||Address.getText().toString().matches("")||Account.getText().toString().matches("")||Password.getText().toString().matches(""))
        {
            tempname="done";
            tempaddress="done";
            temppassword="done";
            tempaccount="done";
        }
    }
    public void senddata()
    {
        //用Volley的方式傳送,但缺點不能傳大量的資料
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://163.23.24.56/application.php";
        //log.d視窗 會顯示 "成功"=車位,"失敗"=ERROR
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
                params.put("name", tempname);
                params.put("address", tempaddress);
                params.put("account", tempaccount);
                params.put("password", temppassword);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void ok()
    {
            AlertDialog.Builder dialog = new AlertDialog.Builder(member.this);
            dialog.setTitle("恭喜註冊成功!!ya~ya~");
            dialog.setMessage("");
            dialog.setMessage("");
            dialog.setMessage("請點選'返回'鍵,回到登入頁面");
            DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(member.this,join.class);
                    startActivity(intent);
                }
            };
            dialog.setPositiveButton("返回", OkClick);
            dialog.setCancelable(false);
            dialog.show();
    }
}