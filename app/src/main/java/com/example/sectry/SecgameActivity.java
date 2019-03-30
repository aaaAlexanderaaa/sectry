package com.example.sectry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
SecgameActivity逻辑:
    activity.oncreate:三秒倒计时,开始游戏
    timer倒计时打开//倒计时结束自动切换
    submit//
        gamemood==0:新一轮,初始化,
        gamemood==1:提交答案,计算是否成功

 */

public class SecgameActivity extends AppCompatActivity {
    private Button baktomain;
    private Button submit;
    private ImageView [] imageView=new ImageView[8];
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView information;
    private TextView score;
    private TextView time;
    private TextView playscore;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private ArrayList allimage=new ArrayList();

    private int[] correctnum=new int[8];
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
    private int maxtime=60;
    private int gamemood =0;//0:新一轮 1:提交

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secgame);
        baktomain = (Button) findViewById(R.id.sec_back_to_main);
        submit = (Button) findViewById(R.id.sec_submit);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        playscore=(TextView)findViewById(R.id.playscore);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        imageView[0] = (ImageView) findViewById(R.id.imageView1);
        imageView[1] = (ImageView) findViewById(R.id.imageView2);
        imageView[2] = (ImageView) findViewById(R.id.imageView3);
        imageView[3] = (ImageView) findViewById(R.id.imageView4);
        imageView[4] = (ImageView) findViewById(R.id.imageView5);
        imageView[5] = (ImageView) findViewById(R.id.imageView6);
        imageView[6] = (ImageView) findViewById(R.id.imageView7);
        imageView[7] = (ImageView) findViewById(R.id.imageView8);
        information = (TextView) findViewById(R.id.sec_information);
        score = (TextView) findViewById(R.id.sec_score);
        time = (TextView) findViewById(R.id.sec_time);

        scorenum=0;
        quenum=0;

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setText("");
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText2.setText("");
            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText3.setText("");
            }
        });
        editText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText4.setText("");
            }
        });

        baktomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SecgameActivity.this,MainActivity.class);
                SecgameActivity.this.startActivity(intent);
                SecgameActivity.this.finish();
            }
        });

        final CountDownTimer timer = new CountDownTimer((maxtime)*1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                String text1=(int)(millisUntilFinished/1000-quenum)+"";
                String text2=(int)(millisUntilFinished%1000/10)+"";
                if((int)millisUntilFinished/1000-quenum<10){text1="0"+text1;}
                if((int)millisUntilFinished%1000/10<10){text2="0"+text2;}
                time.setText(text1+" : "+text2);
                if(millisUntilFinished<(maxtime-3)*1000){
                    playscore.setText("");
                    information.setText("");
                }
                if((millisUntilFinished/1000-quenum)==0){
                    time.setText("00 : 00");
                    playscore.setText("菜!!!");
                    resetgamemood();
                    submit.performClick();
                }
            }

            @Override
            public void onFinish() {
                time.setText("00 : 00");
                playscore.setText("菜!!!");
                resetgamemood();
                submit.performClick();
            }
        };
        final CountDownTimer bigintimer = new CountDownTimer(4*1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                if(millisUntilFinished>3*1000) {
                    time.setText("准备");
                    time.setTextSize(60-(int)40*(4*1000-millisUntilFinished)/1000);
                }
                else {
                    time.setText((int) ((millisUntilFinished / 1000)+1)+"");
                }
            }


            @Override
            public void onFinish() {
                resetgamemood();
                submit.performClick();
            }
        }.start();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gamemood==0){
                    try {
                        for(int i=0;i<8;i++){
                            correctnum[i]=0;
                        }
                        score.setText(setscorestr());
                        create4card.init();
                        while (create4card.answerList.size()==0) {
                            create4card.init();
                        }
                        quenum+=1;
                        String[] allcard={create4card.card1,create4card.card2,create4card.card3,create4card.card4};
                        ArrayList randomimg=getrandam(8);
                        for(int i=0;i<8;i++){
                            if(i<4){
                                imageView[(int)randomimg.get(i)].setImageResource(getResources().getIdentifier(allcard[i],"drawable", "com.example.sectry"));
                                correctnum[i]=create4card.num[i];
                            }
                            else {
                                int nothing=(int) (1 + Math.random() * 52);
                                correctnum[i]=Math.abs(nothing%13+1);
                                String randomcard=Create4card.getcardnum(nothing);
                                imageView[(int)randomimg.get(i)].setImageResource(getResources().getIdentifier(randomcard,"drawable", "com.example.sectry"));
                            }
                        }//这里设置好了imageview
                        ArrayList textlist=Create4card.showlist((String) create4card.answerList.get(0));
                        textView1.setText((String)textlist.get(0));
                        textView2.setText((String)textlist.get(1));
                        textView3.setText((String)textlist.get(2));
                        textView4.setText((String)textlist.get(3));
                        textView5.setText((String)textlist.get(4));//这里设置好了计算表达式的文本信息
                        editText1.setText("");
                        editText2.setText("");
                        editText3.setText("");
                        editText4.setText("");
                        timer.start();
                        gamemood = 1;

                    }catch (Exception e){
                        information.setText("生成出错");
                    }
                }
                else if(gamemood==1){
                    String submitstr=textView1.getText().toString()+editText1.getText().toString()+textView2.getText().toString()
                            +editText2.getText().toString()+textView3.getText().toString()+editText3.getText().toString()
                            +textView4.getText().toString()+editText4.getText().toString()+textView5.getText().toString();
                    if(editText4.getText().toString().isEmpty()||editText3.getText().toString().isEmpty()||
                            editText2.getText().toString().isEmpty()||editText1.getText().toString().isEmpty()){
                        information.setText("请不要提交空字符串");
                    }

                    int[] unknow={Integer.valueOf(editText1.getText().toString()),Integer.valueOf(editText2.getText().toString()),
                            Integer.valueOf(editText3.getText().toString()),Integer.valueOf(editText4.getText().toString())};

                    int kk=0;
                    int[] knowm=correctnum.clone();
                    for (int i=0;i<4;i++){
                        for(int j=0;j<8;j++){
                            if(unknow[i]==knowm[j]){
                                knowm[j]=0;
                                kk=1;
                                break;
                            }
                        }
                        if(kk==0){
                            information.setText("你输入的数字好像出错啦");
                            return;
                        }
                        kk=0;
                    }

                    float result;
                    try{
                        result = (float)Calculate.eval(submitstr);
                        if(result==24){
                            information.setText("恭喜你！答对了！");
                            playscore.setText("Nice!");
                            scorenum+=1;
                            score.setText(setscorestr());
                            resetgamemood();
                            submit.performClick();
                        }
                        else {
                            information.setText("很遗憾，你的答案或许有点问题。");

                            resetgamemood();
                            submit.performClick();
                        }
                    }catch (Exception e){
                        information.setText("很遗憾，你的答案或许有点问题。");
                    }
                }
            }
        });

    }
    private void resetgamemood() {
        this.gamemood=0;
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
    private ArrayList getrandam(int nummax){
        ArrayList numreturn=new ArrayList();
        ArrayList numnow=new ArrayList();
        for(int i=0;i<nummax;i++){
            numnow.add((int)i);
        }
        for(int i=0;i<nummax;i++){
            int j=(int)(Math.random()*numnow.size());
            numreturn.add(numnow.get(j));
            numnow.remove(j);
        }
        return numreturn;
    }
}
