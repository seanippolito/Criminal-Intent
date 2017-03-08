package com.example.seanippolito.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by seanippolito on 3/2/17.
 * This is the Question object to be used across QuizActivity
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private ArrayList<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + (i));
            crime.setSolved((i % 2) == 0);
            crime.setRequiresPolice(((i % 5) == 0));
            mCrimes.add(crime);
        }
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for(Crime crime: mCrimes) {
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
