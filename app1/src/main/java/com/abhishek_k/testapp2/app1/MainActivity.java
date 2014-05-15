package com.abhishek_k.testapp2.app1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.abhishek_k.testapp2.app1.async.NetworkCall;

import static com.abhishek_k.testapp2.app1.ConverterUtil.celsiusToFahrenheit;
import static com.abhishek_k.testapp2.app1.ConverterUtil.fahrenheitToCelsius;
import static java.lang.String.valueOf;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void calculateEvent(View view) {
        EditText text = (EditText) findViewById(R.id.editText);
        switch (view.getId()) {
            case R.id.calculate:
                Log.d("button-clicked", "Calculate button is clicked");
                RadioButton celsiusButton = (RadioButton) findViewById(R.id.to_celsius);
                RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.to_fahrenheit);
                if (text.getText().length() == 0) {
                    Toast.makeText(this, "Please enter a valid number",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(text.getText().toString());
                if (celsiusButton.isChecked()) {
                    text.setText(valueOf(fahrenheitToCelsius(inputValue)));
                    celsiusButton.setChecked(false);
                    fahrenheitButton.setChecked(true);
                } else {
                    text.setText(valueOf(celsiusToFahrenheit(inputValue)));
                    fahrenheitButton.setChecked(false);
                    celsiusButton.setChecked(true);
                }
                break;
            case R.id.networkCall:
                Log.d("button-clicked", "Network button is clicked");
                NetworkCall networkCall = new NetworkCall(this);

//                getApplication().
                // Run parallelly
                networkCall.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "https://talk.to/api/history/status");

                // Run serially
//                AsyncTask<String, Integer, String> execute = networkCall.execute("https://talk.to/api/history/status");
                Toast.makeText(this, "Async call is made", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
