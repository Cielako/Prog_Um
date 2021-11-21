package com.example.studentcrimeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private LinkedList<CrimeLab> list_of_crimes = new LinkedList<>()  ;
    private RecyclerView recyclerView;
    final List<Crime> crimes =  CrimeLab.get(this).getCrimes();;
    private  CrimeListAdapter crimeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView.findViewById(R.id.recyclerView);

        crimeListAdapter = new CrimeListAdapter(this, crimes);
        recyclerView.setAdapter(crimeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}