package com.example.sectry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button lets_begin;
    private Button introduce;
    private Button sec_begin;
    private Button thi_begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lets_begin=(Button)findViewById(R.id.game_begin);
        lets_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        introduce=(Button)findViewById(R.id.introduce);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,IntroduceActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        sec_begin=(Button)findViewById(R.id.sec_game_begin);
        sec_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecgameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        thi_begin=(Button)findViewById(R.id.thi_game_begin);
        thi_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThigameActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
