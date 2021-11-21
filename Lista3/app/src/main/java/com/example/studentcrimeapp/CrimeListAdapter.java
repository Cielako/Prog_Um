package com.example.studentcrimeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.CrimeViewHolder> {

    private List<Crime> mCrimes;
    private LayoutInflater inflater;

    public CrimeListAdapter(Context context, List<Crime> mCrimes){
        inflater = LayoutInflater.from(context);
        this.mCrimes = mCrimes;
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder{

        public final TextView crimeText;
        public final CrimeListAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView, CrimeListAdapter adapter) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.crime);
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public CrimeListAdapter.CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.crime_list_item, parent, false);
        return new CrimeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeListAdapter.CrimeViewHolder holder, int position) {
        Crime current = mCrimes.get(position);
        holder.crimeText.setText(current.toString());

    }

    @Override
    public int getItemCount() {
        return mCrimes.size();
    }
}
