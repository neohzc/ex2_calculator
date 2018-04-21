package com.example.neoh.ex2_calculator;

import android.util.Log;

import java.math.BigDecimal;
import java.util.*;


public class Calculate {
    private final static String TAG = "CALCILATING:";
    static char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    static char ope[] = {'=', '+', '-', '×', '÷', '(', ')'};


    private String st_Pln;
    private String st_Rst;

    Calculate(String s1) {
        st_Pln = s1;
        st_Rst = "";
    }

    public String Calculat() {
        //接受输入的表达式
        //st_Rst=st_Pln;
        //转换为后缀表达式
        String[] strings = new String[128];
        strings = toPostfix();

        st_Pln = "";
        for (int i = 0; i < strings.length; i++) {
            st_Pln += strings[i];
        }

        //return st_Pln;
        //用后缀表达式进行计算
        doCalculate(strings);

        return getSt_Rst();
    }


    private String[] toPostfix() {
        //转换为后缀表达式
        String pln = st_Pln.replace("+", "|+|");
        pln = pln.replace("-", "|-|");
        pln = pln.replace("×", "|×|");
        pln = pln.replace("÷", "|÷|");
        pln = pln.replace("=", "|=");

        String[] strings = pln.split("\\|");
        String[] pxF = new String[strings.length];
        Stack<String> ope = new Stack<String>();
        // return  strings;

        int t = 0;
        for (int pos = 0; pos < strings.length; pos++) {
            if (isOperator(strings[pos].charAt(0))) {
                if (ope.empty() || higherPre(ope.peek().charAt(0), strings[pos].charAt(0)))
                    ope.push(strings[pos]);
                else {
                    while (!ope.empty() &&
                            !higherPre(ope.peek().charAt(0), strings[pos].charAt(0))) {
                        pxF[t++] = String.valueOf(ope.pop());
                    }
                    ope.push(strings[pos]);
                }

            } else {
                pxF[t++] = strings[pos];
            }
        }
        if (!ope.empty())
            if (ope.peek().equals("="))
                pxF[t] = ope.pop();

        return pxF;
    }

    private void doCalculate(String[] strings) {
        Stack<Double> num = new Stack<Double>();
        double a = 0, b = 0;
        for (int pos = 0; pos < strings.length-1; pos++) {
            if (isOperator(strings[pos].charAt(0))) {
                b = num.pop();
                a = num.pop();
                num.push(doOperator(a,b,strings[pos]));
            } else
                num.push(Double.valueOf(strings[pos]));
        }
        if(num.size()==1)
            st_Rst=String.valueOf(num.pop());
    }

    //基本运算操作函数，真正运算的地方
    private double doOperator(double a, double b, String c) {
        BigDecimal b1 = new BigDecimal(Double.toString(a));
        BigDecimal b2 = new BigDecimal(Double.toString(b));

        switch (c) {
            case "+":
                return b1.add(b2).doubleValue();
            case "-":
                return  b1.subtract(b2).doubleValue();
            case "×":
                return b1.multiply(b2).doubleValue();
            case "÷":
                return b1.divide(b2,10,BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return -1;
    }

    private boolean isOperator(char c)//如果输入的为运算符返回真
    {
        for (int i = 0; i < ope.length; i++) {
            if (c == ope[i])
                return true;
        }
        return false;
    }

    public String getSt_Rst() {
        return st_Rst;
    }

    private boolean higherPre(char top, char now) {
        /*
         * 比较当前扫描到的运算符是否比栈顶运算符优先级高
         * 高则压栈  return 1 ，等或低则出栈-1
         * */
        char ope[] = {'=', ')', '+', '-', '×', '÷', '('};
        int it = 0, in = 0;
        switch (top) {
            case '=':
                it = 0;
                break;
            case '+':
                it = 1;
                break;
            case '-':
                it = 1;
                break;
            case '×':
                it = 2;
                break;
            case '÷':
                it = 2;
                break;
            case '(':
                it = 3;
                break;
            case ')':
                it = 3;
                break;
        }
        switch (now) {
            case '=':
                in = 0;
                break;
            case '+':
                in = 1;
                break;
            case '-':
                in = 1;
                break;
            case '×':
                in = 2;
                break;
            case '÷':
                in = 2;
                break;
            case '(':
                in = 3;
                break;
            case ')':
                in = 3;
                break;
        }
        if (it < in) return true;
        return false;
    }

}


/*
    private String[] toPostfix() {
        //转换为后缀表达式


        String[] s = new String[128];

        int pos = 0;
        int stPos = 0;//string数组中的位置

        double d_Rst = 0;
        double a = 0, b = 0;
        int LockDot = 0;//0-整数位未锁定，1-已锁定
        double mvDot = 1;//移动小数点

        Stack<Character> ope = new Stack<Character>();


        while (st_Pln.charAt(pos++) != '=') {
            if (isNum(st_Pln.charAt(pos))) {
                if (LockDot == 0) {
                    if (st_Pln.charAt(pos) != '.')
                        a = a * 10 + st_Pln.charAt(pos) - '0';
                    else if (st_Pln.charAt(pos) == '.') {
                        LockDot = 1;
                    }
                } else if (LockDot == 1) {
                    if (st_Pln.charAt(pos) != '.'){
                        mvDot *= 10;
                        a += ( st_Pln.charAt(pos) - '0')/mvDot;
                    }
                    if(isOperator(st_Pln.charAt(pos+1))){
                        s[stPos++]=String.valueOf(a);
                        a=0;
                        LockDot = 0;
                        mvDot = 1;
                    }
                }

            } else if (isOperator(st_Pln.charAt(pos))) {
                pln[stPos++] = String.valueOf(a);
                if (ope.empty())
                    ope.push(st_Pln.charAt(pos));
                else if (higherPre(ope.peek(), st_Pln.charAt(pos)))
                    ope.push(st_Pln.charAt(pos));
                else {
                    pln[stPos++] = String.valueOf(ope.pop());
                    ope.push(st_Pln.charAt(pos));
                }
            } else
                return false;

            return s;
        }
    }

}*/

/*
    public boolean cal() {


        int pos = 0;
        int stPos = 0;//string数组中的位置
        double d_Rst = 0;
        double a = 0, b = 0;
        int LockDot = 0;//0-整数位未锁定，1-已锁定
        Stack<Double> number = new Stack<Double>();
        Stack<Character> ope = new Stack<Character>();
        //转换为后缀表达式


        while (st_Pln.charAt(pos) != '=') {
            if (isNum(st_Pln.charAt(pos))) {
                if (LockDot == 0) {
                    if (st_Pln.charAt(pos) != '.')
                        a = a * 10 + st_Pln.charAt(pos) - '0';
                    else if (st_Pln.charAt(pos) == '.') {
                        LockDot = 1;
                    } else
                        return false;
                } else if (LockDot == 1) {
                    if (st_Pln.charAt(pos) != '.')
                        a += (st_Pln.charAt(pos) - '0') / 10;
                    else if (st_Pln.charAt(pos) == '.') {
                        return false;
                    } else
                        return false;
                }

            } else if (isOperator(st_Pln.charAt(pos))) {
                pln[stPos++] = String.valueOf(a);
                if (ope.empty())
                    ope.push(st_Pln.charAt(pos));
                else if (higherPre(ope.peek(), st_Pln.charAt(pos)))
                    ope.push(st_Pln.charAt(pos));
                else {
                    pln[stPos++] = String.valueOf(ope.pop());
                    ope.push(st_Pln.charAt(pos));
                }
            } else
                return false;
        }
        st_Rst.concat(pln[0]);
        //
        // for (int i = 0; i < stPos; i++) {
        //     st_Rst.concat(pln[i]);
        // }


        return true;
    }*/


  /*
   public double calculate(String s)//直接计算值
    {
        int i = 0;

        double sum = 0;
        double a, b;
        char c;
        Stack<Double> number = new Stack<Double>();
        Stack<Character> ope = new Stack<Character>();
        // ope.push('=');
        while (s.charAt(i) != '=' || ope.peek() != '=') {
            int point = 0;
            double t0 = 1;
            if (isOperator(s.charAt(i))) {
                point = 0;
                t0 = 1;
            }
            if (isOperator(s.charAt(i)) || s.charAt(i) == '=') {

                number.push(sum);
                switch (precede(ope.peek(), s.charAt(i))) {
                    case -1:
                        ope.push(s.charAt(i));//新运算符优先级较小，将新运算符压栈处理
                        i++;
                        break;
                    case 0:
                        ope.pop();
                        i++;
                        break;
                    case 1:
                        b = number.peek();
                        number.pop();
                        a = number.peek();
                        number.pop();
                        c = ope.peek();
                        ope.pop();
                        number.push(goOperator(a, b, c));
                        break;

                }
            } else if (isNum(s.charAt(i))) {
                double t;
                while (('0' <= s.charAt(i) && s.charAt(i) <= '9') || s.charAt(i) == '.') {
                    if (s.charAt(i) == '.') {
                        point++;
                        i++;
                        continue;
                    }
                    if (point == 0) {
                        t = s.charAt(i) - '0';
                        sum = sum * 10 + t;
                    } else {
                        t = s.charAt(i) - '0';
                        t0 = t0 * 10;
                        sum = sum + t / t0;
                    }
                    i++;
                }
            }

        }
        return number.peek();
    }




   private int precede(char c1, char c2) {//比较两运算符的优先级大小1

        if (c1 == '+' || c1 == '-') {
            if (c2 == '+' || c2 == '-' || c2 == '=' || c2 == ')') return 1;
            else return -1;
        } else if (c1 == '*' || c1 == '/') {
            if (c2 == '(') return -1;
            else return 1;
        } else if (c1 == '(') {
            if (c2 == ')') return 0;
            else return -1;
        } else if (c1 == ')') {
            return 1;
        } else if (c1 == '=') {
            if (c2 == '=') return 0;
            else return -1;
        }
        return 0;
    }





   public boolean isRight(String s) {
        for (int i = 0; i < s.length() - 1; i++)//对等号前面的每一个字符
        {
            if (!isNum(s.charAt(i)) && !isOperator(s.charAt(i)))//如果存在非数字或运算符输入
            {
                Log.i(TAG, "等式包含非法字符!");//提示输入非法
                return false;
            }
        }
        if (s.charAt(s.length() - 1) != '=')//如果最末尾不是等号结束
        {
            Log.i(TAG, " 等式未以等号结尾!");//提示要以等号结束
            return false;
        }
        Stack<Character> temp = new Stack<Character>();//临时栈
        char c1, c2;
        int j = 0;
        while (true) {
            c1 = s.charAt(j++);
            if (c1 == '(') temp.push(c1);//读入左括号将压栈
            else if (c1 == ')' && temp.empty() != true)
                temp.pop();//读入右括号且栈不为空出栈
            else if (c1 == ')' && temp.empty() == true) {//读入右括号但栈为空报错
                Log.i(TAG, "等式出现括号不匹配或有多余括号出现!");
                return false;
            }
            else if (c1 == '=' && temp.empty() != true) {//读入等号但栈仍未为空报错
                while (temp.empty() != true)
                    temp.pop();
                Log.i(TAG, "等式有多余括号出现!");
                return false;
            } else if (c1 == '=' && temp.empty() == true) break;////读入等号且栈已空结束
        }
        j = 0;
        int point = 0;
        if (s.charAt(0) == '.' || (isOperator(s.charAt(0)) && s.charAt(0) != '(')) {//如果输入第一个字符为小数点或非左括号的运算符即报错
            Log.i(TAG, "等式第一个位置出现错误!");
            return false;
        } else temp.push(s.charAt(j++));//对未匹配的数字项进行压栈处理
        while (true) {
            if (j == s.length()) break;//当已经到字符串非等号最后位置退出循环
            c1 = s.charAt(j++);
            c2 = temp.peek();
            if (isOperator(c2)) point = 0;//对不同的操作符根据运算优先级出入栈
            if (c2 == '(' && (isOperator(c1) && (c1 != '(') || c1 == '=' || c1 == '.')) {
                return false;
            } else if (isNum(c2) && (c1 == '(')) {
                return false;
            } else if (c2 == '.' && (c1 == '.' || c1 == '=' || isOperator(c1))) {
                return false;
            } else if (isNum(c2) && c2 != '.' && c1 == '.') {
                point++;
                if (point > 1) return false;
                else temp.push(c1);
            } else if (c2 != '(' && c2 != ')' && isOperator(c2) && (c1 == '.' || c1 == '=' || (isOperator(c1) && c1 != '('))) {
                return false;
            } else if (c2 == ')' && (c1 == '(' || isNum(c1))) {
                return false;
            } else {
                temp.push(c1);
            }
        }
        return true;
    }*/
