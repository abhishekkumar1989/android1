package com.abhishek_k.dictionarylearner;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.abhishek_k.dictionarylearner.model.Word;
import com.abhishek_k.dictionarylearner.service.WordHandlerService;

import static com.abhishek_k.dictionarylearner.QuizFragment.EXTRA_USER_NAME;

public class WordsRotatorFragment extends Fragment {

    private TextView wordView;
    private TextView meaningView;
    private WordHandlerService handlerThread;
    private Button quizPlayButton;
    private ImageButton rotatorStopButton;
    private ImageButton rotatorStartButton;
    private Button nextWordButton;
    private static final String LOG_TAG = WordsRotatorFragment.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_rotator, container, false);
        wordView = (TextView) view.findViewById(R.id.word_viewer);
        meaningView = (TextView) view.findViewById(R.id.meaning_viewer);

        rotatorStopButton = (ImageButton) view.findViewById(R.id.stop_wordSlide_button);
        rotatorStartButton = (ImageButton) view.findViewById(R.id.start_wordSlide_button);
        nextWordButton = (Button) view.findViewById(R.id.get_next_word_button);

        quizPlayButton = (Button) view.findViewById(R.id.quiz_playButton);
        wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditingWordActivity();
            }
        });
        handlerThread = WordHandlerService.get(getActivity());
        handlerThread.addRotatorHandler(new Handler());
        handlerThread.registerDisplayCb(new WordHandlerService.Callback() {
            @Override
            public void handleResponse(Word word) {
                updateWord(word);
            }
        });

        quizPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder userPopup = new AlertDialog.Builder(getActivity());
                userPopup.setTitle(R.string.give_username_label);
                userPopup.setMessage("Please provide your username");
                final EditText input = new EditText(getActivity());
                userPopup.setView(input);
                userPopup.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = input.getText().toString();
                        startQuizActivity(username.equals("") ? "Guest" : username);
                    }
                });
                userPopup.setNegativeButton("Play as Guest", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startQuizActivity("Guest");
                    }
                });
                userPopup.show();
            }
        });

        rotatorStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Stopping the rotator");
                handlerThread.stopRotatorRunnable();
                rotatorStopButton.setEnabled(false);
                rotatorStartButton.setEnabled(true);
            }
        });
        rotatorStartButton.setEnabled(false);
        rotatorStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Starting the rotator");
                handlerThread.modifyScheduler(WordHandlerService.rotateMillis);
                rotatorStartButton.setEnabled(false);
                rotatorStopButton.setEnabled(true);
            }
        });
        nextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Fetching the nex word");
                handlerThread.stopRotatorRunnable();
                handlerThread.getRandomWord();
                rotatorStopButton.setEnabled(false);
                rotatorStartButton.setEnabled(true);
            }
        });

        return view;
    }

    private void startEditingWordActivity() {
        Intent intent = new Intent(getActivity(), WordActivity.class);
        intent.putExtra(WordFragment.EXTRA_WORDKEY, wordView.getText().toString());
        intent.putExtra(WordFragment.EXTRA_MEANINGKEY, meaningView.getText().toString());
        startActivity(intent);
    }

    private void updateWord(Word word) {
        if (word == null) {
            wordView.setText("Nothing to display");
            return;
        }
        wordView.setText(word.getWord());
        meaningView.setText(word.getMeaning());
    }

    public void startQuizActivity(String username) {
        Intent intent = new Intent(getActivity(), QuizActivity.class);
        intent.putExtra(EXTRA_USER_NAME, username);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dictionary, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_add_word:
                Intent intent = new Intent(getActivity(), WordActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_edit_word:
                startEditingWordActivity();
                return true;
            case R.id.menu_item_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
