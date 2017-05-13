package com.example.android.moranlee.helloworld;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {

    Stack<Character> general = new Stack();
    LinkedList <Double> NumResult = new LinkedList();
    LinkedList<Character> ExpResult = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void getExpressionList(){
        Collections.reverse(general);
        Double temp2 = 0.0;
        while(general.size()>0){
            char temp = general.pop();
            if(Character.isDigit(temp)){
                temp2 = temp2*10 + Character.digit(temp,10);
            }
            else{
                if(temp == '.'){
                    boolean notInt = true;
                    while(notInt==true && general.size()!=0){
                        char temp3 = general.pop();
                        Double i = 10.0;
                        if(Character.isDigit(temp3)){
                            temp2 = temp2+(Character.digit(temp3,10))/i;
                            i=i*10;
                        }
                        else{
                            notInt=false;
                            NumResult.add(temp2);
                            temp2 = 0.0;
                            ExpResult.add(temp3);
                        }
                    }
                }
                else {
                    NumResult.add(temp2);
                    ExpResult.add(temp);
                    temp2 = 0.0;
                }
            }
        }
        NumResult.add(temp2);
    }

    public void calculate(View view){
        getExpressionList();
        Iterator<Character> dIte = ExpResult.iterator();
        int i =0;
        int j=0;
        while (dIte.hasNext()){
            Character t0 = dIte.next();
            if(t0.equals('s')){
                NumResult.add(i-j, Math.sin(NumResult.get(i-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                dIte.remove();
            }
            else if(t0.equals('c')){
                NumResult.add(i-j, Math.cos(NumResult.get(i-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                dIte.remove();
            }
            else if(t0.equals('t')){
                NumResult.add(i-j, Math.tan(NumResult.get(i-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                dIte.remove();
            }
            else if(t0.equals('!')){
                NumResult.add(i-j, getFactorial(NumResult.get(i-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                dIte.remove();
            }
            i++;
        }
        Iterator<Character>cIte = ExpResult.iterator();
        i = 0;
        j = 0;
        while(cIte.hasNext()){
            Character t0 = cIte.next();
            if(t0.equals('l')){
                NumResult.add(i-j, Math.log(NumResult.get(i-j))/ Math.log(NumResult.get(i+1-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                cIte.remove();
                j++;
            }
            else if(t0.equals('^')){
                NumResult.add(i-j, Math.pow(NumResult.get(i-j), NumResult.get(i+1-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                cIte.remove();
                j++;
            }
            else if(t0.equals('√')){
                NumResult.add(i-j, Math.pow(NumResult.get(i-j), 1/NumResult.get(i+1-j)));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                cIte.remove();
                j++;
            }
            i++;
        }
        Iterator<Character>aIte = ExpResult.iterator();
        i = 0;
        j = 0;
        while(aIte.hasNext()){
            Character t0 = aIte.next();
            if(t0.equals('*')){
                NumResult.add(i-j, NumResult.get(i-j) * NumResult.get(i+1-j));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                aIte.remove();
                j++;
            }
            else if(t0.equals('/')){
                NumResult.add(i-j, NumResult.get(i-j) / NumResult.get(i+1-j));
                NumResult.remove(i+2-j);
                NumResult.remove((i+1-j));
                aIte.remove();
                j++;
            }
            i++;
        }
        i = 0;
        Double result = NumResult.get(i);
        Iterator<Character>bIte = ExpResult.iterator();
        while(bIte.hasNext()){
            Character t1 = bIte.next();
            if(t1.equals('+')){
                result+=NumResult.get(i+1);
            }
            else if(t1.equals('-')){
                result-=NumResult.get(i+1);
            }
            i++;
        }
        general.clear();
        NumResult.clear();
        ExpResult.clear();
        display(result);
    }

    public Double reverseNum(Double obj){
        Integer aobj = obj.intValue();
        Double reverse = 0.0;
        while(aobj>=1){
            reverse= reverse*10+aobj%10;
            aobj=aobj/10;
        }
        return  reverse;
    }

    public double getFactorial(Double a0){
        Integer temp =a0.intValue();
        Integer k = 1;
        for(int i=temp;i>=1;i--){
            k = k * i;
        }
        return (double)k;
    }

    public String toAString(Stack<Character> a){
        String rt = "";
        for(int i=0;i<a.size();i++){
            rt+= a.elementAt(i).toString();
        }
        return rt;
    }

    public void display(){
        TextView resultTextView = (TextView) findViewById(R.id.textView);
        resultTextView.setText("");
        resultTextView.setText(toAString(general));
    }

    public void display(Double a){
        TextView resultTextView = (TextView) findViewById(R.id.textView);
        resultTextView.setText(a.toString());
    }

    public void addOne(View view){
        Button bTwo = (Button)findViewById(R.id.button1);
        general.push(bTwo.getText().toString().charAt(0));
        display();
    }

    public void addTwo(View view){
        Button bTwo = (Button)findViewById(R.id.button2);
        general.push(bTwo.getText().toString().charAt(0));
        display();
    }

    public void addThree(View view){
        Button bOne = (Button)findViewById(R.id.button3);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addFour(View view){
        Button bOne = (Button)findViewById(R.id.button4);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addFive(View view){
        Button bOne = (Button)findViewById(R.id.button5);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addSix(View view){
        Button bOne = (Button)findViewById(R.id.button6);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addSeven(View view){
        Button bOne = (Button)findViewById(R.id.button7);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addEight(View view){
        Button bOne = (Button)findViewById(R.id.button8);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addNine(View view){
        Button bOne = (Button)findViewById(R.id.button9);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addZero(View view){
        Button bOne = (Button)findViewById(R.id.button0);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addPlus(View view){
        Button bOne = (Button)findViewById(R.id.buttonPlus);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addMinus(View view){
        Button bOne = (Button)findViewById(R.id.buttonMinus);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addMultiply(View view){
        Button bOne = (Button)findViewById(R.id.buttonMultiply);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addDivide(View view){
        Button bOne = (Button)findViewById(R.id.buttonDivide);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addDot(View view){
        Button bOne = (Button)findViewById(R.id.buttonDot);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addSin(View view){
        Button bOne = (Button)findViewById(R.id.buttonSin);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addCos(View view){
        Button bOne = (Button)findViewById(R.id.buttonCos);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addTan(View view){
        Button bOne = (Button)findViewById(R.id.buttonTan);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addLog(View view){
        Button bOne = (Button)findViewById(R.id.buttonLog);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addExtraction(View view){
        Button bOne = (Button)findViewById(R.id.buttonExtraction);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addFactorial(View view){
        Button bOne = (Button)findViewById(R.id.buttonFactorial);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addPower(View view){
        Button bOne = (Button)findViewById(R.id.buttonPower);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }


    public void reset(View view){
        general.clear();
        NumResult.clear();
        ExpResult.clear();
        display();
    }

     /*
    public void addLeftBracket(View view){
        Button bOne = (Button)findViewById(R.id.buttonLeftBracket);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }

    public void addRightBracket(View view){
        Button bOne = (Button)findViewById(R.id.buttonRightBracket);
        general.push(bOne.getText().toString().charAt(0));
        display();
    }
    */

}
