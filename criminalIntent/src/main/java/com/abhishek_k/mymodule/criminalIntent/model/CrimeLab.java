package com.abhishek_k.mymodule.criminalIntent.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.abhishek_k.mymodule.criminalIntent.CrimeIntentJSONSerializer;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private Context mAppContext;
    private ArrayList<Crime> mCrimes;
    private CrimeIntentJSONSerializer mSerializer;
    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CrimeIntentJSONSerializer(appContext, FILENAME);
        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
            mCrimes = new ArrayList<Crime>();
            Toast.makeText(mAppContext, "Loading crimes from the disk failed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public boolean saveCrimes() {
        try {
            mSerializer.saveCrimes(mCrimes);
            Log.d(TAG, "crimes saved to file");
            Toast.makeText(mAppContext, "Crimes successfully saved", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes: ", e);
            Toast.makeText(mAppContext, "Crimes saving to disk failed", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime c : mCrimes) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

}