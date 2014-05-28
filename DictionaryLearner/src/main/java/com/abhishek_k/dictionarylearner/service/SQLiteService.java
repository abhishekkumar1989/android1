package com.abhishek_k.dictionarylearner.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abhishek_k.dictionarylearner.model.QuizQuestion;
import com.abhishek_k.dictionarylearner.model.Word;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.ANSWER_KEY;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.ANSWER_OPTION1_KEY;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.ANSWER_OPTION2_KEY;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.ANSWER_OPTION3_KEY;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.ANSWER_OPTION4_KEY;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.QUESTION_ID;
import static com.abhishek_k.dictionarylearner.model.QuizQuestion.QUESTION_KEY;
import static com.abhishek_k.dictionarylearner.model.Word.MEANING_KEY;
import static com.abhishek_k.dictionarylearner.model.Word.WORD_KEY;

public class SQLiteService extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dictionary.db";
    public static final String TABLE_DICTIONARY_NAME = "dictionary";
    public static final String TABLE_QUIZ_NAME = "quiz";
    public static final int DICTIONARY_VERSION = 1;
    private static final String LOG_TAG = SQLiteService.class.getSimpleName();
    private String[] allColumns = {WORD_KEY, MEANING_KEY};
    private String[] allQuizColumns = {QUESTION_ID, QUESTION_KEY, ANSWER_OPTION1_KEY, ANSWER_OPTION2_KEY,
            ANSWER_OPTION3_KEY, ANSWER_OPTION4_KEY, ANSWER_KEY};
    public static SQLiteService sqlService;
    private SQLiteDatabase database;

    private static final String TABLE_DICTIONARY_CREATE = "create table " + TABLE_DICTIONARY_NAME +
            "(" +
            WORD_KEY + " text primary key, " +
            MEANING_KEY + " text non null" +
            ");";

    private static final String TABLE_QUIZ_CREATE = "create table " + TABLE_QUIZ_NAME +
            "(" +
            QUESTION_ID + " integer primary key autoincrement, " +
            QUESTION_KEY + " text non null, " +
            ANSWER_OPTION1_KEY + " text non null, " +
            ANSWER_OPTION2_KEY + " text non null, " +
            ANSWER_OPTION3_KEY + " text non null, " +
            ANSWER_OPTION4_KEY + " text non null, " +
            ANSWER_KEY + " text non null" +
            ");";

    public static SQLiteService get(Context context) {
        if (sqlService == null) {
            sqlService = new SQLiteService(context);
        }
        return sqlService;
    }

    public QuizQuestion getQuestion(int questionId) {
        Cursor cursor = getReadableDatabase().query(TABLE_QUIZ_NAME, allQuizColumns, QUESTION_ID + ">" + questionId, null, null, null, QUESTION_ID + " ASC", "1");
        cursor.moveToLast();
        QuizQuestion question = null;
        while (!cursor.isAfterLast()) {
            question = QuizQuestion.builder().id(cursor.getInt(0)).question(cursor.getString(1))
                    .option1(cursor.getString(2)).option2(cursor.getString(3)).option3(cursor.getString(4))
                    .option4(cursor.getString(5)).answer(cursor.getString(6));
            cursor.moveToNext();
        }
        cursor.close();
        return question;
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
        db.execSQL(TABLE_DICTIONARY_CREATE);
        db.execSQL(TABLE_QUIZ_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to new version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICTIONARY_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_NAME);
        onCreate(db);
    }

}
