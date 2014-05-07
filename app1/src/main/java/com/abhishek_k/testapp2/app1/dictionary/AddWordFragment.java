package com.abhishek_k.testapp2.app1.dictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek_k.testapp2.app1.R;

public class AddWordFragment extends Fragment {

    private DictionarySQLHelper sqlHelper;

    public static AddWordFragment init() {
        return new AddWordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layoutView = inflater.inflate(R.layout.addword, container, false);
        sqlHelper = new DictionarySQLHelper(layoutView.getContext());
        Button addButton = (Button) layoutView.findViewById(R.id.addword);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWord(v, layoutView);
            }
        });
        return layoutView;
    }

    public void addWord(View view, View layoutView) {
        EditText keyView = (EditText) layoutView.findViewById(R.id.key);
        EditText meaningView = (EditText) layoutView.findViewById(R.id.meaning);
        String key = keyView.getText().toString();
        String meaning = meaningView.getText().toString();
        Context context = layoutView.getContext();
        if (key.equals("")) {
            Toast.makeText(context, "Key can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (meaning.equals("")) {
            Toast.makeText(context, "Meaning can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Word insertedWord = sqlHelper.addWord(key, meaning);
        keyView.setText("");
        meaningView.setText("");
        if(insertedWord == null) {
            Toast.makeText(context, "Key already exist [ " + key + " ]", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Word inserted [ " + insertedWord.toString() + " ]", Toast.LENGTH_LONG).show();
        }
    }
}
