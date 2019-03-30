package com.example.sectry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Create4card {
    public String card1="";
    public String card2="";
    public String card3="";
    public String card4="";
    public int num1;
    public int num2;
    public int num3;
    public int num4;
    public int[] num=new int[4];
    public ArrayList answerList;//可用表达式列表
    public static ArrayList showlist(String string){
        ArrayList returnlist=new ArrayList();
        char[] str=string.toCharArray();
        String exp="";
        for(int i=0;i<string.length();i++){
            if(!Character.isDigit(str[i])){
                exp+=str[i];
                if(i==string.length()-1){
                    returnlist.add(exp);
                }
            }
            else {
                if (exp != "") {
                    if(i==0){
                        returnlist.add(" ");
                    }
                    returnlist.add(exp);
                    exp="";
                }
            }
        }


        return returnlist;
    }// 分割表达式

    private void setnum(){
        this.num[0]=Math.abs(this.num1%13+1);
        this.num[1]=Math.abs(this.num2%13+1);
        this.num[2]=Math.abs(this.num3%13+1);
        this.num[3]=Math.abs(this.num4%13+1);
    }
    public static String getcardnum(int numa){
        String carda="";
        switch ((int)((numa-1)/13)){
            case 0:carda="t"+String.valueOf(numa%13+1);break;
            case 1:carda="f"+String.valueOf(numa%13+1);break;
            case 2:carda="h"+String.valueOf(numa%13+1);break;
            case 3:carda="m"+String.valueOf(numa%13+1);break;
            default:break;
        }
        return carda;
    }

    Create4card(){
    }
    public void init(){
        Createstr createstr = new Createstr();
        //do {
            num1 = (int) (1 + Math.random() * 52);
            do {
                num2 = (int) (1 + Math.random() * 52);
            } while (num1 == num2);
            do {
                num3 = (int) (1 + Math.random() * 52);
            } while (num1 == num3 || num2 == num3);
            do {
                num4 = (int) (1 + Math.random() * 52);
            } while (num1 == num4 || num2 == num4 || num3 == num4);
            setnum();
            createstr.setAllstr(num);
            this.answerList=createstr.getAnswer();
        //}while (this.answerList.size()==0);
        this.card1=getcardnum(num1);
        this.card2=getcardnum(num2);
        this.card3=getcardnum(num3);
        this.card4=getcardnum(num4);
    }
}

