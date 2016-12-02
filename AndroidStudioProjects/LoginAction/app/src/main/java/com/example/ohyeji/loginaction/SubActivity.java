package com.example.ohyeji.loginaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    Context main_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        SharedPreferences sharedPreferences = getSharedPreferences("mylogin", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        String nickname = sharedPreferences.getString("nickname","");
        TextView test = (TextView)findViewById(R.id.test);
        test.setText("email : " + email + "\nnickname : " + nickname);
    }
    void mOnClick(View v){
        SharedPreferences.Editor sharedPreferences = getSharedPreferences("mylogin", Context.MODE_PRIVATE).edit();
        sharedPreferences.remove("login");
        sharedPreferences.remove("email");
        sharedPreferences.remove("nickname");
        sharedPreferences.commit();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
