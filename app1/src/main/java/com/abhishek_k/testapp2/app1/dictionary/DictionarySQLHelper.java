package com.abhishek_k.testapp2.app1.dictionary;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DictionarySQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydictionary.db";
    public static final String TABLE_NAME = "dic1";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY = "_id";
    public static final String MEANING = "meaning";
    private String[] allColumns = {KEY, MEANING};
    private static final String INIT_DATABASE = "create table " + TABLE_NAME + "(" + KEY + " text primary key, " + MEANING +
            " text not null);";

    public DictionarySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INIT_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DictionarySQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Word addWord(String key, String meaning) {
        ContentValues values = new ContentValues();
        values.put(KEY, key);
        values.put(MEANING, meaning);
        long insertId = getWritableDatabase().insert(TABLE_NAME, null, values);
        return (insertId < 0) ? null : new Word(key, meaning);
    }

    public Word readWord() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, allColumns, null, null, null, null, "RANDOM()", "1");
        cursor.moveToFirst();
        Word word = null;
        while (!cursor.isAfterLast()) {
            word = getWord(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return word;
    }

    private Word getWord(Cursor cursor) {
        return new Word(cursor.getString(0), cursor.getString(1));
    }

}
