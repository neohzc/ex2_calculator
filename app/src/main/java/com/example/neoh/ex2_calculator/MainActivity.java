package com.example.neoh.ex2_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigDecimal.*;

import android.text.SpannableString;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_0, bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, bt_9, bt_Dot;
    Button bt_Psd, bt_Add, bt_Mul, bt_Div, bt_Sub;
    Button bt_Del, bt_AC, bt_Eql;
    TextView tv_Show;

    static char num[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    static char ope[] = {'=', '+', '-', '×', '÷', '(', ')','%'};

    private String st_Hsty = new String("");//计算历史
    private String st_Pln = new String("");//当前计算式
    private String st_Rst = new String("");//计算结果
    private Calculate s = new Calculate("0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitViews();
        setListener(this);

    }

    private void InitViews() {
        bt_0 = findViewById(R.id.bt_0);
        bt_1 = findViewById(R.id.bt_1);
        bt_2 = findViewById(R.id.bt_2);
        bt_3 = findViewById(R.id.bt_3);
        bt_4 = findViewById(R.id.bt_4);
        bt_5 = findViewById(R.id.bt_5);
        bt_6 = findViewById(R.id.bt_6);
        bt_7 = findViewById(R.id.bt_7);
        bt_8 = findViewById(R.id.bt_8);
        bt_9 = findViewById(R.id.bt_9);
        bt_Dot = findViewById(R.id.bt_Dot);

        bt_Psd = findViewById(R.id.bt_Psd);
        bt_Add = findViewById(R.id.bt_Add);
        bt_Mul = findViewById(R.id.bt_Mul);
        bt_Div = findViewById(R.id.bt_Div);
        bt_Sub = findViewById(R.id.bt_Sub);

        bt_Del = findViewById(R.id.bt_Del);
        bt_AC = findViewById(R.id.bt_AC);
        bt_Eql = findViewById(R.id.bt_Eql);

        tv_Show = findViewById(R.id.tv_Show);
    }

    private void setListener(View.OnClickListener myLsn) {
        bt_0.setOnClickListener(myLsn);
        bt_1.setOnClickListener(myLsn);
        bt_2.setOnClickListener(myLsn);
        bt_3.setOnClickListener(myLsn);
        bt_4.setOnClickListener(myLsn);
        bt_5.setOnClickListener(myLsn);
        bt_6.setOnClickListener(myLsn);
        bt_7.setOnClickListener(myLsn);
        bt_8.setOnClickListener(myLsn);
        bt_9.setOnClickListener(myLsn);
        bt_Dot.setOnClickListener(myLsn);

        bt_Psd.setOnClickListener(myLsn);
        bt_Add.setOnClickListener(myLsn);
        bt_Mul.setOnClickListener(myLsn);
        bt_Div.setOnClickListener(myLsn);
        bt_Sub.setOnClickListener(myLsn);

        bt_Del.setOnClickListener(myLsn);
        bt_AC.setOnClickListener(myLsn);
        bt_Eql.setOnClickListener(myLsn);

        tv_Show = findViewById(R.id.tv_Show);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bt_0: doClick_0(); break;
            case R.id.bt_1: doClick_1(); break;
            case R.id.bt_2: doClick_2(); break;
            case R.id.bt_3: doClick_3(); break;
            case R.id.bt_4: doClick_4(); break;
            case R.id.bt_5: doClick_5(); break;
            case R.id.bt_6: doClick_6(); break;
            case R.id.bt_7: doClick_7(); break;//
            case R.id.bt_8: doClick_8(); break;
            case R.id.bt_9: doClick_9(); break;
            case R.id.bt_Dot:doClick_Dot();break;
            case R.id.bt_Add:doClick_add();break;
            case R.id.bt_Sub:doClick_sub();break;
            case R.id.bt_Mul:doClick_mul();break;
            case R.id.bt_Div:doClick_div();break;
            case R.id.bt_Psd:doClick_psd(); break;
            case R.id.bt_AC: doClick_AC(); break;
            case R.id.bt_Del:doClick_Del();break;
            case R.id.bt_Eql:doClick_Eql();break;

        }
    }

    private void refreshScreen() {
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        doCalculate();
        tv_Show.append(st_Rst);
    }

    private void doClick_Eql() {
        if (st_Pln.length() == 0)
            return;
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
        refreshScreen();

        st_Pln = st_Pln.concat(st_Rst);
        st_Hsty += st_Pln + "\n--------------\n";
        st_Pln = "";
        st_Rst = "0";
    }

    private void doClick_Del() {
        if (st_Pln.length() == 0)
            return;
        if (st_Pln.length() == 1) {
            st_Pln = "0";
            refreshScreen();
            return;
        } else if (st_Pln.length() >= 2) {
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            if (isOperator(st_Pln.charAt(st_Pln.length() - 1))) {
                tv_Show.setText(st_Hsty + "\n");
                tv_Show.append(st_Pln + "\n");
                tv_Show.append(st_Rst);
            } else
                refreshScreen();
            return;
        }

    }

    private void doClick_div() {
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0");
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
        st_Pln = st_Pln.concat("÷");
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        tv_Show.append(st_Rst);
    }

    private void doClick_mul() {
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0");
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
        st_Pln = st_Pln.concat("×");
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        tv_Show.append(st_Rst);
    }

    private void doClick_psd() {
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0");
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);

        //remove last number
        int a = 0, b = st_Pln.length() - 1;
        for (int i = 0; i <= b; i++) {
            if (isOperator(st_Pln.charAt(i)))
                a = i + 1;
        }
        String temp = st_Pln.substring(a, st_Pln.length());

        if (a == 0) {
            temp+="÷100=";
            Calculate tempC =new Calculate(temp);
            temp=tempC.Calculat();
            BigDecimal db = new BigDecimal(temp);
            st_Pln = db.toPlainString();
        }
        else {
            st_Pln = st_Pln.substring(0, a);
            temp+="÷100=";
            Calculate tempC =new Calculate(temp);
            temp=tempC.Calculat();
            BigDecimal db = new BigDecimal(temp);
            st_Pln += db.toPlainString();
        }
        refreshScreen();

    }

    private void doClick_sub() {
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0");
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
        st_Pln = st_Pln.concat("-");
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        tv_Show.append(st_Rst);
    }

    private void doClick_add() {
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0");
        if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
        st_Pln = st_Pln.concat("+");
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        tv_Show.append(st_Rst);
    }

    private void doClick_AC() {
        st_Hsty = "";
        st_Pln = "";
        st_Rst = "0";
        tv_Show.setText(st_Hsty + "\n");
        tv_Show.append(st_Pln + "\n");
        tv_Show.append(st_Rst);
    }

    private void doClick_Dot() {
        if (hasDot())
            return;
        if (st_Pln.length() == 0)
            st_Pln = st_Pln.concat("0.");
        else if (isOperator(st_Pln.charAt(st_Pln.length() - 1)))
            st_Pln = st_Pln.concat("0.");
        else
            st_Pln = st_Pln.concat(".");

        refreshScreen();
    }

    private void doClick_0() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("0");
        else if (zeroStart())
            st_Pln = "0";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("0");
        }else
            st_Pln = st_Pln.concat("0");
        refreshScreen();
    }
    private void doClick_1() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("1");
        else if (zeroStart())
            st_Pln = "1";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("1");
        }else
            st_Pln = st_Pln.concat("1");
        refreshScreen();
    }
    private void doClick_2() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("2");
        else if (zeroStart())
            st_Pln = "2";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("2");
        }else
            st_Pln = st_Pln.concat("2");
        refreshScreen();
    }
    private void doClick_3() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("3");
        else if (zeroStart())
            st_Pln = "3";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("3");
        }else
            st_Pln = st_Pln.concat("3");
        refreshScreen();
    }
    private void doClick_4() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("4");
        else if (zeroStart())
            st_Pln = "4";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("4");
        }else
            st_Pln = st_Pln.concat("4");
        refreshScreen();
    }
    private void doClick_5() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("5");
        else if (zeroStart())
            st_Pln = "5";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("5");
        }else
            st_Pln = st_Pln.concat("5");
        refreshScreen();
    }
    private void doClick_6() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("6");
        else if (zeroStart())
            st_Pln = "6";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("6");
        }else
            st_Pln = st_Pln.concat("6");
        refreshScreen();
    }
    private void doClick_7() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("7");
        else if (zeroStart())
            st_Pln = "7";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("7");
        }else
            st_Pln = st_Pln.concat("7");
        refreshScreen();
    }
    private void doClick_8() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("8");
        else if (zeroStart())
            st_Pln = "8";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("8");
        }else
            st_Pln = st_Pln.concat("8");
        refreshScreen();
    }
    private void doClick_9() {
        if(st_Pln.length()==0)
            st_Pln = st_Pln.concat("9");
        else if (zeroStart())
            st_Pln = "9";
        else if (zeroNum()){
            st_Pln = st_Pln.substring(0, st_Pln.length() - 1);
            st_Pln = st_Pln.concat("9");
        }else
            st_Pln = st_Pln.concat("9");
        refreshScreen();
    }

    private void doCalculate() {
        if (isError()) {
            st_Rst = "不能除以0！";
            bt_Psd.setEnabled(false);
            bt_Eql.setEnabled(false);
            bt_Add.setEnabled(false);
            bt_Sub.setEnabled(false);
            bt_Mul.setEnabled(false);
            bt_Div.setEnabled(false);
            return;
        } else {
            bt_Psd.setEnabled(true);
            bt_Eql.setEnabled(true);
            bt_Add.setEnabled(true);
            bt_Sub.setEnabled(true);
            bt_Mul.setEnabled(true);
            bt_Div.setEnabled(true);
            s = new Calculate(st_Pln + "=");
        }
        String temp = s.Calculat();
        BigDecimal db = new BigDecimal(temp);
        st_Rst = " =" + db.toPlainString();
    }

    private boolean isOperator(char c){//如果输入的为运算符返回真
        for (int i = 0; i < ope.length; i++) {
            if (c == ope[i])
                return true;
        }
        return false;
    }

    private boolean zeroStart() {
        if (st_Pln.length() == 0)
            return false;
        else if (st_Pln.length() == 1) {
            if (st_Pln == "0")
                return true;
        }else
            return false;
        return false;
    }


    private boolean zeroNum() {
        if (st_Pln.length() == 0)
            return false;
        else if (st_Pln.length() == 1) {
            if (st_Pln == "0")
                return true;
        }else if (st_Pln.length() >= 2) {
            if (isOperator(st_Pln.charAt(st_Pln.length() - 2)) &&
                    st_Pln.charAt(st_Pln.length() - 1) == '0')
                return true;
        }
        return false;
    }

    private boolean hasDot() {
        int a = 0, b = st_Pln.length() - 1;
        for (int i = 0; i < st_Pln.length(); i++) {
            if (isOperator(st_Pln.charAt(i)))
                a = i;
        }
        for (; a <= b; a++) {
            if (st_Pln.charAt(a) == '.')
                return true;
        }
        return false;
    }

    private boolean isError() {
        boolean x = false;
        boolean y = true;
        //get last number
        int a = 0, b = st_Pln.length() - 1;
        for (int i = 0; i <= b; i++) {
            if (isOperator(st_Pln.charAt(i)))
                a = i + 1;
        }
        String lastnum = st_Pln.substring(a, st_Pln.length());

        if (a < 2) return false;
        if ((st_Pln.charAt(a - 1) == '÷')&&(Double.valueOf(lastnum)==0))
           return true;

        return false;

    }

}
