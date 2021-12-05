package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public List<Crime> crimes =  CrimeLab.get(this).getCrimes();
    private int crimePos;
    private UUID crimeUUID;
    private Crime crime;
    public Calendar c;
    public Calendar c2;
    private Date date = new Date();
    private ViewPager2 viewPager2;
    private String crimeT;
    private String crimeD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crime_activity);

        viewPager2 = findViewById(R.id.pager);
        DetailPagerAdapter adapter = new DetailPagerAdapter();
        viewPager2.setAdapter(adapter);


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            crimeUUID = UUID.fromString(extras.get("crimeUUID").toString());
            crimePos = Integer.parseInt(extras.get("position").toString());

        }
        viewPager2.setCurrentItem(crimePos, false);
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
        //crimeDate.setText(c.get(Calendar.DAY_OF_MONTH) +"-"+ c.get(Calendar.MONTH) +"-"+ c.get(Calendar.YEAR));


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c2 = Calendar.getInstance();
        c2.set(Calendar.HOUR, hourOfDay);
        c2.set(Calendar.MINUTE, minute);

        date.setHours(c2.get(Calendar.HOUR));
        date.setMinutes(c2.get(Calendar.MINUTE));
        crimeT = date.getHours()+ ":" + date.getMinutes();
    }

    public class DetailPagerAdapter extends RecyclerView.Adapter<DetailPagerAdapter.MyViewHolder> {

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private EditText crimeText;
            private Button crimeDate;
            private Button crimeTime;
            private CheckBox crimeSolve;

            private Button firstItemButton;
            private Button lastItemButton;

            DetailPagerAdapter adapter;


            public MyViewHolder(@NonNull View itemView, DetailPagerAdapter adapter){
                super(itemView);
                crimeText = itemView.findViewById(R.id.crimeNameEdit);
                crimeDate = itemView.findViewById(R.id.crimeDateButton);
                crimeTime = itemView.findViewById(R.id.crimeTimeButton);
                crimeSolve = itemView.findViewById(R.id.checkBoxSolve);
                firstItemButton = itemView.findViewById(R.id.firstItemButton);
                lastItemButton = itemView.findViewById(R.id.lastItemButton);

                this.adapter = adapter;

                crimeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment datePicker = new DatePickerFragment();
                        datePicker.show(getSupportFragmentManager(), "date picker");
                        crimeDate.setText("Date is Set");
                    }
                });

                crimeTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment timePicker = new TimePickerFragment();
                        timePicker.show(getSupportFragmentManager(), "time picker");
                        crimeTime.setText("Time is Set");
                    }
                });

            }

        }

        @NonNull
        @Override
        public DetailPagerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(
                    (LayoutInflater.from(CrimeDetailActivity.this).inflate(
                            R.layout.crime_activity_layout,
                            parent,
                            false
                    )),
                    this

            );
        }



        @Override
        public void onBindViewHolder(@NonNull DetailPagerAdapter.MyViewHolder holder, int position) {
            Crime current = crimes.get(position);
            holder.crimeText.setText(current.getTitle());
            holder.crimeDate.setText(current.getDate().toString());
            holder.crimeTime.setText(current.getDate().getHours() + ":" + current.getDate().getMinutes());
            holder.crimeSolve.setChecked(current.getSolved());

            crimeUUID = crimes.get(position).getId();
            crimePos = viewPager2.getCurrentItem();
            System.out.println(crimeUUID);

            holder.crimeText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    current.setTitle(holder.crimeText.getText().toString());
                }
            });

            holder.crimeSolve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    current.setSolved(holder.crimeSolve.isChecked());
                }
            });

            if(crimePos == 0){
                holder.firstItemButton.setVisibility(View.GONE);
            }
            if (crimePos == crimes.size()){
                holder.lastItemButton.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return crimes.size();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CrimeLab.get(this).getCrime(crimeUUID).setDate(date);
        //finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //finish();

    }

    public void First(View view){
        viewPager2.setCurrentItem(0);
    }

    public void Last(View view){
        viewPager2.setCurrentItem(crimes.size());
    }


    public void crimeDelete(View view) {
        CrimeLab.get(this).getCrimes().remove(viewPager2.getCurrentItem());
        finish();
    }


}