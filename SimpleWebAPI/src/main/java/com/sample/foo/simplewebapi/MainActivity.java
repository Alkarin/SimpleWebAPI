package com.sample.foo.simplewebapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MainActivity extends AppCompatActivity {

    EditText emailText;
    TextView responseView;
    ProgressBar progressBar;
    static final String API_KEY = constants.API_KEY;
    static final String API_URL = "https://api.fullcontact.com/v2/person.json?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab elements
        responseView = (TextView) findViewById(R.id.responseView);
        emailText = (EditText) findViewById(R.id.emailText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Grab Button
        Button queryButton = (Button) findViewById(R.id.queryButton);
        // Set query execution on button click
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Execute query with async task
                new RetrieveFeedTask(responseView, emailText, progressBar).execute();
            }
        });
    }

    protected static void parseResponse(String response){

        if(response.equals("THERE WAS AN ERROR")){
            // Do nothing
            Log.i("INFO", "Exiting RetrieveFeedTask.parseResponse()");
            return;
        }

        try {
            JSONObject object = (JSONObject) new JSONTokener(response).nextValue();

            // Parse values from JSON Object into specific variables
            String requestID = object.getString("requestId");
            int likelihood = object.getInt("likelihood");
            JSONArray photos = object.getJSONArray("photos");

            //Log.d("Debug",String.valueOf(photos));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
