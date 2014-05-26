package com.abhishek_k.dictionarylearner.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abhishek_k.dictionarylearner.model.Word;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.abhishek_k.dictionarylearner.model.Word.MEANING_KEY;
import static com.abhishek_k.dictionarylearner.model.Word.WORD_KEY;

public class SQLiteService extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dictionary.db";
    public static final String TABLE_DICTIONARY_NAME = "dictionary";
    public static final int DICTIONARY_VERSION = 1;
    private static final String LOG_TAG = SQLiteService.class.getSimpleName();
    private String[] allColumns = {WORD_KEY, MEANING_KEY};
    public static SQLiteService sqlService;
    private SQLiteDatabase database;

    private static final String TABLE_CREATE = "create table " + TABLE_DICTIONARY_NAME +
            "(" +
            WORD_KEY + " text primary key, " +
            MEANING_KEY + " text non null" +
            ");";

    public static SQLiteService get(Context context) {
        if (sqlService == null) {
            sqlService = new SQLiteService(context);
        }
        return sqlService;
    }

    public Word randomWord() {
        Cursor cursor = getReadableDatabase().query(TABLE_DICTIONARY_NAME, allColumns, null, null, null, null, "RANDOM()", "1");
        cursor.moveToLast();
        Word word = null;
        while (!cursor.isAfterLast()) {
            word = new Word(cursor.getString(0), cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return word;
    }

    public Word getWord(String word) {
        return null;
    }

    public boolean addOrUpdate(Word word) {
        ContentValues values = new ContentValues();
        values.put(WORD_KEY, word.getWord());
        values.put(MEANING_KEY, word.getMeaning());
        long insertId = getWritableDatabase().insertWithOnConflict(TABLE_DICTIONARY_NAME, null, values, CONFLICT_REPLACE);
        return (insertId >= 0);
    }

    public Word updateWord() {
        return null;
    }

    public boolean deleteWord(String wordName) {
        int deleteId = getWritableDatabase().delete(TABLE_DICTIONARY_NAME, WORD_KEY + "='" + wordName + "'", null);
        return deleteId > 0;
    }

    public SQLiteService(Context context) {
        super(context, DATABASE_NAME, null, DICTIONARY_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to new version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY_NAME);
        onCreate(db);
    }

}
