package com.abhishek_k.testapp2.app1.dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek_k.testapp2.app1.R;

public class AddWordActivity extends Activity {

    private DictionarySQLHelper sqlHelper;

    public AddWordActivity() {
        sqlHelper = new DictionarySQLHelper(this);
    }

    public AddWordActivity(DictionarySQLHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addword);
    }

    public void addWord(View view) {
        EditText keyView = (EditText) findViewById(R.id.key);
        EditText meaningView = (EditText) findViewById(R.id.meaning);
        String key = keyView.getText().toString();
        String meaning = meaningView.getText().toString();
        if (key.equals("")) {
            Toast.makeText(this, "Key can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (meaning.equals("")) {
            Toast.makeText(this, "Meaning can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Word insertedWord = sqlHelper.addWord(key, meaning);
        keyView.setText("");
        meaningView.setText("");
        if(insertedWord == null) {
            Toast.makeText(this, "Key already exist [ " + key + " ]", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Word inserted [ " + insertedWord.toString() + " ]", Toast.LENGTH_LONG).show();
        }
    }

}
