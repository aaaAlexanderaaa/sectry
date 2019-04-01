package com.example.sectry;

import java.util.ArrayList;
import java.util.Stack;

public class Calculate {
    private static char[] correctch ={'+','-','*','/','1','2','3','4','5','6','7','8','9','0',' ','(',')'};

    public ArrayList<String> getStringList(String str) {
        ArrayList<String> result = new ArrayList<String>();
        String num = "";
        str+= " ";
        int k=0;//记录当前数字的字符第一位位置
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                if(i==0||!Character.isDigit(str.charAt(i-1))) {k=i;}
                num = num + str.charAt(i);
            } else {
                if (num != "") {
                    boolean x=  k!=0&&(str.charAt(k-1)=='-'||str.charAt(k-1)=='+')&&(k-1==0||(!Character.isDigit(str.charAt(k-2))&&str.charAt(k-2)!=')'&&str.charAt(k-2)!=' '));
                    if(x) {//这是一个代表正负号的+或-而非运算符
                        result.remove(result.size()-1);
                        result.add("(");
                        result.add(0+"");
                        result.add(str.charAt(k-1)+"");
                        result.add(num);
                        result.add(")");
                        num = "";
                    }
                    else {
                        result.add(num);
                        num = "";
                    }
                }
                if(str.charAt(i)==' '){continue;}
                result.add(str.charAt(i) + "");
            }
        }
        if (num != "") {
            boolean x=  k!=0&&(str.charAt(k-1)=='-'||str.charAt(k-1)=='+')&&(k-1==0||!Character.isDigit(str.charAt(k-2)));
            if(x) {//这是一个代表正负号的+或-而非运算符
                result.remove(result.size()-1);
                result.add("(");
                result.add(0+"");
                result.add(str.charAt(k-1)+"");
                result.add(num);
                result.add(")");
            }
            else {
                result.add(num);
            }
        }
        return result;
    }

    /**
     * 将中缀表达式转化为后缀表达式
     */
    public ArrayList<String> getPostOrder(ArrayList<String> inOrderList) {

        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if (Character.isDigit(inOrderList.get(i).charAt(0))) {
                result.add(inOrderList.get(i));
            } else {
                switch (inOrderList.get(i).charAt(0)) {
                    case '(':
                        stack.push(inOrderList.get(i));
                        break;
                    case ')':
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))) {
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * 计算后缀表达式
     */
    public float calculate(ArrayList<String> postOrder) {
        Stack stack = new Stack();
        for (int i = 0; i < postOrder.size(); i++) {
            if (Character.isDigit(postOrder.get(i).charAt(0))) {
                stack.push(Float.valueOf(postOrder.get(i)));
            } else {
                float back = (float) stack.pop();
                float front = (float) stack.pop();
                float res = 0;
                switch (postOrder.get(i).charAt(0)) {
                    case '+':
                        res = front + back;
                        break;
                    case '-':
                        res = front - back;
                        break;
                    case '*':
                        res = front * back;
                        break;
                    case '/':
                        res = front / back;
                        break;
                }
                stack.push(res);
            }
        }
        return (float) stack.pop();
    }

    /**
     * 比较运算符等级
     */
    public boolean compare(String peek, String cur) {
        if ("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        }
        return false;
    }
    public static int iscorrert(String string,Create4card create4card){
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
        for(int i=0;i<string.length();i++){
            if(Character.isDigit(ch[i])){
                if(t>=4){
                    return 3;//第三类错误,输入多余数字
                }
                nownum=nownum + ch[i];
            }
            else {
                if(nownum!="") {
                    if (Integer.valueOf(nownum) != create4card.num[t]) {
                        return 2;//第二类错误,错误输入数字
                    }
                    nownum = "";
                    t += 1;
                }
            }
        }
        return 0;
    }

    public static float eval(String string) {
        Calculate calculate = new Calculate();
        ArrayList result = calculate.getStringList(string);  //String转换为List
        result = calculate.getPostOrder(result);   //中缀变后缀
        float i = (float) calculate.calculate(result);   //计算
        return i;
    }

    /*public static void main(String[] args) {
        Calculate calculate = new Calculate();
        String s = "+3*9";
        ArrayList result = calculate.getStringList(s);  //String转换为List
        result = calculate.getPostOrder(result);   //中缀变后缀
        float i = calculate.calculate(result);
        //int i = (int)calculate.calculate(result);   //计算
        System.out.println(i);
    }*/
}
