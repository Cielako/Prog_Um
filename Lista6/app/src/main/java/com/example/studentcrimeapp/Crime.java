package com.example.studentcrimeapp;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;
    private boolean mSolved;
    private String mImg_Path;

    public Crime() {
        mId = UUID.randomUUID();
        //mDate = new Date();
    }

    public Crime (UUID id){
        this.mId = id;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setDate(String date){
        mDate = date;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public void setImg_Path(String mImg_Path){this.mImg_Path = mImg_Path;}

    public UUID getId() {
        return mId;
    }

    public String getTitle() { return mTitle; }

    public String getDate() { return mDate; }

    public boolean getSolved(){return  mSolved;}

    public String getImg_Path(){return mImg_Path;}
}
