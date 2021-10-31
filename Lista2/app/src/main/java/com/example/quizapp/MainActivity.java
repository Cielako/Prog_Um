package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String ANS = "com.example.quiz.ANS";

    private TextView textView,summaryView;
    private int counter = 0;
    private int points = 0;
    private int ansQuestCounter = 0;
    Button butTrue, butFalse, butRestart, butCheat,butShowAnswer;
    Boolean btnTrueBool, btnFalseBool;
    ArrayList<Integer> answeredQuestions = new ArrayList<>(questions.length);
    Snackbar infoCorrect, infoIncorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.question_text);
        textView.setText(questions[0].getTextId());

        butTrue = (Button)findViewById(R.id.butTrue);
        butFalse = (Button)findViewById(R.id.butFalse);
        butRestart = (Button)findViewById(R.id.butRestart);
        butRestart.setVisibility(View.GONE);
        butCheat = (Button)findViewById(R.id.butCheat);
        butShowAnswer = (Button)findViewById(R.id.butShowAnswer);
        butShowAnswer.setVisibility(View.GONE);


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
            butCheat.setVisibility(View.GONE);
            butShowAnswer.setVisibility(View.VISIBLE); }
        else{
            butTrue.setEnabled(true);
            butFalse.setEnabled(true);
            butCheat.setVisibility(View.VISIBLE);
            butShowAnswer.setVisibility(View.GONE); }

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
        infoCorrect = Snackbar.make(textView, "Correct :)", Snackbar.LENGTH_SHORT);
        infoIncorrect = Snackbar.make(textView, "Incorrect :(", Snackbar.LENGTH_SHORT);
        switch (view.getId()){

            case R.id.butTrue:
                ansQuestCounter++;
                butTrue.setEnabled(false);
                butFalse.setEnabled(false);
                butCheat.setVisibility(View.GONE);
                butShowAnswer.setVisibility(View.VISIBLE);
                if(questions[counter].isAnswer() == btnTrueBool) {
                    points++;
                    infoCorrect.show();
                }
                else infoIncorrect.show();
                summary();
                break;

            case R.id.butFalse:
                ansQuestCounter++;
                butTrue.setEnabled(false);
                butFalse.setEnabled(false);
                butCheat.setVisibility(View.GONE);
                butShowAnswer.setVisibility(View.VISIBLE);
                if(questions[counter].isAnswer() == btnFalseBool) {
                    points++;
                    infoCorrect.show();
                }else infoIncorrect.show();
                summary();
                break;
        }
    }

    public void summary() {
        if(ansQuestCounter == questions.length){

            summaryView.setVisibility(View.VISIBLE);
            summaryView.setText("Total points:" + points + "\n" + "Correct Answers: " + points +"\n" + "Incorrect Answers: " + (questions.length - points) +"\n" + "Cheated Questions: " + CheatActivity.cheatCounter + "\n" + "Score: " + totalResult());
            butRestart.setVisibility(View.VISIBLE);
        }
    }

    public double totalResult(){
        double fine = CheatActivity.cheatCounter * 15;
        double pointsCalc = points;
        if((pointsCalc/questions.length * 100 - fine) < 0 || fine > 100 ){
            return 0;
        }
        else
            return (pointsCalc/questions.length * 100 - fine);
    }

    public void restartQuiz(View view) {
        summaryView.setVisibility(View.GONE);
        butRestart.setVisibility(View.GONE);
        textView.setText(questions[counter].getTextId());
        counter = 0;
        points = 0;
        ansQuestCounter = 0;
        answeredQuestions.clear();
        butTrue.setEnabled(true);
        butFalse.setEnabled(true);
        butCheat.setVisibility(View.VISIBLE);
        CheatActivity.cheatCounter = 0;
        butShowAnswer.setVisibility(View.GONE);

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
        checkIfAnswered();
        summary();
    }

    public void startCheatActivity(View view){
        Intent intent = new Intent(this, CheatActivity.class);
        intent.putExtra(ANS ,Boolean.toString(questions[counter].isAnswer()));
        startActivity(intent);
    }

    public void showCorrectAnswer(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Correct Answer is");
        alertDialog.setMessage(Boolean.toString(questions[counter].isAnswer()));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "EXIT", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void SearchAnswer(View view) {
        String searchTerm = textView.getText().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + searchTerm)));
    }
}