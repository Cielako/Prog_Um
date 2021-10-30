package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView,summaryView;
    private int counter = 0;
    private int points = 0;
    private int ansQuestCounter = 0;
    Button butTrue, butFalse, butRestart;
    Boolean btnTrueBool, btnFalseBool;
    ArrayList<Integer> answeredQuestions = new ArrayList<>(questions.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.question_text);
        textView.setText(questions[0].getTextId());

        butTrue=(Button)findViewById(R.id.butTrue);
        butFalse=(Button)findViewById(R.id.butFalse);
        butRestart=(Button)findViewById(R.id.butRestart);
        butRestart.setVisibility(View.GONE);

        btnTrueBool=Boolean.parseBoolean(butTrue.getText().toString());
        btnFalseBool=Boolean.parseBoolean(butFalse.getText().toString());

        summaryView = findViewById(R.id.textViewSummary);

        if(savedInstanceState != null){
            counter = savedInstanceState.getInt("counter_state");
            points = savedInstanceState.getInt("points_state");
            ansQuestCounter = savedInstanceState.getInt("ans_counter");
            answeredQuestions = savedInstanceState.getIntegerArrayList("ans_array_state");
        }
    }
    private static final Question[] questions = new Question[]{
            new Question(R.string.question1, true),
            new Question(R.string.question2, false),
            new Question(R.string.question3, false)
    };

    public void nextQuestion(View view) {
        counter++;
        if (counter == questions.length) {
            counter = 0;
        }
        textView.setText(questions[counter].getTextId());

        checkIfAnswered();
    }

    public  void  checkIfAnswered(){
        if(answeredQuestions.contains(counter)){
            butTrue.setEnabled(false);
            butFalse.setEnabled(false);
        }
        else{
            butTrue.setEnabled(true);
            butFalse.setEnabled(true);}
    }

    public void previousQuestion(View view) {
        counter --;
        if(counter < 0){
            counter = questions.length - 1;
        }
        textView.setText(questions[counter].getTextId());

        checkIfAnswered();
    }

    @SuppressLint("NonConstantResourceId")
    public void submitAnswer(View view) {
        if(answeredQuestions.contains(counter)){
            //do nothing
        }
        else answeredQuestions.add(counter);
        switch (view.getId()){

            case R.id.butTrue:
                ansQuestCounter++;
                butTrue.setEnabled(false);
                butFalse.setEnabled(false);
                if(questions[counter].isAnswer() == btnTrueBool) {
                    points++;
                    //butTrue.setBackgroundColor(Color.GREEN);
                }
                summary();
                break;

            case R.id.butFalse:
                ansQuestCounter++;
                butTrue.setEnabled(false);
                butFalse.setEnabled(false);
                if(questions[counter].isAnswer() == btnFalseBool) {
                    points++;
                    //butFalse.setBackgroundColor(Color.GREEN);
                }
                summary();
                break;
        }

    }

    public void summary() { // Wyświetlanie podsumowania
        if(ansQuestCounter == questions.length){
            summaryView.setVisibility(View.VISIBLE);
            summaryView.setText("Total points:" + points + "\n" + "Correct Answers: " + points +"\n" + "Incorrect Answers: " + (questions.length - points));
            butRestart.setVisibility(View.VISIBLE);
        }
    }

    public void restartQuiz(View view) {
        summaryView.setVisibility(View.GONE);
        butRestart.setVisibility(View.GONE);
        counter = 0;
        points = 0;
        ansQuestCounter = 0;
        answeredQuestions.clear();
        butTrue.setEnabled(true);
        butFalse.setEnabled(true);
        textView.setText(questions[counter].getTextId());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("counter_state", counter);
        outState.putIntegerArrayList("ans_array_state", answeredQuestions);
        outState.putInt("points_state", points);
        outState.putInt("ans_counter", ansQuestCounter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(questions[counter].getTextId());
        //textView.setVisibility(View.VISIBLE);
        checkIfAnswered();
        summary();
    }
}