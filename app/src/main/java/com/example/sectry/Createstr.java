package com.example.sectry;

import java.util.ArrayList;

public class Createstr {
    private  ArrayList answer=new ArrayList();
    private static String[] operrate={"+","-","*","/"};
    private static String[][] order={
        {"(((",")",")",")"},
        {"((",")","(","))"},
        {"(","((",")","))"},
        {"((","(","))",")"},
        {"(","(","(",")))"}
    };
    private ArrayList allstr=new ArrayList();
    public void setAllstr(int[] number){
        for(int i=0;i<5;i++){//按照运算顺序
            for(int j=0;j<operrate.length;j++){//按第一个操作
                for (int k=0;k<operrate.length;k++){//第二个
                    for (int l=0;l<operrate.length;l++){//第三个
                            switch (i){
                                case 0:{
                                    this.allstr.add(order[i][0]+number[0]+operrate[j]+number[1]+order[i][1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                    this.allstr.add(order[i][0]+"-"+number[0]+operrate[j]+number[1]+order[i][1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                }break;
                                case 1:{
                                    this.allstr.add(order[i][0]+number[0]+operrate[j]+number[1]+order[i][1]+operrate[k]+order[i][2]+number[2]+operrate[l]+number[3]+order[i][3]+"");
                                    this.allstr.add(order[i][0]+"-"+number[0]+operrate[j]+number[1]+order[i][1]+operrate[k]+order[i][2]+number[2]+operrate[l]+number[3]+order[i][3]+"");
                                }break;
                                case 2:{
                                    this.allstr.add(order[i][0]+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                    this.allstr.add(order[i][0]+"-"+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                }break;
                                case 3:{
                                    this.allstr.add(order[i][0]+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                    this.allstr.add(order[i][0]+"-"+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+number[2]+order[i][2]+operrate[l]+number[3]+order[i][3]+"");
                                }break;
                                case 4:{
                                    this.allstr.add(order[i][0]+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+order[i][2]+number[2]+operrate[l]+number[3]+order[i][3]+"");
                                    this.allstr.add(order[i][0]+"-"+number[0]+operrate[j]+order[i][1]+number[1]+operrate[k]+order[i][2]+number[2]+operrate[l]+number[3]+order[i][3]+"");
                                }break;
                            }
                    }
                }
            }
        }
    }
    public ArrayList getAnswer(){
        for (Object string:allstr){
            if(Calculate.eval(string.toString())==24){
                this.answer.add(string);
            }
        }
        return this.answer;
    }
}
