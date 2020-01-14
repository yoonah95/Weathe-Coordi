package com.example.parkeunjeong.mycoordinator;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
public class ResultActivity extends AppCompatActivity {

    private Button goBoard;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        goBoard = (Button)findViewById(R.id.goBoard_btn);
        goBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("Aclothes",MODE_PRIVATE);
        int tops = pref.getInt("shirt",0);
        int pantss = pref.getInt("pants",0);

        ImageView top = (ImageView) findViewById(R.id.top);
        ImageView pants = (ImageView) findViewById(R.id.pants);
        top.setImageResource(tops);
        pants.setImageResource(pantss);


    }
}