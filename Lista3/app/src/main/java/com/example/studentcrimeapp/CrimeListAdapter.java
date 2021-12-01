package com.example.studentcrimeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.CrimeViewHolder> {

    private final List<Crime> crimeList;
    private LayoutInflater inflater;

    public CrimeListAdapter(Context context, List<Crime> crimeList){
        inflater = LayoutInflater.from(context);
        this.crimeList = crimeList;
    }

    class CrimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView crimeText;
        public final TextView crimeSolvedText;
        public final TextView crimeDateText;
        final CrimeListAdapter adapter;

        public CrimeViewHolder(@NonNull View itemView, CrimeListAdapter adapter) {
            super(itemView);
            crimeText = itemView.findViewById(R.id.crime);
            crimeSolvedText = itemView.findViewById(R.id.crimeSolved);
            crimeDateText = itemView.findViewById(R.id.crimeDate);
            this.adapter = adapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;

            int position = getLayoutPosition();
            Crime element = crimeList.get(position);
            crimeList.set(position, element);
            adapter.notifyItemChanged(position);

            intent = new Intent(view.getContext(), CrimeActivity.class);
            intent.putExtra("crimeUUID",element.getId().toString());
            view.getContext().startActivity(intent);
        }

    }


    @NonNull
    @Override
    public CrimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.crime_list_item, parent, false);
        return new CrimeViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CrimeViewHolder holder, int position) {
        Crime current = crimeList.get(position);
        holder.crimeText.setText(current.getTitle());
        holder.crimeSolvedText.setText("Solved:" + String.valueOf(current.getSolved()));
        holder.crimeDateText.setText(current.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }


}
