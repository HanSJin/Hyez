package com.example.ohyeji.loginaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private API networkService;
    EditText user_email;
    EditText user_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_email = (EditText)findViewById(R.id.user_email);
        user_password = (EditText)findViewById(R.id.user_password);

        String BASE_URL = String.format("http://%s:%d", "drdiary.co.kr", 3000);
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient();
        GsonConverterFactory factory = GsonConverterFactory.create(gson);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(factory).build();
        networkService = retrofit.create(API.class);

        goLogin();
    }

    protected void onResume(){
        super.onResume();
        goLogin();
    }

    void goLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("mylogin", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        if(email != ""){
            Intent intent = new Intent(getApplicationContext(), SubActivity.class);
            startActivity(intent);
        }
    }

    void mOnClick (View v){
        Call<Members> c = networkService.LoginAction(user_email.getText().toString(), user_password.getText().toString());
        c.enqueue(new Callback<Members>() {
            @Override
            public void onResponse(Call<Members> call, Response<Members> response) {
                if(response.isSuccessful()){
                    Members field = response.body();
                    if(field.getEmail() == null || field.getPassword() == null){
                        Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("mylogin",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("login",true);
                        editor.putString("email",field.getEmail());
                        editor.putString("nickname",field.getNickname());
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Members> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
