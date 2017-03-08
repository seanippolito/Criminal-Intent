package com.example.seanippolito.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by seanippolito on 3/2/17.
 * This is the Question object to be used across QuizActivity
 */

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private SimpleDateFormat mSimpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI(){

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        ArrayList<Crime> crimes = crimeLab.getCrimes();

        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.police_crime);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mSimpleDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.US);
            String date = mSimpleDateFormat.format(mCrime.getDate());
            mDateTextView.setText(date);
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
//            mCurrentPosition = getAdapterPosition();
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    //Create a second holder for the call police button
    private class CrimeHolderRequiresPolice extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mRequiresPolice;
        private ImageView mSolvedImage;

        private Crime mCrime;

        public CrimeHolderRequiresPolice(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_police_crime, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mRequiresPolice = (Button) itemView.findViewById(R.id.requires_police);
            mSolvedImage = (ImageView) itemView.findViewById(R.id.police_crime);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());

            mSimpleDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.US);
            String date = mSimpleDateFormat.format(mCrime.getDate());
            mDateTextView.setText(date);
            mSolvedImage.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<Crime> mCrimes;
        private static final int CRIME_VIEW_TYPE_NORMAL = 1;
        private static final int CRIME_VIEW_TYPE_POLICE = 2;

        public CrimeAdapter(ArrayList<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public int getItemViewType(int position){
            Crime crime = mCrimes.get(position);
            if(crime.isRequiresPolice()){
                return CRIME_VIEW_TYPE_POLICE;
            } else {
                return CRIME_VIEW_TYPE_NORMAL;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //Decide here which view holder to use
            switch(viewType) {
                case CRIME_VIEW_TYPE_NORMAL:
                    return new CrimeHolder(layoutInflater, parent);
                case CRIME_VIEW_TYPE_POLICE:
                    return new CrimeHolderRequiresPolice(layoutInflater, parent);
                default:
                    //Exception here?
                    return new CrimeHolder(layoutInflater, parent);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            //Find out which holder to use, cast appropriately, call correct bind
            if(crime.isRequiresPolice()) {
                CrimeHolderRequiresPolice policeHolder = (CrimeHolderRequiresPolice)holder;
                policeHolder.bind(crime);
            }else {
                CrimeHolder normalHolder = (CrimeHolder) holder;
                normalHolder.bind(crime);
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
