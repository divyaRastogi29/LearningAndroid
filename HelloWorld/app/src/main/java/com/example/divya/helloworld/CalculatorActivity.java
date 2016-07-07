package com.example.divya.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * Created by divya on 21/5/16.
 */
public class CalculatorActivity extends Activity implements View.OnClickListener {
    TextView textView;
    Button zero, one, two, three, four, five, six, seven, eight, nine, divide, multiply, plus, minus, delete, equals;
    Logger logger = Logger.getLogger(CalculatorActivity.class.getName());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_activity);
        initViews();
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        divide.setOnClickListener(this);
        multiply.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        delete.setOnClickListener(this);
        equals.setOnClickListener(this);
    }
    private void initViews(){
        textView = (TextView)findViewById(R.id.textView);
        zero = (Button) findViewById(R.id.zero);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        divide = (Button) findViewById(R.id.divide);
        multiply = (Button) findViewById(R.id.mult);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button)findViewById(R.id.minus);
        delete = (Button)findViewById(R.id.delete);
        equals = (Button)findViewById(R.id.equal);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String s = "";
        switch(id) {
            case R.id.zero : s="0";
                break;
            case R.id.one : s="1";
                break;
            case R.id.two : s="2";
                break;
            case R.id.three : s="3";
                break;
            case R.id.four : s="4";
                break;
            case R.id.five : s="5";
                break;
            case R.id.six : s="6";
                break;
            case R.id.seven : s="7";
                break;
            case R.id.eight : s="8";
                break;
            case R.id.nine : s="9";
                break;
            case R.id.plus : s="+";
                break;
            case R.id.minus : s="-";
                break;
            case R.id.mult : s="*";
                break;
            case R.id.divide : s="/";
                break;
            case R.id.equal:
                s=evaluateExp();
                textView.setText("");
                break;
            case R.id.delete :
                s= textView.getText().toString();
                textView.setText("");
                s=s.substring(0,s.length()-1);
                break;
        }
        textView.append(s);
    }

    private String evaluateExp() {
        String exp = textView.getText().toString();
        String postFix = infixToPostFix(exp);
        int result = evaluatePostFix(postFix);
        return (result+"");
    }

    public int priority(char ch){
        if(ch=='*')
            return 4;
        else if(ch=='/')
            return 3;
        else if(ch=='+')
            return 2;
        else if(ch=='-')
            return 1;
        return 0 ;
    }

    public  String infixToPostFix(String exp){
        Log.d("\nExpression is : ",exp);
        Stack<Character> stack = new Stack<>();
        String postFix = "";
        for(int i=0;i<exp.length();i++){
            char ch = exp.charAt(i);
            switch(ch){
                case '*':
                case '/':
                case '+':
                case '-':
                    postFix=postFix+'a';
                    while((!stack.isEmpty())&&(priority(stack.peek())>=priority(ch))){
                        postFix = postFix + stack.pop();
                    }
                    stack.push(ch);
                    break;
                default:postFix=postFix+ch;
                    break;
            }
        }
        while(!stack.isEmpty())
            postFix=postFix+stack.pop();
        Log.d("\nPOSTFIX : ",postFix);
        return postFix;
    }

    public int evaluatePostFix(String postFix) {
        Stack<Integer> stack = new Stack<>();
        String num = "";
        for (int i = 0; i < postFix.length(); i++) {
            char ch = postFix.charAt(i);
            if (ch == 'a') {
                stack.push(Integer.parseInt(num));
                num = "";
            } else {
                if ((ch >= '0') && (ch <= '9')) {
                    num = num + ch;
                } else {
                    int a = stack.pop();
                    int b = stack.pop();
                    switch (ch) {
                        case '*':
                            stack.push(a * b);
                            break;
                        case '/':
                            stack.push(a / b);
                            break;
                        case '+':
                            stack.push(a + b);
                            break;
                        case '-':
                            stack.push(a - b);
                            break;
                    }
                }
            }
        }
        Log.d("\nStack POP : ",stack.peek()+"");
        return stack.pop();
    }

}
