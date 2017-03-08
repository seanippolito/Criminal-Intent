package com.example.seanippolito.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by seanippolito on 3/7/17.
 * This is the Question object to be used across QuizActivity
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.example.seanippolito.criminalintent.crime_id";

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;
    private Button mJumpToFirst;
    private Button mJumpToLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(16);
        mViewPager.setPadding(16,16,16,16);

        mJumpToFirst = (Button) findViewById(R.id.jump_to_first);
        mJumpToLast = (Button) findViewById(R.id.jump_to_last);

        mJumpToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                mJumpToFirst.setEnabled(false);
            }
        });

        mJumpToLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size());
                mJumpToLast.setEnabled(false);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 0) {
                    mJumpToFirst.setEnabled(true);
                } else {
                    mJumpToFirst.setEnabled(false);
                }

                if(position != (mCrimes.size() - 1)) {
                    mJumpToLast.setEnabled(true);
                } else {
                    mJumpToLast.setEnabled(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for(int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
