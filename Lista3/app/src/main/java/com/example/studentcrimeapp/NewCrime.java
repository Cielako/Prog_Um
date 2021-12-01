package com.example.studentcrimeapp;

import static java.lang.Boolean.parseBoolean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Date;


public class NewCrime extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText crimeText;
    private Button crimeDate;
    private Button crimeTime;
    private CheckBox crimeSolve;
    private  Calendar c;
    private  Calendar c2;
    private Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_crime);

        crimeText = findViewById(R.id.crimeNameEdit);
        crimeDate = findViewById(R.id.crimeDateButton);
        crimeTime = findViewById(R.id.crimeTimeButton);
        crimeSolve = findViewById(R.id.checkBoxSolve);

        crimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        crimeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        date.setDate(c.get(Calendar.DAY_OF_MONTH));
        date.setMonth(c.get(Calendar.MONTH));
        date.setYear(c.get(Calendar.YEAR)-1900);
        crimeDate.setText(c.get(Calendar.DAY_OF_MONTH) +"-"+ c.get(Calendar.MONTH) +"-"+ c.get(Calendar.YEAR));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR, hourOfDay);
        c2.set(Calendar.MINUTE, minute);

        date.setHours(c2.get(Calendar.HOUR));
        date.setMinutes(c2.get(Calendar.MINUTE));
        crimeTime.setText(c2.get(Calendar.HOUR) + ":" + c2.get(Calendar.MINUTE));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Crime crime = new Crime();
        crime.setTitle(crimeText.getText().toString());
        crime.setSolved(crimeSolve.isChecked());
        crime.setDate(date);

        CrimeLab.get(this).newCrime(crime);
        finish();
    }

}