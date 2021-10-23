package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    boolean newOp = true;
    private TextView showText;
    private String defaultOp = "+";
    private String oldNumber = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showText = findViewById(R.id.textView);
    }

    public void numberClick(View view) {
        if(newOp)
            showText.setText("");
        newOp = false;
        String number = showText.getText().toString();
        switch (view.getId()){
            case R.id.numBu1:
                number += "1";
                break;
            case R.id.numBu2:
                number += "2";
                break;
            case R.id.numBu3:
                number += "3";
                break;
            case R.id.numBu4:
                number += "4";
                break;
            case R.id.numBu5:
                number += "5";
                break;
            case R.id.numBu6:
                number += "6";
                break;
            case R.id.numBu7:
                number += "7";
                break;
            case R.id.numBu8:
                number += "8";
                break;
            case R.id.numBu9:
                number += "9";
                break;
            case R.id.numBu0:
                number += "0";
                break;
            case R.id.plusBu:
                number += "+" + number;
                break;
            case R.id.minusBu:
                number += "-"+ number;
                break;
            case R.id.multiplyBu:
                number += "*" + number;
                break;
            case R.id.divideBu:
                number += "/" + number;
                break;

        }
        showText.setText(number);
    }

    public void operatorClick(View view) {
        newOp = true;
        oldNumber = showText.getText().toString();
        switch (view.getId()){
            case R.id.plusBu: defaultOp = "+";
                break;
            case R.id.minusBu: defaultOp = "-";
                break;
            case R.id.multiplyBu: defaultOp = "*";
                break;
            case R.id.divideBu: defaultOp = "/";
                break;
        }

    }

    public void equalClick(View view) {
        String newNumber = showText.getText().toString();
        double result = 0.0;
        System.out.println((showText.getText().toString()));

        Snackbar myInfo = Snackbar.make(findViewById(R.id.textView), "nie dzielimy przez 0 :)", Snackbar.LENGTH_SHORT);
        switch (defaultOp){
            case "+":
                result = Double.parseDouble(oldNumber) + Double.parseDouble(newNumber);
                break;
            case "-":
                result = Double.parseDouble(oldNumber) - Double.parseDouble(newNumber);
                break;
            case "*":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
            case "/":
                if(Double.parseDouble(newNumber) == 0.0)
                   //Wyświetlamy komunikat przy dzieleniu przez 0
                    myInfo.show();
                else
                    result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                break;
        }
        showText.setText(result+"");
    }

    public void clearData(View view) {
        showText.setText("0");
        oldNumber="0";
        newOp = true;
    }


}