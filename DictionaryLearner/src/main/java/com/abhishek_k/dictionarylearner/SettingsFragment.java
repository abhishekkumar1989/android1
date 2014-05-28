package com.abhishek_k.dictionarylearner;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhishek_k.dictionarylearner.service.WordHandlerService;

public class SettingsFragment extends Fragment {

    private EditText rotatorMillisText;
    private Button updateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        rotatorMillisText = (EditText) view.findViewById(R.id.settings_rotator_millis);
        updateButton = (Button) view.findViewById(R.id.setting_updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMsString = rotatorMillisText.getText().toString();
                if (!newMsString.equals("") && !newMsString.equals(R.string.new_ms_is_zero)) {
                    WordHandlerService.get(getActivity()).modifyScheduler(Long.parseLong(newMsString));
                    Toast.makeText(getActivity(), R.string.success_activity_message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.warning_wrong_rotator_value, Toast.LENGTH_SHORT).show();
                }
            }
        });
        setHasOptionsMenu(true);
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
