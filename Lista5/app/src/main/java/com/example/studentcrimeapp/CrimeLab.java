package com.example.studentcrimeapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.UserDataHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CrimeLab {
    //private List<Crime> mCrimes;
    private static CrimeLab sCrimeLab;

    private SQLiteDatabase mDatabase;


    private CrimeLab(Context context) {
        mDatabase = new DBHandler(context).getWritableDatabase();
        //mCrimes = new ArrayList<>();
       /* for (int i = 0; i < 50; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }*/
    }

    public static  CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID id) {
        /*for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;*/
        Cursor cursor = mDatabase.query("crimes", null, "_uuid = ?", new String[]{id.toString()},null,null,null);

        try {
            cursor.moveToFirst();
            String crime_title = cursor.getString(cursor.getColumnIndexOrThrow("crime_title"));
            String dateString = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String solved = cursor.getString(cursor.getColumnIndexOrThrow("solved"));
            Crime crime = new Crime(id);
            crime.setTitle(crime_title);
            crime.setDate(dateString);
            crime.setSolved(Boolean.parseBoolean(solved));
            return crime;
        }  finally {
            cursor.close();
        }

    }
    public List<Crime> getCrimes() {

        //return mCrimes;
        Cursor cursor = mDatabase.query("crimes", null, null, null,null,null,null);
        List<Crime> crimes = new ArrayList<>();
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String crime_title = cursor.getString(cursor.getColumnIndexOrThrow("crime_title"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                String solved = cursor.getString(cursor.getColumnIndexOrThrow("solved"));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow("_uuid"));
                Crime crime = new Crime(UUID.fromString(uuid));
                crime.setTitle(crime_title);
                crime.setDate(dateStr);
                crime.setSolved(Boolean.parseBoolean(solved));
                crimes.add(crime);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public void updateCrime(UUID id, String title, boolean solve, String date){
       /* for(Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                crime.setTitle(title);
                crime.setSolved(solve);
                crime.setDate(date);
            }
        }*/
        Crime crime = new Crime(id);
        crime.setTitle(title);
        crime.setSolved(solve);
        crime.setDate(date.toString());
        mDatabase.update("crimes", getContetntValues(crime), "_uuid = ?",
                new String[] {id.toString()});
    }

    public void deleteCrime(UUID id){
        /*for(Crime crime: mCrimes){
            if(crime.getId().equals(id)){
                mCrimes.remove(crime);
                break;
            }
        }*/
        mDatabase.delete("crimes","_uuid = ?", new String[]{id.toString()});
    }

    public void newCrime(Crime crime){
        //mCrimes.add(crime);
        mDatabase.insert("crimes", null, getContetntValues(crime));
    }

    private ContentValues getContetntValues(Crime crime){
        ContentValues contentValues = new ContentValues();
        contentValues.put("_uuid", crime.getId().toString());
        contentValues.put("crime_title", crime.getTitle());
        contentValues.put("date", crime.getDate());
        contentValues.put("solved",Boolean.toString(crime.getSolved()));

        return contentValues;
    }
}
