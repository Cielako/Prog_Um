package com.example.studentcrimeapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private List<Crime> mCrimes;
    private static CrimeLab sCrimeLab;

    public static  CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public void updateCrime(UUID id, String title, boolean solve, Date date){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                crime.setTitle(title);
                crime.setSolved(solve);
                crime.setDate(date);
            }
        }
    }

    public void deleteCrime(UUID id){
        for(Crime crime: mCrimes){
            if(crime.getId().equals(id)){
                mCrimes.remove(crime);
                break;
            }
        }
    }

    public void newCrime(Crime crime){
        mCrimes.add(crime);
    }
}
