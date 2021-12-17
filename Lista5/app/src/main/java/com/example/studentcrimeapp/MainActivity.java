package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Crime> mcrimes; /*=  CrimeLab.get(this).getCrimes();;*/
    private RecyclerView recyclerView;
    private CrimeListAdapter crimeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        mcrimes = CrimeLab.get(getApplicationContext()).getCrimes();
        crimeListAdapter = new CrimeListAdapter(this, mcrimes);
        recyclerView.setAdapter(crimeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_of_crimes_menu, menu);
        MenuItem  menuItem = menu.findItem(R.id.search_crime);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //String searchStr = newText;
                //System.out.println(newText);
                crimeListAdapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_crime:
                Intent intent = new Intent(this, NewCrime.class);
                startActivity(intent);
                return true;
            case R.id.search_crime:
                int id = item.getItemId();
                if(id == R.id.search_crime) {
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        mcrimes = CrimeLab.get(getApplicationContext()).getCrimes();
        crimeListAdapter = new CrimeListAdapter(this, mcrimes);
        recyclerView.setAdapter(crimeListAdapter);
    }
}