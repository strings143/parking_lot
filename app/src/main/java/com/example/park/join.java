package com.example.park;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class  join extends AppCompatActivity {
    private Button  register;
    private Button join_btn;
    private EditText account;
    private EditText password;
    private boolean isHideFirst = true;
    private ImageView imageView;
    String tempaccount,temppassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        register=(Button)findViewById(R.id.register);
        join_btn=(Button)findViewById(R.id.btn_join);
        imageView = (ImageView) findViewById(R.id.eyes);
        account=(EditText)findViewById(R.id.account);
        password=(EditText)findViewById(R.id.password);
        imageView.setOnClickListener(this::onClick);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(join.this,member.class);
                startActivity(intent);
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getdata();
                send();
            }
        });

    }
    //禁止返回,因為登出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.eyes:
                if (isHideFirst == true) {
                    imageView.setBackgroundColor(Color.rgb(255, 255, 255));
                    imageView.setImageResource(R.drawable.eyes_pic);//按一下睜眼
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();//顯示
                    password.setTransformationMethod(method1);
                    isHideFirst = false;

                } else {
                    imageView.setBackgroundColor(Color.rgb(255, 255, 255));
                    imageView.setImageResource(R.drawable.eyes_pic2);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();//隱藏
                    password.setTransformationMethod(method);
                    isHideFirst = true;

                }
                int index =  password.getText().toString().length();
                password.setSelection(index);
                break;
        }
    }

    public void getdata()
    {
       tempaccount=account.getText().toString().trim();//"trim()"開頭和結尾的空格去掉
       temppassword=password.getText().toString().trim() ;
       if(account.getText().toString().trim().matches("")||password.getText().toString().trim().matches(""))
       {
           tempaccount="done";
           temppassword="done";
       }
    }
    public void in()
    {
        Intent intent=new Intent(join.this,turn.class);
        startActivity(intent);
    }
    public void saveaccount()
    {
        SharedPreferences sharedPreferences= getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Saved",tempaccount);
        editor.commit();
    }
    public void send()
    {

        //final String url = "http://172.20.10.4/join.php";
        final String url = "http://163.23.24.56/join.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object=new JSONObject(response);
                    if(object.getBoolean("error")||tempaccount=="done"||temppassword=="done")
                    {
                        Toast.makeText(join.this,"請輸入正確的帳號和密碼",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        saveaccount();
                        in();
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
                params.put("password",temppassword);
                params.put("account",tempaccount);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}