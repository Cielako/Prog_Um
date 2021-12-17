package com.example.studentcrimeapp;

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
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private UUID crimeUUID;
    private Crime crime;
    private EditText crimeText;
    private Button crimeDate;
    private Button crimeTime;
    private CheckBox crimeSolve;
    private Calendar c;
    private  Calendar c2;
    private Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        crimeText = findViewById(R.id.crimeNameEdit);
        crimeDate = findViewById(R.id.crimeDateButton);
        crimeTime = findViewById(R.id.crimeTimeButton);
        crimeSolve = findViewById(R.id.checkBoxSolve);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            crimeUUID = UUID.fromString(extras.get("crimeUUID").toString());
        }

        crime = CrimeLab.get(this).getCrime(crimeUUID);
        crimeText.setText(crime.getTitle());
        crimeDate.setText(crime.getDate().toString());
        crimeSolve.setChecked(crime.getSolved());

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
        date.setYear(c.get(Calendar.YEAR) - 1900);

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
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CrimeLab.get(getApplicationContext()).updateCrime(crimeUUID,crimeText.getText().toString(),crimeSolve.isChecked(), date.toString());
    }

    public void crimeDelete(View view) {
        CrimeLab.get(getApplicationContext()).deleteCrime(crimeUUID);
        finish();
    }
}