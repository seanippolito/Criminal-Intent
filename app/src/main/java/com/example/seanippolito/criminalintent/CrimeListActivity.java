package com.example.seanippolito.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by seanippolito on 3/2/17.
 * This is the Question object to be used across QuizActivity
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
