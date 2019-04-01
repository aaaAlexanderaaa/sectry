package com.example.sectry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ThigameActivity extends AppCompatActivity {

    private ImageView[] imageView =new ImageView[4];
    private TextView textView;
    private EditText editText;
    private Button play;
    private Button back;
    private Button next;

    private int gamemood=0;
    private Create4card create4card=new Create4card();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thigame);
        imageView[0]=(ImageView)findViewById(R.id.thi_img1);
        imageView[1]=(ImageView)findViewById(R.id.thi_img2);
        imageView[2]=(ImageView)findViewById(R.id.thi_img3);
        imageView[3]=(ImageView)findViewById(R.id.thi_img4);
        textView=(TextView)findViewById(R.id.thi_textView);
        editText=(EditText)findViewById(R.id.thi_editText);
        play=(Button)findViewById(R.id.thi_game_button);
        back=(Button)findViewById(R.id.thi_back_to_main);
        next=(Button)findViewById(R.id.thi_next);

        resetGamemood();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ThigameActivity.this,MainActivity.class);
                ThigameActivity.this.startActivity(intent);
                ThigameActivity.this.finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGamemood();
                textView.setText(" ");//清空提示信息
                editText.setText(" ");//清空输入
                play.performClick();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gamemood==0){
                    create4card.init();
                    while (create4card.answerList.size()==0||create4card.num[0]>10||create4card.num[1]>10||create4card.num[2]>10||create4card.num[3]>10) {
                        create4card.init();
                    }
                    String[] allcard={create4card.card1,create4card.card2,create4card.card3,create4card.card4};
                    ArrayList randomimg=getrandam(4);
                    for(int i=0;i<4;i++) {
                        imageView[(int) randomimg.get(i)].setImageResource(getResources().getIdentifier(allcard[i], "drawable", "com.example.sectry"));
                    }
                    gamemood=1;
                }

                else if(gamemood==1){
                    textView.setText("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0) ;//每提交一次答案收回一次软键盘

                    String submit=editText.getText().toString();
                    if(!submit.isEmpty()){
                        if(iscorrert(submit,create4card)==0){
                            try {
                                if(Calculate.eval(submit)==24.0){
                                    textView.setText("Congratulation!");
                                    resetGamemood();
                                }
                                else {
                                    textView.setText("Incorrect!");
                                }
                            }catch (Exception e){
                                textView.setText("You input is illegal!");
                            }
                        }
                        else {
                            textView.setText("You input is illegal!");
                        }
                    }
                }
            }
        });
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
    private void resetGamemood(){
        gamemood=0;
    }
    private int iscorrert(String string,Create4card create4card){
        string+=" ";
        char[] correctch ={'+','-','*','/','1','2','3','4','5','6','7','8','9','0',' ','(',')'};
        int[] known=create4card.num.clone();
        if(string.isEmpty()){
            return 4;//空字符串
        }
        char[] ch = string.toCharArray();
        String nownum= "";
        int k=0;//是否对应correctch数组中的值
        for(int i=0;i<string.length();i++){
            for (int j=0;j<correctch.length;j++){
                if(ch[i]==correctch[j]){
                    k=1;
                }
            }
            if(k==0){
                return 1;//第一类错误,非法字符
            }
        }
        int t=0;//数字序号
        int kk=0;//
        for(int i=0;i<string.length();i++){
            if(Character.isDigit(ch[i])){
                if(t>=4){
                    return 3;//第三类错误,输入多余数字
                }
                nownum=nownum + ch[i];
            }
            else {
                if(nownum!="") {
                    for (int p=0;p<4;p++){
                        if(Integer.valueOf(nownum)==known[p]){
                            known[p]=0;
                            kk=1;
                            break;
                        }
                    }
                    if(kk==0) {
                        return 2;
                    }
                    kk=0;
                    nownum = "";
                    t += 1;
                }
            }
        }
        return 0;
    }
}

