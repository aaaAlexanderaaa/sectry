package com.example.sectry;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {
    private Button back_to_main;
    private EditText editText;
    private Button play;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private TextView information;
    private TextView score;
    private Button next;
    private Button showone;
    private Button showall;
    private TextView answerview;
    private TextView timelimit;

    private int scorenum;//分数
    private int quenum;//题数
    private static String[] howaboutscore={
            "你这车开的也太快了趴!",
            "你可真skr小天才",
            "哦呦,不错呦",
            "高手,这是高手",
            "你还能做得更好",
            "你这技术也就中等意思吧",
            "感觉我妹妹都能比你van得好",
            "emmm,其实这些题不难吧",
            "我ball ball你了,认真点玩吧",
            "哇,你是认真的吗?",
            "算了,你可能不太适合这种游戏"};//分数评价

    private Create4card create4card=new Create4card();//初始创建一个全局的用于创建四个数的对象
    private int gamemood =0;//0:没有开始,1:提交
    private int maxtime=60;//计时器时间

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        back_to_main=(Button)findViewById(R.id.back_to_main2);
        showone=(Button)findViewById(R.id.showone);
        showall=(Button)findViewById(R.id.showall);
        next=(Button)findViewById(R.id.next);
        play=(Button)findViewById(R.id.game_button);
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        img4=(ImageView)findViewById(R.id.img4);
        editText=(EditText)findViewById(R.id.editText);
        information=(TextView)findViewById(R.id.information);
        score=(TextView)findViewById(R.id.score);
        answerview=(TextView)findViewById(R.id.answerview);
        timelimit=(TextView)findViewById(R.id.limittime);
//view全部初始化
        final CountDownTimer timer = new CountDownTimer(maxtime*1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                timelimit.setText((int)millisUntilFinished/1000+" : "+(int) ((millisUntilFinished%1000)/10));
            }

            @Override
            public void onFinish() {

                timelimit.setText("00 : 00");
            }
        };
//接下来设置监听事件

        resetGamemood();
        scorenum=0;
        quenum=0;

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGamemood();
                String scorestr = setscorestr();
                score.setText(scorestr);//分数不消掉
                answerview.setText(" ");//清空答案列表
                information.setText(" ");//清空提示信息
                editText.setText(" ");//清空输入
                play.performClick();
            }
        });

        showone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamemood==1) {
                    timer.cancel();
                    answerview.setText("");
                    answerview.append(create4card.answerList.get(0).toString());
                }
            }
        });
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gamemood==1) {
                    timer.cancel();
                    if (create4card.answerList.size() > 1) {
                        answerview.setText("");
                        int i = 0;
                        for (i = 0; i + 1 < create4card.answerList.size(); i += 2) {
                            answerview.append(create4card.answerList.get(i).toString());
                            answerview.append("\t\t\t");
                            answerview.append(create4card.answerList.get(i + 1).toString());
                            answerview.append("\n");
                        }
                        if (i - 1 < create4card.answerList.size()) {
                            answerview.append(create4card.answerList.get(i - 1).toString());
                        }
                    } else {
                        showone.performClick();
                    }
                }
            }
        });

        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(GameActivity.this,MainActivity.class);
                GameActivity.this.startActivity(intent);
                GameActivity.this.finish();
            }
        });



        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gamemood==0){
                    try {
                        create4card.init();
                        quenum+=1;
                        while (create4card.answerList.size()==0) {
                            create4card.init();
                        }
                        img1.setImageResource(getResources().getIdentifier(create4card.card1, "drawable", "com.example.sectry"));
                        img2.setImageResource(getResources().getIdentifier(create4card.card2, "drawable", "com.example.sectry"));
                        img3.setImageResource(getResources().getIdentifier(create4card.card3, "drawable", "com.example.sectry"));
                        img4.setImageResource(getResources().getIdentifier(create4card.card4, "drawable", "com.example.sectry"));
                        timer.start();
                        play.setText("我算好了!");
                        information.setText("");
                        answerview.setText("");
                        gamemood = 1;
                    }catch (Exception e){
                        information.setText("生成出错");
                    }
                }
                else if(gamemood==1){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0) ;//每提交一次答案收回一次软键盘

                    String submit=editText.getText().toString();
                    if(submit.isEmpty()){
                        information.setText("请不要提交空字符串");
                    }
                    switch (Calculate.iscorrert(submit,create4card)){
                        case 0:{
                            float result;
                            try{
                                result = (float)Calculate.eval(submit);
                                if(result==24){
                                    timer.cancel();
                                    information.setText("恭喜你！答对了！");
                                    if(answerview.getText().toString().isEmpty()&&timelimit.getText().toString()!="00 ; 00"){scorenum+=1;}
                                    String scorestr = setscorestr();
                                    editText.setText("");
                                    score.setText(scorestr);
                                    resetGamemood();
                                    play.setText("再来一次!");
                                }
                                else {
                                    information.setText("很遗憾，你的答案或许有点问题。");
                                }
                            }catch (Exception e){
                                information.setText("很遗憾，你的答案或许有点问题。");
                            }
                        }break;
                        case 1:information.setText("哦呦，你输入的字符串有非法字符呦!");break;
                        case 2:information.setText("emmm，再检查一下是不是按顺序输入了呢？");break;
                        case 3:information.setText("好像你输入的字符有点多耶...");break;
                        case 4:information.setText("请输入非空字符串！");break;
                    }
                }
            }
        });

    }
    private void resetGamemood(){
        gamemood=0;
    }
    private int getimgid(String address){
        return getResources().getIdentifier(address, "drawable", "com.example.sectry");
    }
    private String setscorestr(){
        String scorestr = "在过去的"+String.valueOf(quenum)+"题中,您答对了"+ String.valueOf(scorenum)+" 道题";
        if(quenum<=3){
            scorestr+="\n要继续加油哦";
        }
        else {
            int i=quenum-scorenum;
            if(i>10){i=10;}
            scorestr=scorestr+"\n"+howaboutscore[i];
        }
        return scorestr;
    }

}

