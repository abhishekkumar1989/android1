package com.abhishek_k.mymodule.criminalIntent;

import android.content.Context;

import com.abhishek_k.mymodule.criminalIntent.model.Crime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CrimeIntentJSONSerializer {

    private Context mContext;
    private String mFilename;

    public CrimeIntentJSONSerializer(Context mContext, String mFilename) {
        this.mContext = mContext;
        this.mFilename = mFilename;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            for (int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (reader != null)
                reader.close();
        }
        return crimes;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Crime c : crimes) {
            jsonArray.put(c.toJSON());
        }
        Writer writer = null;
        try {
            OutputStream os = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(os);
            writer.write(jsonArray.toString());
        } finally {
            if (writer != null) writer.close();
        }
    }

}
