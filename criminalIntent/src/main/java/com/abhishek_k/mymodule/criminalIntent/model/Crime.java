package com.abhishek_k.mymodule.criminalIntent.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private Photo mPhoto;
    private String mSuspect;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if (json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        mSolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_PHOTO))
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        if (json.has(JSON_SUSPECT))
            mSuspect = json.getString(JSON_SUSPECT);
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo mPhoto) {
        this.mPhoto = mPhoto;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID, mId);
        jsonObject.put(JSON_TITLE, mTitle);
        jsonObject.put(JSON_DATE, mDate.getTime());
        jsonObject.put(JSON_SOLVED, mSolved);
        if (mPhoto != null)
            jsonObject.put(JSON_PHOTO, mPhoto.toJSON());
        jsonObject.put(JSON_SUSPECT, mSuspect);
        return jsonObject;
    }
}
