package com.example.studentcrimeapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHandler extends SQLiteOpenHelper {
    public DBHandler( Context context) {
        super(context, "crimeDatabase.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE crimes(" +
                "_id integer primary key autoincrement," +
                "_uuid," +
                "crime_title," +
                "date," +
                "solved)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
